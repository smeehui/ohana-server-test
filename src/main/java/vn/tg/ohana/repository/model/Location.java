package vn.tg.ohana.repository.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Location implements Serializable {
    private Long provinceId;
    private String provinceName;
    private String provinceUnsignedName;
    private Long districtId;
    private String districtName;
    private String districtUnsignedName;
    private Long wardId;
    private String wardName;
    private String wardUnsignedName;
    private String locationDetail;
    private String locationUnsignedDetail;

    public Location() {
    }

//    @JsonCreator
//    public Location(@JsonProperty("provinceId")
//                            Long provinceId,
//                    @JsonProperty("provinceName")
//                            String provinceName,
//                    @JsonProperty("provinceUnsignedName")
//                            String provinceUnsignedName,
//                    @JsonProperty("districtId")
//                            Long districtId,
//                    @JsonProperty("districtName")
//                            String districtName,
//                    @JsonProperty("districtUnsignedName")
//                            String districtUnsignedName,
//                    @JsonProperty("wardId")
//                            Long wardId,
//                    @JsonProperty("wardName")
//                            String wardName,
//                    @JsonProperty("wardUnsignedName")
//                            String wardUnsignedName,
//                    @JsonProperty("locationDetail")
//                            String locationDetail) {
//        this.provinceId = provinceId;
//        this.provinceName = provinceName;
//        this.provinceUnsignedName = provinceUnsignedName;
//        this.districtId = districtId;
//        this.districtName = districtName;
//        this.districtUnsignedName = districtUnsignedName;
//        this.wardId = wardId;
//        this.wardName = wardName;
//        this.wardUnsignedName = wardUnsignedName;
//        this.locationDetail = locationDetail;
//    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getProvinceUnsignedName() {
        return provinceUnsignedName;
    }

    public void setProvinceUnsignedName(String provinceUnsignedName) {
        this.provinceUnsignedName = provinceUnsignedName;
    }

    public String getDistrictUnsignedName() {
        return districtUnsignedName;
    }

    public void setDistrictUnsignedName(String districtUnsignedName) {
        this.districtUnsignedName = districtUnsignedName;
    }

    public String getWardUnsignedName() {
        return wardUnsignedName;
    }

    public void setWardUnsignedName(String wardUnsignedName) {
        this.wardUnsignedName = wardUnsignedName;
    }

    public String getLocationUnsignedDetail() {
        return locationUnsignedDetail;
    }

    public void setLocationUnsignedDetail(String locationUnsignedDetail) {
        this.locationUnsignedDetail = locationUnsignedDetail;
    }
}