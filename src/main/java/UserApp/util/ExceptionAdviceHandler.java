package UserApp.util;

import UserApp.api.ResponseCode;
import UserApp.api.response.CustomErrorResponse;
import UserApp.api.response.ValidationErrorResponse;
import UserApp.exceptions.DuplicatedEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

import static UserApp.api.ResponseCode.Constants.MISSING_MANDATORY_FIELD;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleDuplicatedEmailException(DuplicatedEmailException d) {
    log.info("a user with this email already exists: {}", d.getMessage());
    return new ResponseEntity<>(CustomErrorResponse.builder()
            .description("email address is already used by another user")
            .property("email")
            .errorCode("101").build(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<CustomErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    log.warn("Type mismatch: {}", ex.getMessage());

    String message = String.format("You provided '%s', which is not a valid number.", ex.getValue());
    CustomErrorResponse errorResponse = CustomErrorResponse.builder()
            .description(message)
            .errorCode("400") // Use appropriate error code
            .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // Set the HTTP status to 400 Bad Request
  public ResponseEntity<CustomErrorResponse> handleMissingRequestHeader(MissingRequestHeaderException ex) {
    log.warn("Missing request header: {}", ex.getMessage());

    String message = String.format("The required header '%s' is missing.", ex.getHeaderName());
    CustomErrorResponse errorResponse = CustomErrorResponse.builder()
            .description(message)
            .errorCode("400") // Use an appropriate error code
            .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleException(MethodArgumentNotValidException ex) {
    var fields = ex.getFieldErrors().stream()
            .map(ExceptionAdviceHandler::mapToValidationError)
            .collect(Collectors.groupingBy(ValidationError::getProperty))
            .entrySet()
            .stream()
            .map(ExceptionAdviceHandler::removeOtherErrorsIfHasMissingMandatoryField)
            .collect(Collectors.flatMapping(v -> v.getValue().stream(), Collectors.toList()));
    fieldValidationLogger(fields);
    return new ResponseEntity<>(new ValidationErrorResponse(fields), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private static ValidationError mapToValidationError(FieldError e) {
    ResponseCode responseCode = ResponseCode.valueOf(e.getDefaultMessage());
    return ValidationError.builder()
            .description(responseCode.getDescription())
            .property(e.getField())
            .code(responseCode.getCode()).build();
  }

  private static Map.Entry<String, List<ValidationError>> removeOtherErrorsIfHasMissingMandatoryField(Map.Entry<String, List<ValidationError>> errorGroup) {
    Optional<ValidationError> missingField = errorGroup
            .getValue().stream().filter(x -> x.getDescription().equals(MISSING_MANDATORY_FIELD))
            .findFirst();
    if(missingField.isPresent()){
      return new AbstractMap.SimpleEntry<>(errorGroup.getKey(), Collections.singletonList(missingField.get()));
    } else {
      return  errorGroup;
    }
  }

  private static void fieldValidationLogger(List<ValidationError> fieldErrors) {
    for (ValidationError error : fieldErrors) {
      log.info("A validation exception was thrown for field: {} and message is: {}", error.getProperty(), error.getDescription());
    }
  }
}
