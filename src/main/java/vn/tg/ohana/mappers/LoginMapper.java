package vn.tg.ohana.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.LoginResult;
import vn.tg.ohana.repository.model.PostMedia;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.services.PostMediaService;

import java.util.Optional;

@Component
public class LoginMapper {

    @Autowired
    PostMediaService postMediaService;

    public LoginResult toDTO(User user) {
        LoginResult loginResult = new LoginResult();
        loginResult.setId(user.getId());
        loginResult.setFullName(user.getFullName());
        loginResult.setEmail(user.getEmail());
        loginResult.setPhone(user.getPhone());
        loginResult.setPassword(user.getPassword());
        if (user.getThumbnailId() != null) {
            Optional<PostMedia> option = postMediaService.findById(user.getThumbnailId());
            option.ifPresent(postMedia -> loginResult.setThumbnailUrl(option.get().getFileUrl()));
        }
        return loginResult;
    }
}
