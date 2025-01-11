package cn.garden.message.client.wecom.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 描述: 基础响应信息
 *
 * @author liwei
 */
public class WecomResponseBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(WecomResponseBase.class);

    @JsonProperty("errcode")
    private Integer errCode;

    @JsonProperty("errmsg")
    private String errMsg;

    @JsonIgnore
    public Boolean succeed() {
        if (Objects.isNull(errCode)) {
            return true;
        }
        return errCode == 0;
    }


    /**
     * 不合法的Token
     * <a href="https://developer.work.weixin.qq.com/document/path/90313#%E9%94%99%E8%AF%AF%E7%A0%81%EF%BC%9A40014">...</a>
     */
    public boolean isInvalidToken() {
        return Objects.equals(getErrCode(), 40014);
    }


    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
