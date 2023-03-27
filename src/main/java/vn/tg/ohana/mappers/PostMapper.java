package vn.tg.ohana.mappers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.repository.model.*;
import vn.tg.ohana.services.PostMediaService;
import vn.tg.ohana.services.UtilityService;
import vn.tg.ohana.utils.JacksonParser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class PostMapper {
    @Autowired
    RentHouseMapper rentHouseMapper;

    @Autowired
    PostMediaMapper postMediaMapper;

    @Autowired
    LocationMapper locationMapper;

    @Autowired
    UtilityMapper utilityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMediaService postMediaService;

    @Autowired
    UtilityService utilityService;

    public PostResult toDTO(Post post) {
        PostResult result = new PostResult();
        result.setId(post.getId());
        result.setRentHouse(rentHouseMapper.toDTO(post.getRentHouse()));
        result.setTitle(post.getTitle());
        result.setSlug(post.getSlug());
        result.setDescriptionContent(post.getDescriptionContent());
        result.setCreatedAt(post.getCreatedAt());
        result.setUser(userMapper.toDTO(post.getUser()));
        result.setCategory(post.getCategory());
        result.setLocation(locationMapper.toLocationResult(post.getLocation()));
        result.setStatus(post.getStatus());

        if (post.getThumbnailId() != null) {
            Optional<PostMedia> option = postMediaService.findById(post.getThumbnailId());
            option.ifPresent(postMedia -> result.setThumbnailUrl(postMediaMapper.toDTO(option.get())));
        }
        List<PostMediaResult> postMediaResults = postMediaService.findAllByPost(post.getThumbnailId(), post.getId());
        result.setImageUrls(postMediaResults);

        List<Long> utilityIds = JacksonParser.INSTANCE.toList(post.getUtilities(), Long.class);
        result.setUtilities(utilityService.findAllByIdASC(utilityIds));

        return result;
    }

    public Post toPost(PostCreateParam postCreateParam) {
        Post post = new Post();
        post.setTitle(postCreateParam.getTitle());
        post.setDescriptionContent(postCreateParam.getDescription());
        post.setUser(new User(postCreateParam.getPoster()));
        post.setCategory(new Category(postCreateParam.getCategoryId()));
        RentHouse rentHouse = rentHouseMapper.toRentHouse(postCreateParam.getRentHouse());
        post.setRentHouse(rentHouse);

        post.setLocation(locationMapper.toLocation(postCreateParam.getLocation()));
        List<Long> utilities = postCreateParam.getUtilities();
        String jsonUtilities = JacksonParser.INSTANCE.toJson(utilities);
        post.setUtilities(jsonUtilities);

        return post;
    }

    public Post postUpdateToPost(PostUpdateParam postParam) {
        Post post = new Post();
        post.setId(postParam.getId());
        post.setTitle(postParam.getTitle());
        post.setDescriptionContent(postParam.getDescription());
        post.setUser(new User(postParam.getPoster()));
        post.setCategory(new Category(postParam.getCategoryId()));
        RentHouse rentHouse = rentHouseMapper.toRentHouse(postParam.getRentHouse());
        post.setRentHouse(rentHouse);
        post.setLocation(locationMapper.toLocation(postParam.getLocation()));
        List<Long> utilities = postParam.getUtilities();
        String jsonUtilities = JacksonParser.INSTANCE.toJson(utilities);
        post.setUtilities(jsonUtilities);

        return post;
    }

    public DataSearchResult toDataSearchResult(Post post, String locationUnsigned) {
        DataSearchResult dataSearchResult = new DataSearchResult();
//        dataSearchResult.setPostId(post.getId());
        dataSearchResult.setProvinceName(post.getLocation().getProvinceName());
//        if (post.getLocation().getProvinceUnsignedName().contains(locationUnsigned)) {
//            dataSearchResult.setLocationDetail(post.getLocation().getProvinceName());
//        }
        if (post.getLocation().getDistrictUnsignedName().contains(locationUnsigned)) {
            dataSearchResult.setDistrictName(post.getLocation().getDistrictName());
            dataSearchResult.setLocationDetail(post.getLocation().getDistrictName() + " ," + post.getLocation().getProvinceName());
        } else if (post.getLocation().getWardUnsignedName().contains(locationUnsigned)) {
            dataSearchResult.setWardName(post.getLocation().getWardName());
            dataSearchResult.setLocationDetail(post.getLocation().getWardName() + " ," + post.getLocation().getProvinceName());
        } else if (post.getLocation().getProvinceUnsignedName().contains(locationUnsigned) && !post.getLocation().getWardName().contains(locationUnsigned)) {
            dataSearchResult.setLocationDetail(null);
        } else {
            dataSearchResult.setLocationDetail(post.getLocation().getLocationDetail() + " ," + post.getLocation().getProvinceName());
        }
        return dataSearchResult;
    }
}
