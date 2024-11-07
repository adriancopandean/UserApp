package UserApp.steps;

import UserApp.util.Config;
import UserApp.util.MyLogger;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;


import static UserApp.util.StateMap.MYMAP;

public class RestActionSteps {
  private Config config = new Config();

  public RestActionSteps() {
  }

  @When("^I post request to (.+)$")
  public void iPostRequestTo(String endpoint) {
    Response response = RestAssured.given()
            .log().all()
            .baseUri(config.getBaseUri())
            .contentType("application/json")
            .body(MYMAP.getRegisterRequest())
            .post(endpoint);
    MyLogger.log.info("Server response is {}", response.asPrettyString());
    MYMAP.setResponse(response);
  }

  @When("^I get users list when posting to (.+)$")
  public void iGetUsersListWhenPostingTo(String endpoint) {
    Response response = RestAssured.given()
            .log().all()
            .baseUri(config.getBaseUri())
            .auth().basic(MYMAP.getRegisterRequest().getUsername(), MYMAP.getRegisterRequest().getPassword())
            .get(endpoint);
    MyLogger.log.info("Server response is {}", response.asPrettyString());
    MYMAP.setResponse(response);
  }

  @When("^I post request named (.+) to (.+)$")
  public void iPostRequestNamedTo(String requestName, String endpoint) {
    Response response = RestAssured.given()
            .log().all()
            .baseUri(config.getBaseUri())
            .contentType("application/json")
            .body(MYMAP.get(requestName))
            .post(endpoint);
    MyLogger.log.info("Server response is {}", response.asPrettyString());
    MYMAP.setResponse(response);
  }
}
