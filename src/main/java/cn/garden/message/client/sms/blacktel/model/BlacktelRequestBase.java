package cn.garden.message.client.sms.blacktel.model;


import cn.garden.message.util.ExceptionUtil;

import java.util.Objects;

/**
 * @author liwei
 */
public class BlacktelRequestBase {

    /**
     * 企业Id
     */
    private Integer eprId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 请求key
     */
    private String key;


    public Integer getEprId() {
        return eprId;
    }

    public void setEprId(Integer eprId) {
        this.eprId = eprId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void validateBase() {
        if (Objects.isNull(eprId)) {
            throw ExceptionUtil.createDefaultException("eprId不能为空");
        }
        if (Objects.isNull(password)) {
            throw ExceptionUtil.createDefaultException("password不能为空");
        }
        if (Objects.isNull(userId)) {
            throw ExceptionUtil.createDefaultException("userId不能为空");
        }
    }
}
