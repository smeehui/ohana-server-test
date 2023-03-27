package vn.tg.ohana.uploader;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableConfigurationProperties(CloudinaryConfig.class)
public class UploaderConfig {
    @Autowired
    private CloudinaryConfig config;

    //    @Bean
//    public Cloudinary cloudinary() {
//        return new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", config.getCloudName(),
//                "api_key", config.getApiKey(),
//                "api_secret", config.getApiSecret()));
//    }
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ohana123",
                "api_key", "534578634358973",
                "api_secret", "yVhzuaKVIg7zcs2moQn47Ga9nH8"));
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolverX(){
//        CommonsMultipartResolver commonsMultipartResolver =new CommonsMultipartResolver();
//        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        return  commonsMultipartResolver;
//    }
}
