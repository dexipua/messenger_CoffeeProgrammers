package com.messenger.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "codes")
@NoArgsConstructor
@EqualsAndHashCode
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "expiry", nullable = false)
    private LocalDateTime expiryDate;

    public VerificationCode(String email) {
        this.email = email;
        this.code = UUID.randomUUID().toString().replace("-", "0").substring(0, 7);
        this.expiryDate = LocalDateTime.now().plusMinutes(3);
    }
}
