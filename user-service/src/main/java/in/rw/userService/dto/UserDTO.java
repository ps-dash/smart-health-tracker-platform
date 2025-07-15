package in.rw.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String role;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
    private double height;
    private double weight;
    private String healthIssues;
}
