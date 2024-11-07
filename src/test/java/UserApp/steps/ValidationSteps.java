package UserApp.steps;

import UserApp.util.MyLogger;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.Map;

import static UserApp.util.StateMap.MYMAP;

public class ValidationSteps {
  @Then("I validate successful register response")
  public void iValidateSuccessfulRegisterResponse() {
    MyLogger.log.info("Checking register request was successful");
    Assert.assertEquals(200, MYMAP.getResponse().getStatusCode());
    Assert.assertEquals("Successfully registered", MYMAP.getResponse().path("message"));
  }

  @Then("^I check response contains the following$")
  public void iCheckResponseContainsTheFollowing(Map<String, String> attributes) {
    Map<String, String> myRegisterRequest = (Map<String, String>) MYMAP.get("registerRequest");

    attributes.forEach((key, value) ->{
      if ( value.equals("*GENERATED*")) {
        Assert.assertEquals(myRegisterRequest.get(key), MYMAP.getResponse().path(key));
      } else {
        MyLogger.log.info("Checking attribute {} has value {}", key, value);
        Assert.assertEquals(value, MYMAP.getResponse().path(key));
      }
    });
  }
}
