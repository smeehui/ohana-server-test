package vn.tg.ohana.dto;

import lombok.Data;
import vn.tg.ohana.repository.model.Role;
import vn.tg.ohana.repository.model.UserStatus;

import java.time.Instant;

@Data
public class UserResult {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String thumbnailUrl;
//    private String thumbnailId;
    private String description;
    private String address;
    private Instant registeredAt;
    private Instant lastLogin;
//    private  String password;
//    private UserStatus status;
//    private Role role;
}
