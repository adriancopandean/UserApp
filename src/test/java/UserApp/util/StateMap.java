package UserApp.util;

import UserApp.api.request.RegisterRequest;
import io.restassured.response.Response;

import java.util.HashMap;


public enum StateMap {
  MYMAP;

  private final HashMap<String, Object> state = new HashMap<>();

  private RegisterRequest registerRequest;
  private Response response;

  public void setRegisterRequest(RegisterRequest registerRequest) {
    this.registerRequest = registerRequest;
  }

  public RegisterRequest getRegisterRequest() {
    return registerRequest;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public Response getResponse() {
    return response;
  }

  public void put(String key, Object value) {
    state.put(key, value);
  }

  public Object get(String key) {
    return state.get(key);
  }
}
