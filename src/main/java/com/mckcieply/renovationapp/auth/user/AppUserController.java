package com.mckcieply.renovationapp.auth.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
