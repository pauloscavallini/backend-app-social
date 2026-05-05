package dev.cavallini.social.controller;

import dev.cavallini.social.domain.user.LoginResponseDTO;
import dev.cavallini.social.domain.user.User;
import dev.cavallini.social.domain.user.UserProfileDTO;
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
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable @Valid String username) {
        User user = (User) userRepository.findByLogin(username);

        UserProfileDTO profileDTO = new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getDisplayname(),
                user.getProfile_picture_url()
        );

        return ResponseEntity.ok(profileDTO);
    }

}
