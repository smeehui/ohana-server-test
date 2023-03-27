package vn.tg.ohana.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResult {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String thumbnailUrl;

}
