package vn.tg.ohana.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GGSignInParam {
    private String credential;
    private String g_csrf_token;

    @Override
    public String toString() {
        return "GGSignInParam{" +
                "credential='" + credential + '\'' +
                ", g_csrf_token='" + g_csrf_token + '\'' +
                '}';
    }
}
