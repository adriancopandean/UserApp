package UserApp.steps;

import UserApp.api.request.RegisterRequest;
import UserApp.util.StringUtil;
import io.cucumber.java.en.Given;

import java.util.HashMap;
import java.util.Map;

import static UserApp.util.StateMap.MYMAP;

public class PreparationSteps {
  @Given("I create a valid sign up request using api objects")
  public void iCreateAValidSignUpRequestUsingApiObjects() {
    MYMAP.setRegisterRequest(
            RegisterRequest.builder()
                    .username(StringUtil.generateValue("username"))
                    .email(StringUtil.generateValue("email"))
                    .password("12345").build());
  }

  @Given("^I create signup request from datatable$")
  public void iCreateSignupRequestFromDatatable(Map<String, String> attributes) {
    Map<String, String> mutableAttributes = new HashMap<>(attributes);

    mutableAttributes.forEach((key, value) ->{
      if ( value.equalsIgnoreCase("*GENERATED*")) {
          String generatedValue = StringUtil.generateValue(key);
          mutableAttributes.put(key, generatedValue);
      }
    });

    MYMAP.put("registerRequest", mutableAttributes);
  }
}
