package tmdt.turf.model.user;

import jakarta.persistence.*;
import lombok.*;
import tmdt.turf.model.enums.Role;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;
    private Boolean enabled = false;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
