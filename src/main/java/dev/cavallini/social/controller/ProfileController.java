package dev.cavallini.social.controller;

import dev.cavallini.social.domain.user.LoginResponseDTO;
import dev.cavallini.social.domain.user.User;
import dev.cavallini.social.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping("/{username}")
    public ResponseEntity getProfile(@PathVariable @Valid String username) {
        UserDetails user = (User) userRepository.findByLogin(username);
        return ResponseEntity.ok(user);
    }

}
