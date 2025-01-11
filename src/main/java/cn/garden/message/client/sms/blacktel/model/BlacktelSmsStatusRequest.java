package cn.garden.message.client.sms.blacktel.model;

import cn.garden.message.util.JsonUtil;

/**
 * @author liwei
 */
public class BlacktelSmsStatusRequest extends BlacktelRequestBase {

    /**
     * 功能名
     */
    private String cmd = "status";

    /**
     * 状态数量
     */
    private Integer num = 500;


    /**
     * 状态的类型
     */
    private String type = "1";


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public Integer getEprId() {
        return super.getEprId();
    }

    @Override
    public void setEprId(Integer eprId) {
        super.setEprId(eprId);
    }

    @Override
    public String getUserId() {
        return super.getUserId();
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }


}

