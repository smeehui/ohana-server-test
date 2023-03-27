package vn.tg.ohana.services;

import vn.tg.ohana.dto.*;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.repository.model.UserStatus;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<UserResult> findAll();

    Optional<User> findById(Long id);

    void active(Long userId);

    User getById(Long id);

    UserResult signUp(SignUpParam signUpParam);

    LoginResult saveGoogleEmail(GooglePojo googlePojo);

    void remove(Long id);

    LoginResult findByEmailOrPhone(String email, String phone);

    LoginResult findByEmail(String email);

    boolean existsByPhoneOrEmail(String phoneOrEmail);

    UserResult updateFullInformation();

    UserResult getUserByEmail(String email);
    boolean checkInformationUser(String email);

    User save(User user);
    List<UserResult> findAllByStatus(UserStatus status);

    List<UserResult> searchUsers(String query,UserStatus status);

    Long getQuantityUser(UserStatus userStatus);

    boolean checkAdmin(LoginParam loginParam);

//    IUserRepository userRepository;

//
//    public List<UserDto> findAll() {
//        List<User> users = userRepository.getAll();
//        List<UserDto> userDtos = new ArrayList<>();
//        users.forEach(user -> {
//            userDtos.add(UserDto.parser(user));
//        });
//        return userDtos;
//    }
}
