package UserApp.util;

import java.security.SecureRandom;

public class StringUtil {
  private static final SecureRandom random = new SecureRandom();
  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRST";

  public static String generateValue(String key) {
    if (key.equals("email")) {
      return String.valueOf(generateRandomLetters(5)).concat("@mail.com");
    }
    if (key.equals("username")) {
      return String.valueOf(generateRandomLetters(4));
    }
    return "UNDEFINED";
  }

  private static char[] generateRandomLetters(int length) {
    char[] result = new char[length];
    for ( int i=0; i<length; i++) {
      int index = random.nextInt(LETTERS.length());
      result[i] = LETTERS.charAt(index);
    }
    return result;
  }
}
