package UserApp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicatedEmailException extends RuntimeException{
  String message;
}
