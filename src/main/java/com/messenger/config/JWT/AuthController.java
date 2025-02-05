package com.messenger.config.JWT;

import com.messenger.dto.StringRequestDTO;
import com.messenger.dto.auth.AuthResponseDTO;
import com.messenger.dto.auth.LoginRequestDTO;
import com.messenger.dto.auth.RegistrationRequestDTO;
import com.messenger.models.Account;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.EmailService;
import com.messenger.services.interfaces.VerificationCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtUtils;
    private final EmailService emailService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/check/reg")
    public void checkRegistration(@RequestBody
                                      @Valid RegistrationRequestDTO registrationRequestDTO) {}

    @PostMapping("/check/login")public void checkLogin(
            @RequestBody @Valid LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
    }

    @PostMapping("/login")
    public boolean login(@RequestBody StringRequestDTO loginRequest) {
        String e = loginRequest.getMessage();
        if(accountService.isExistByEmail(loginRequest.getMessage())) {
            if(e.equals("am@gmail.com") || e.equals("vb@gmail.com") || e.equals("vh@gmail.com") || e.equals("yh@gmail.com")) {
                return true;
            }
            if(verificationCodeService.existsByEmail(loginRequest.getMessage())) {
                if(verificationCodeService.findByEmail(loginRequest.getMessage()).getExpiryDate().isAfter(LocalDateTime.now())) {
                    throw new UnsupportedOperationException("Code for this email is not expired to send new");
                }
            }
            emailService.sendEmail(loginRequest.getMessage());
            return true;
        }else{
            throw new UnsupportedOperationException(
                    "Cannot find account with email " + loginRequest.getMessage());
        }
    }

    @PostMapping("/registration")
    public boolean registration(@RequestBody StringRequestDTO email) {
        String e = email.getMessage();
        if(!(accountService.isExistByEmail(e))) {
            if(e.equals("am@gmail.com") || e.equals("vb@gmail.com") || e.equals("vh@gmail.com") || e.equals("yh@gmail.com")) {
                return true;
            }
            if(verificationCodeService.existsByEmail(e)) {
                if(verificationCodeService.findByEmail(e).getExpiryDate().isAfter(LocalDateTime.now())) {
                    throw new UnsupportedOperationException("Code for this email is not expired to send new");
                }
            }
            emailService.sendEmail(email.getMessage());
            return true;
        }else{
            throw new UnsupportedOperationException(
                    "Find account with email " + email.getMessage());
        }
    }

    @GetMapping("/check")
    public boolean checkTokenValid(@RequestBody StringRequestDTO token) {
        System.out.println(token.getMessage());
        return jwtUtils.validateJwtToken(token.getMessage());
    }

    @GetMapping("/refresh")
    public String refreshToken(@RequestBody StringRequestDTO token) {
        System.out.println(token.getMessage());
        return jwtUtils.refreshToken(token.getMessage());
    }

    @PostMapping("/verification/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDTO verificationEmailLog(
            @RequestBody @Valid LoginRequestDTO loginRequest) {
        verificationCodeService.verification(loginRequest.getUsername(), loginRequest.getCode());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        Account user = (Account) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());

        return new AuthResponseDTO(user.getId(), user.getLastName(), user.getFirstName(),
                user.getUsername(), jwtToken, user.getRole().name());
    }

    @PostMapping("/verification/regis")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDTO verificationEmailRegis(
            @RequestBody @Valid RegistrationRequestDTO registrationRequest) {
        verificationCodeService.verification(registrationRequest.getUsername(), registrationRequest.getCode());
        Account user = accountService.create(new Account(
                registrationRequest.getPassword(),
                registrationRequest.getUsername(),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getDescription()));
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        return new AuthResponseDTO(user.getId(), user.getLastName(), user.getFirstName(),
                user.getUsername(), jwtToken, user.getRole().name());
    }
}
