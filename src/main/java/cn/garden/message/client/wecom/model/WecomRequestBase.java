package cn.garden.message.client.wecom.model;

import cn.garden.message.util.ExceptionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 * 通用企业微信参数
 */
public class WecomRequestBase {

    /**
     * 企业Id
     */
    private String corpId;

    /**
     * 企业密钥
     * 可能是应用的密码
     */
    private String corpSecret;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @JsonIgnore
    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public void validateBase() {
        if (StringUtils.isEmpty(corpId)) {
            throw ExceptionUtil.createDefaultException("corpId不能为空");
        }
        if (StringUtils.isEmpty(corpSecret)) {
            throw ExceptionUtil.createDefaultException("corpSecret不能为空");
        }
    }
}
