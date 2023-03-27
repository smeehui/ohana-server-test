package vn.tg.ohana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class LocationParam {
    private Long provinceId;
    private String provinceName;
    private Long districtId;
    private String districtName;
    private Long wardId;
    private String wardName;
    private String locationDetail;

    @Override
    public String toString() {
        return "LocationParam{" +
                "provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", districtId=" + districtId +
                ", districtName='" + districtName + '\'' +
                ", wardId=" + wardId +
                ", wardName='" + wardName + '\'' +
                ", locationDetail='" + locationDetail + '\'' +
                '}';
    }

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

    @JsonProperty("provinceUnsignedName")
    public String getProvinceUnsignedName() {
        return StringUtils.stripAccents(provinceName).toLowerCase();
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

    @JsonProperty("districtUnsignedName")
    public String getDistrictUnsignedName() {
        return StringUtils.stripAccents(districtName).toLowerCase();
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

    @JsonProperty("wardUnsignedName")
    public String getWardUnsignedName() {
        return StringUtils.stripAccents(wardName).toLowerCase();
    }

    @JsonProperty("locationUnsignedDetail")
    public String getLocationUnsignedDetail() {
        return StringUtils.stripAccents(locationDetail).toLowerCase();
    }

    public String getLocationDetail() {
        return locationDetail;
    }


    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }
}