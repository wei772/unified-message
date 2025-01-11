package cn.garden.message.client.wecom.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 描述: 微信accesstoken返回示例
 * 微信文档 <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getStableAccessToken.html">...</a>
 */
public class WecomAccessTokenResponse extends WecomResponseBase {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
