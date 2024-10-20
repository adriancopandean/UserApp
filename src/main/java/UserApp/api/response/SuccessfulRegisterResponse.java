package UserApp.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessfulRegisterResponse {
  String email;
  String username;
  String message;
}
