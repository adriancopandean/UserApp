package UserApp.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import static UserApp.api.ResponseCode.Constants.INVALID_LENGTH;
import static UserApp.api.ResponseCode.Constants.MISSING_MANDATORY_FIELD;

@Data
@Builder
public class RegisterRequest {

  @NotBlank(message = MISSING_MANDATORY_FIELD)
  @Size(max = 5, message = INVALID_LENGTH)
  String username;

  @NotBlank(message = MISSING_MANDATORY_FIELD)
  @Size(min = 5, max = 10, message = INVALID_LENGTH)
  String password;

  @NotBlank(message = MISSING_MANDATORY_FIELD)
  @Size(max = 20, message = INVALID_LENGTH)
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
          message = "INVALID_EMAIL_FORMAT")
  private String email;

}
