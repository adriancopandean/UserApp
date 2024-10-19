package UserApp.api.response;

import UserApp.util.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
  private List<ValidationError> errors;
}
