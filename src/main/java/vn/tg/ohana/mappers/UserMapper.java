package vn.tg.ohana.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.GooglePojo;
import vn.tg.ohana.dto.SignUpParam;
import vn.tg.ohana.dto.UserResult;
import vn.tg.ohana.repository.model.PostMedia;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.services.PostMediaService;

import java.util.Optional;


@Component
public class UserMapper {

    @Autowired
    PostMediaService postMediaService;

    public UserResult toDTO(User user) {
        UserResult userResult = new UserResult();
        userResult.setId(user.getId());
        userResult.setFullName(user.getFullName());
//        userResult.setEmail(user.getEmail());

        if(user.getEmail().contains("@")){
            userResult.setEmail(user.getEmail());
        } else {
            userResult.setEmail(null);
        }
        userResult.setPhone(user.getPhone());
//        userResult.setPassword(user.getPassword());
//        userResult.setRole(user.getRole());
//        userResult.setStatus(user.getStatus());

        if (user.getThumbnailId() != null) {
            Optional<PostMedia> option = postMediaService.findById(user.getThumbnailId());
            option.ifPresent(postMedia -> userResult.setThumbnailUrl(option.get().getFileUrl()));
//            userResult.setThumbnailId(user.getThumbnailId());
        }
//        userResult.setThumbnailUrl(postMedia.get().getFileUrl());
        userResult.setDescription(user.getDescription());
        userResult.setAddress(user.getAddress());
        userResult.setRegisteredAt(user.getRegisteredAt());
        userResult.setLastLogin(user.getLastLogin());
        return userResult;
    }

    public User signUpParamToUser(SignUpParam signUpParam) {
        User user = new User();
        if (signUpParam.getPhoneOrEmail().contains("@")) {
            user.setEmail(signUpParam.getPhoneOrEmail());
            user.setPhone(null);
        } else {
            user.setPhone(signUpParam.getPhoneOrEmail());
            user.setEmail(String.valueOf(System.currentTimeMillis()));
        }
        user.setFullName(signUpParam.getFullName());
        user.setPassword(signUpParam.getPassword());
        return user;
    }

    public User googlePojoToUser(GooglePojo googlePojo) {
        User user = new User();
        user.setPassword(googlePojo.getId());
        user.setEmail(googlePojo.getEmail());
        user.setFullName(googlePojo.getName());
        user.setThumbnailId(googlePojo.getPicture());
        return user;
    }
}
