package UserApp;

import UserApp.api.request.RegisterRequest;
import UserApp.exceptions.DuplicatedEmailException;
import UserApp.model.AppUser;
import UserApp.repository.UserRepository;
import UserApp.util.Greeting;
import UserApp.util.JustANumber;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class AppController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }

  @GetMapping("/give-number")
  public JustANumber number(@RequestParam(value = "number") int number,
                            @RequestHeader("Session-Token") String sessionToken) {
    return new JustANumber(counter.incrementAndGet(), number);
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AppUser> register(@Valid @RequestBody RegisterRequest request) {
    log.info("POST /register: {}", request);

    Optional<AppUser> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
    if (user.isPresent()) {
      throw new DuplicatedEmailException("already exists");
    }

    AppUser appUser = new AppUser();
    appUser.setPassword(passwordEncoder.encode(request.getPassword()));
    appUser.setUsername(request.getUsername());
    appUser.setEmail(request.getEmail());
    userRepository.save(appUser);
    return new ResponseEntity<>(userRepository.findByEmail(request.getEmail()), HttpStatus.OK);
  }

  @GetMapping("/users")
  public ResponseEntity<List<AppUser>> getUsers() {
    List<AppUser> users = userRepository.findAll();

    // Create headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Custom-Header-Value", "762644");

    return ResponseEntity.ok()
            .headers(headers)
            .body(users);
  }
}
