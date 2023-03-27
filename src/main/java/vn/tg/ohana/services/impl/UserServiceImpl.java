package vn.tg.ohana.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.mappers.LoginMapper;
import vn.tg.ohana.mappers.UserMapper;
import vn.tg.ohana.repository.UserRepository;
import vn.tg.ohana.repository.model.*;
import vn.tg.ohana.services.PostMediaService;
import vn.tg.ohana.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    PostMediaService postMediaService;

    @Override
    public List<UserResult> findAll() {
        List<UserResult> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(userMapper.toDTO(user)));
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public List<PostResult> findAllByStatus(StatusPost status) {
//        List<PostResult> posts = new ArrayList<>();
//        postRepository.findAllByStatus(status).
//                forEach(post -> posts.add(postMapper.toDTO(post)));
//        return posts;
//    }

    @Override
    public void active(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(UserStatus.NOT_ACTIVATED);
            userRepository.save(user);
        }
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public UserResult signUp(SignUpParam signUpParam) {
        // User user = userRepository.getByEmailOrPhone(signUpParam.getPhoneOrEmail(), signUpParam.getPhoneOrEmail());
        boolean exists = existsByPhoneOrEmail(signUpParam.getPhoneOrEmail());
        if (exists) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        } else {
            User user = userMapper.signUpParamToUser(signUpParam);
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            User userResult = userRepository.save(user);
            return userMapper.toDTO(userResult);
        }
    }

    @Override
    public LoginResult saveGoogleEmail(GooglePojo googlePojo) {
        User userCheck = userRepository.findByEmail(googlePojo.getEmail());
        if (userCheck != null) {
            return loginMapper.toDTO(userCheck);
        } else {
            User user = userMapper.googlePojoToUser(googlePojo);
            user.setStatus(UserStatus.ACTIVATED);
            user.setRole(Role.USER);
            user.setPassword(googlePojo.getPassword());
            PostMedia postMedia = new PostMedia();
            postMedia.setFileUrl(googlePojo.getPicture());
            postMediaService.save(postMedia);
            user.setThumbnailId(postMedia.getId());
            User userResult = userRepository.save(user);
            return loginMapper.toDTO(userResult);
        }
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public LoginResult findByEmailOrPhone(String email, String phone) {
        User loginResult = userRepository.getByEmailOrPhone(email, phone);
        if (loginResult != null) {
            return loginMapper.toDTO(loginResult);
        }
        return null;
    }

    @Override
    public LoginResult findByEmail(String email) {
        User loginResult = userRepository.findByEmail(email);
        if (loginResult != null) {
            return loginMapper.toDTO(loginResult);
        }
        return null;
    }

    @Override
    public boolean existsByPhoneOrEmail(String phoneOrEmail) {
        String phone = phoneOrEmail;
        String email = phoneOrEmail;
        return userRepository.existsByPhoneOrEmail(phone, email);
    }

    @Override
    public UserResult updateFullInformation() {
        return null;
    }

    @Override
    public UserResult getUserByEmail(String email) {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }


    @Override
    public boolean checkInformationUser(String email) {
        UserResult userResult = userMapper.toDTO(userRepository.findByEmail(email));
        if (userResult.getEmail() != null && userResult.getPhone() != null) {
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserResult> findAllByStatus(UserStatus status) {
        List<UserResult> users = new ArrayList<>();
        userRepository.findAllByStatus(status).
                forEach(user -> users.
                        add(userMapper.
                                toDTO(user)));
        return users;
    }

    @Override
    public List<UserResult> searchUsers(String query, UserStatus status) {
        return userRepository.searchAllByStatus(query,status)
                .stream()
                .map(user -> userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public Long getQuantityUser(UserStatus userStatus) {
        return userRepository.countAllByStatus(userStatus);
    }

    @Override
    public boolean checkAdmin(LoginParam loginParam) {
        boolean check = userRepository.existsByPhoneOrEmailAndPasswordAndRole(loginParam.getPhoneOrEmail(), loginParam.getPhoneOrEmail(), loginParam.getPassword(), Role.ADMIN);
        return check;
    }
}
