package UserApp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude
public class ValidationError {
  private String description;
  private String property;
  private String code;
}
