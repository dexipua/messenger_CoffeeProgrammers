package com.messenger.controlers;

import com.messenger.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailTo(@RequestBody String email) {
        emailService.sendEmail(email);
    }
}
