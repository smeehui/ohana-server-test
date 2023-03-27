package vn.tg.ohana.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam {
    private String phoneOrEmail;
    private String password;
}
