package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private Long userId;
    private String login;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
}
