package tmdt.turf.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a well-formed email address")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$",
            message = "Password must be length between 8 -> 20 characters, involve a-z and 0-9")
    private String password;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
}
