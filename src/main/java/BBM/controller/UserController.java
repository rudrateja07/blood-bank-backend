package BBM.controller;

import BBM.model.User;
import BBM.model.PasswordResetToken;
import BBM.repository.UserRepository;
import BBM.repository.PasswordResetTokenRepository;
import BBM.service.EmailService;
import BBM.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/donors")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (userRepository.existsByPhoneOrEmail(user.getPhone(), user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User with this phone or email already exists");
            }
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration: " + e.getMessage());
        }
    }

    // ✅ Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            Optional<User> userOpt = userRepository.findByEmailOrPhone(
                    loginRequest.getEmail(), loginRequest.getPhone());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    String token = jwtUtil.generateToken(user.getEmail());

                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    response.put("username", user.getName());
                    response.put("email", user.getEmail());

                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email/phone or password!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }

    // ✅ Forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            Optional<User> userOpt = userRepository.findByEmailOrPhone(email, email);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            }

            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setEmail(email);
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

            tokenRepository.save(resetToken);
            emailService.sendResetLink(email, token);

            return ResponseEntity.ok("Reset link sent to email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing forgot password request: " + e.getMessage());
        }
    }

   
}