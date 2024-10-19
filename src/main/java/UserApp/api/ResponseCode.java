package UserApp.api;

import lombok.Getter;

@Getter
public enum ResponseCode {
  UNAUTHORIZED_REQUEST("401", "unauthorized"),
  DUPLICATED_EMAIL_ADDRESS("101", "email address is already used by another user"),
  INVALID_LENGTH("102", "invalid length"),
  MISSING_MANDATORY_FIELD("103", "missing mandatory field"),
  INVALID_EMAIL_FORMAT("104", "invalid format");

  final String code;
  final String description;

  ResponseCode(String code, String description) {
    this.code = code;
    this.description =description;
  }

  public static class Constants {
    public static final String MISSING_MANDATORY_FIELD = "MISSING_MANDATORY_FIELD";
    public static final String INVALID_VALUE = "INVALID_VALUE";
    public static final String INVALID_LENGTH = "INVALID_LENGTH";
  }

}
