package com.lamvt.shcedule.entity;


import com.lamvt.shcedule.constraint.AuthProvider;
import com.lamvt.shcedule.constraint.EStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String fullName;
    private String password;
    private String email;
    private String phone;

    private String timeZone;

    private LocalDate birthday;

    private String gender;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @CreatedDate
    private Instant createdAt;

}
