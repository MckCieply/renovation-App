package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.user.dto.AppUserChangePasswordDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserProfileDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AppUserController {

        private final AppUserService appUserService;

        @GetMapping("/get")
        public ResponseEntity<AppUserProfileDTO> getUser(@RequestParam String username) {
            return ResponseEntity.ok(appUserService.getUser(username));
        }

        @PutMapping("/update")
        public ResponseEntity<Void> updateUser(@RequestBody AppUserProfileDTO profileDTO) {
            this.appUserService.updateUser(profileDTO);
            return ResponseEntity.ok().build();
        }

        @PutMapping("/change-password")
        public ResponseEntity<Void> changePassword(@RequestBody AppUserChangePasswordDTO changePasswordDTO) {
            this.appUserService.changePassword(changePasswordDTO);
            return ResponseEntity.ok().build();
        }

        @GetMapping("/get-all")
        public ResponseEntity<List<AppUserProfileDTO>> getAllUsers() {
            return ResponseEntity.ok(appUserService.getAllUsers());
        }
}
