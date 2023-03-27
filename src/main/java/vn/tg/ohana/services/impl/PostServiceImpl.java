package vn.tg.ohana.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.exceptions.DataInputException;
import vn.tg.ohana.mappers.PostMapper;
import vn.tg.ohana.repository.CPostRepository;
import vn.tg.ohana.repository.PostRepository;
import vn.tg.ohana.repository.UtilityRepository;
import vn.tg.ohana.repository.model.*;
import vn.tg.ohana.services.*;
import vn.tg.ohana.utils.UploadUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private TrendService trendService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CPostRepository cpostRepository;
    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private PostMediaService postMediaService;
    @Autowired
    private UtilityService utilityService;
//    @Autowired
//    LocationService locationService;

    @Autowired
    RentHouseService rentHouseService;


    @Override
    public Page<PostResult> findPosts(StatusPost statusPost, Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostResult> p = posts.stream().map(postMapper::toDTO).collect(Collectors.toList());
        PageImpl<PostResult> ps = new PageImpl<>(p, pageable, p.size());

        return ps;
    }

    @Override
    public void postNews(PostCreateParam postCreateParam) throws IOException {
        Post newPost = postMapper.toPost(postCreateParam);
        RentHouse rentHouse = rentHouseService.save(newPost.getRentHouse());
        newPost.setRentHouse(rentHouse);
        newPost.setStatus(StatusPost.PENDING_REVIEW);
        newPost.setCreatedAt(Instant.now());
        newPost.setUser(newPost.getUser());
        Post post = postRepository.save(newPost);
        postCreateParam.getImages().add(0, postCreateParam.getThumbnail());
        for (int i = 0; i < postCreateParam.getImages().size(); i++) {
            PostMedia postMedia = postMediaService.save(new PostMedia());
            uploadAndSaveProductImage(postCreateParam, post, postMedia, i);
        }
    }


    @Override
    public List<PostResult> findAllByCategoryId() {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findAll().forEach(post -> postMapper.toDTO(post));
        return posts;
    }

    @Override
    public List<PostResult> findAll() {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findAll().forEach(post -> posts.add(postMapper.toDTO(post)));
        return posts;
    }

    @Override
    public Optional<PostResult> findById(Long id) {
        Optional<Post> postOption = postRepository.findById(id);
        return postOption.map(post -> Optional.of(postMapper.toDTO(post))).orElse(null);
    }


    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<PostResult> findTop10ByCreatedAt(String province) {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findTop10ByCreatedAt("%provinceName\": \"Tỉnh Thừa Thiên Huế%").forEach(post -> posts.add(postMapper.toDTO(post)));
        return posts;
    }

    @Override
    public List<PostResult> findAllByWard(String address) {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findAllByLocation("%wardName\": \"" + address + "%").forEach(post -> posts.add(postMapper.toDTO(post)));
        return posts;
    }

    @Override
    public List<PostResult> findAllOptionalAttributePost() {
        return postRepository.findAllOptionalAttributePost();
    }

    @Override
    public List<PostResult> findAllByStatusAndUser(StatusPost status, User poster) {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findAllByStatusAndUser(status, poster).forEach(post -> posts.add(postMapper.toDTO(post)));
        return posts;
    }

    @Override
    public List<PostResult> findAllByStatus(StatusPost status) {
        List<PostResult> posts = new ArrayList<>();
        postRepository.findAllByStatus(status).
                forEach(post -> posts.add(postMapper.toDTO(post)));
        return posts;
    }

    @Override
    public void published(Long postId) {
        Optional<Post> postOption = postRepository.findById(postId);
        if (postOption.isPresent()) {
            Post post = postOption.get();
            post.setStatus(StatusPost.PUBLISHED);
            postRepository.save(post);
        }
    }

    //
//    @Override
//    public void emptyRoom(Long postId) {
//        Optional<Post> postOption = postRepository.findById(postId);
//        if (postOption.isPresent()) {
//            Post post = postOption.get();
//            post.setStatus(StatusPost.EMPTY_ROOM);
//            postRepository.save(post);
//        }
//    }
//
//    @Override
//    public void overRoom(Long postId) {
//        Optional<Post> postOption = postRepository.findById(postId);
//        if (postOption.isPresent()) {
//            Post post = postOption.get();
//            post.setStatus(StatusPost.OVER_ROOM);
//            postRepository.save(post);
//        }
//    }
    @Override
    public Long postPreview(PostCreateParam postCreateParam) {
        Post newPost = postMapper.toPost(postCreateParam);
        RentHouse rentHouse = rentHouseService.save(newPost.getRentHouse());
        newPost.setRentHouse(rentHouse);
        newPost.setStatus(StatusPost.DRAFT);
        newPost.setCreatedAt(Instant.now());
        newPost.setUser(newPost.getUser());
        Post post = postRepository.save(newPost);
        postCreateParam.getImages().add(0, postCreateParam.getThumbnail());
        for (int i = 0; i < postCreateParam.getImages().size(); i++) {
            PostMedia postMedia = postMediaService.save(new PostMedia());
            uploadAndSaveProductImage(postCreateParam, post, postMedia, i);
        }
        return post.getId();
    }

    @Override
    public void savePostPreview(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setStatus(StatusPost.PENDING_REVIEW);
            postRepository.save(post.get());
        }
    }

    @Override
    public void postEdit(PostUpdateParam postParam) throws IOException {
        Optional<Post> post = postRepository.findById(postParam.getId());
        if (post.isPresent()) {
            Post postEdit = postMapper.postUpdateToPost(postParam);
            postEdit.setCreatedAt(post.get().getCreatedAt());
            postEdit.setStatus(post.get().getStatus());
            postEdit.getRentHouse().setId(post.get().getRentHouse().getId());
            postEdit.setUpdatedAt(Instant.now());
            postEdit.setStatus(StatusPost.PENDING_REVIEW);
            postEdit.setCategory(new Category(postParam.getCategoryId()));
            postEdit.setUser(post.get().getUser());
            if (postParam.getPostMediaThumbnail().getId().equals("null")) {
                PostMedia postMedia = postMediaService.save(new PostMedia());
                uploadAndSaveProductImageEditThumbnail(postParam.getPostMediaThumbnail().getFile(), postEdit, postMedia);
                delete(post.get().getThumbnailId());
            } else {
                postEdit.setThumbnailId(post.get().getThumbnailId());
            }
            List<PostMediaResult> postMediaResults = postMediaService.findAllByPost(postEdit.getThumbnailId(), post.get().getId());

            List<String> imageIds = new ArrayList<>();
            for (PostMediaParam pM : postParam.getPostMediaImages()) {
                if (pM.getId().equals("null")) {
                    PostMedia postMedia = postMediaService.save(new PostMedia());
                    uploadAndSaveProductImageEdit(pM.getFile(), postEdit, postMedia);
                } else {
                    imageIds.add(pM.getId());
                }
            }
            for (PostMediaResult p : postMediaResults) {
                boolean check = false;
                for (String st : imageIds) {
                    if (p.getId().equals(st)) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    delete(p.getId());
                }
            }
            rentHouseService.save(postEdit.getRentHouse());
            postRepository.save(postEdit);
        }

    }

    @Override
    public List<PostResult> findAllByRentHouse_PriceIsBetweenAndRentHouse_Gender(BigDecimal priceStarts, BigDecimal priceEnds, Long genderId) {
        List<PostResult> postResult = new ArrayList<>();
        postRepository.findAllByRentHouse_PriceIsBetweenAndRentHouse_Gender(priceStarts, priceEnds, new Gender(genderId)).forEach(post -> postResult.add(postMapper.toDTO(post)));
        return postResult;
    }

    @Override
    public List<PostResult> findAllByRentHouse_PriceMax(BigDecimal priceStarts) {
        List<PostResult> postResult = new ArrayList<>();
        postRepository.findAllByRentHouse_PriceMax(priceStarts).forEach(post -> postResult.add(postMapper.toDTO(post)));
        return postResult;
    }

    @Override
    public List<PostResult> findAllByRentHouse_Price(BigDecimal priceStarts, BigDecimal priceEnds) {
        List<PostResult> postResult = new ArrayList<>();
        postRepository.findAllByRentHouse_PriceIsBetween(priceStarts, priceEnds).forEach(post -> postResult.add(postMapper.toDTO(post)));
        return postResult;
    }

    @Override
    public List<PostResult> findAllByRentHouse_Gender(Long genderId) {
        List<PostResult> postResult = new ArrayList<>();
        postRepository.findAllByRentHouse_Gender(new Gender(genderId)).forEach(post -> postResult.add(postMapper.toDTO(post)));
        return postResult;
    }

    @Override
    public List<PostResult> findAllByCategory(Long categoryId) {
        List<PostResult> postResult = new ArrayList<>();
        postRepository.findAllByCategory(new Category(categoryId)).forEach(post -> postResult.add(postMapper.toDTO(post)));
        return postResult;
    }

    @Override
    public List<PostResult> findAllByUtilitiesFilter(List<Long> utilitiesId) {
        return cpostRepository.findAllByUtilitiesFilter(utilitiesId).stream()
                .map(u -> postMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResult> findAllByFilter(FilterParam filterParam) {
        List<PostResult> listPostByPrice = new ArrayList<>();
        if (filterParam.getPriceEnds().compareTo(new BigDecimal(15000000)) == 0) {
            listPostByPrice = findAllByRentHouse_PriceMax(filterParam.getPriceStarts());
        } else {
            listPostByPrice = findAllByRentHouse_Price(filterParam.getPriceStarts(), filterParam.getPriceEnds());
        }
        List<PostResult> listPostByGender = new ArrayList<>();
        Long manId = 2L;
        Long womanId = 3L;
        if (filterParam.getGender() == manId || filterParam.getGender() == womanId) {
            listPostByGender = findAllByRentHouse_Gender(filterParam.getGender());
        } else {
            listPostByGender = findAllByStatus(StatusPost.PUBLISHED);
        }

        List<PostResult> listPostByCategory = new ArrayList<>();
        if (filterParam.getCategories().size() != 0) {
            for (int i = 0; i < filterParam.getCategories().size(); i++) {
                if (filterParam.getCategories().get(i) == 0) {
                    listPostByCategory = findAllByStatus(StatusPost.PUBLISHED);
                    break;
                }
                List<PostResult> listFind = findAllByCategory(filterParam.getCategories().get(i));

                if (listFind != null) {
                    listPostByCategory.addAll(listFind);
                }
            }
        } else {
            listPostByCategory = findAllByStatus(StatusPost.PUBLISHED);
        }

        List<PostResult> listPostByUtility = new ArrayList<>();
        if (filterParam.getUtilities().size() != 0) {
            listPostByUtility = findAllByUtilitiesFilter(filterParam.getUtilities());
        } else {
            listPostByUtility = findAllByStatus(StatusPost.PUBLISHED);
        }
        System.out.println("price " + listPostByPrice.size());
        System.out.println("category " + listPostByCategory.size());
        System.out.println("uti " + listPostByUtility.size());
        System.out.println("gender " + listPostByGender.size());

        List<PostResult> listPostByPriceAndCategory = new ArrayList<>();
        if (filterParam.getCategories().size() == 0) {
            listPostByPriceAndCategory = listPostByPrice;
        } else {
            listPostByPriceAndCategory = getListItemEquals(listPostByPrice, listPostByCategory);
        }
        System.out.println("priceAndCategory " + listPostByPriceAndCategory.size());
        List<PostResult> listPostByUtilityAndGender = new ArrayList<>();
        if (filterParam.getUtilities().size() == 0 && filterParam.getGender() != null) {
            listPostByUtilityAndGender = listPostByGender;
        } else if (filterParam.getGender() == null && filterParam.getUtilities().size() != 0) {
            listPostByUtilityAndGender = listPostByUtility;
        } else if (filterParam.getUtilities().size() == 0 && filterParam.getGender() == null) {
            listPostByUtilityAndGender = findAllByStatus(StatusPost.PUBLISHED);
        } else {
            listPostByUtilityAndGender = getListItemEquals(listPostByUtility, listPostByGender);
        }
        System.out.println("utilityAndGender " + listPostByUtilityAndGender.size());
        List<PostResult> listFilter = getListItemEquals(listPostByPriceAndCategory, listPostByUtilityAndGender);
        listFilter.removeIf(p -> p.getStatus() != StatusPost.PUBLISHED);

        if (filterParam.getWardId() == null && filterParam.getProvinceName() == null && filterParam.getLocationName() == null) {
            return listFilter;
        } else if (filterParam.getWardId() != null) {
            Trend trend = trendService.getById(filterParam.getWardId());
            List<PostResult> listTrend = findAllByWard(trend.getName());
            return getListItemEquals(listTrend, listFilter);
        } else {
            List<PostResult> postByProvinceNameAndLocationName = new ArrayList<>();
            postRepository.getDataSearch("%" + filterParam.getProvinceName() + "%", "%" + filterParam.getLocationName() + "%").forEach(post -> postByProvinceNameAndLocationName.add(postMapper.toDTO(post)));
            return getListItemEquals(postByProvinceNameAndLocationName, listFilter);
        }
    }

    @Override
    public PostResult editStatusRentHouse(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.get().getRentHouse().isStatus()) {
            post.get().getRentHouse().setStatus(false);
        } else {
            post.get().getRentHouse().setStatus(true);
        }
        return postMapper.toDTO(postRepository.save(post.get()));
    }

    @Override
    public Page<PostResult> findPostsByPage(List<PostResult> list, FilterParam filterParam, Pageable pageable) {
        List<PostResult> list1 = findAllByFilter(filterParam);
        return null;
    }

    @Override
    public Long getQuantityStatus(StatusPost statusPost) {
        return postRepository.getQuantityStatus(statusPost);
    }

    @Override
    public List<DataSearchResult> getDataSearch(String location, String province) {
        List<DataSearchResult> dataSearchResults = new ArrayList<>();
        String locationUnsigned = StringUtils.stripAccents(location).toLowerCase();
        postRepository.getDataSearch("%" + locationUnsigned + "%", province).forEach(post -> dataSearchResults.add(postMapper.toDataSearchResult(post, locationUnsigned)));
        dataSearchResults.removeIf(data -> data.getLocationDetail() == null);
        for (int i = 0; i < dataSearchResults.size(); i++) {
            for (int j = dataSearchResults.size() - 1; j > i; j--) {
                if (dataSearchResults.get(i).getLocationDetail().equals(dataSearchResults.get(j).getLocationDetail())) {
                    dataSearchResults.remove(j);
                }
            }
        }
        return dataSearchResults;
    }

    @Override
    public void deletePost(Long postId) {
        postMediaService.removeAllByPostId(postId);
        postRepository.deleteById(postId);
    }

    @Override
    public void unapproved(Long postId) {
        Optional<Post> postOption = postRepository.findById(postId);
        if (postOption.isPresent()) {
            Post post = postOption.get();
            post.setStatus(StatusPost.REFUSED);
            postRepository.save(post);
        }
    }


    private void uploadAndSaveProductImage(PostCreateParam postCreateParam, Post post, PostMedia postMedia, Integer index) {
        try {
            MultipartFile file = postCreateParam.getImages().get(index);
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(postMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            postMedia.setFileName(postMedia.getId() + "." + fileFormat);
            postMedia.setFileUrl(fileUrl);
            postMedia.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            postMedia.setCloudId(postMedia.getFileFolder() + "/" + postMedia.getId());
            postMedia.setPost(post);
            postMediaService.save(postMedia);
            if (index == 0) {
                post.setThumbnailId(postMedia.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    private void uploadAndSaveProductImageEdit(MultipartFile file, Post post, PostMedia postMedia) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(postMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            postMedia.setFileName(postMedia.getId() + "." + fileFormat);
            postMedia.setFileUrl(fileUrl);
            postMedia.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            postMedia.setCloudId(postMedia.getFileFolder() + "/" + postMedia.getId());
            postMedia.setPost(post);
            postMediaService.save(postMedia);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    private void uploadAndSaveProductImageEditThumbnail(MultipartFile file, Post post, PostMedia postMedia) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParams(postMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            postMedia.setFileName(postMedia.getId() + "." + fileFormat);
            postMedia.setFileUrl(fileUrl);
            postMedia.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            postMedia.setCloudId(postMedia.getFileFolder() + "/" + postMedia.getId());
            postMedia.setPost(post);
            postMediaService.save(postMedia);
            post.setThumbnailId(postMedia.getId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public void delete(String thumbnailId) throws IOException {
        Optional<PostMedia> postImage = postMediaService.findById(thumbnailId);
        if (postImage.isPresent()) {
            String publicId = postImage.get().getCloudId();

            uploadService.destroyImage(publicId, uploadUtils.buildImageDestroyParams(postImage.get(), publicId));

            postMediaService.delete(thumbnailId);
        }

    }

    public List<PostResult> getListItemEquals(List<PostResult> postResultOnes, List<PostResult> postResultTwos) {
        List<PostResult> postResults = new ArrayList<>();
        for (PostResult pOne : postResultOnes) {
            for (PostResult pTwo : postResultTwos) {
                if (pOne.getId() == pTwo.getId()) {
                    postResults.add(pTwo);
                    break;
                }
            }
            postResultTwos.remove(pOne);
        }
        return postResults;
    }

    public List<Integer> getInteger(List<Integer> postResultOnes, List<Integer> postResultTwos) {
        List<Integer> postResults = new ArrayList<>();
        for (Integer pOne : postResultOnes) {
            for (Integer pTwo : postResultTwos) {
                if (pOne == pTwo) {
                    postResults.add(pOne);
                    System.out.println("trong");
                    break;
                }
                System.out.println("hihi");
            }
            postResultTwos.remove(pOne);
        }
        return postResults;
    }


}
