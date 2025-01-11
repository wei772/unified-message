package cn.garden.message.client.sms.blacktel.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 */
public class BlacktelResponseBase {

    /**
     * 成功返回码
     */
    static String SUCCEED_RESPONSE_CODE = "1";

    /**
     * 返回码
     */
    private String result = SUCCEED_RESPONSE_CODE;

    /**
     * 提示信息
     */
    private String tips;

    @JsonIgnore
    public Boolean isSucceed() {
        return StringUtils.equalsIgnoreCase(result, SUCCEED_RESPONSE_CODE);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void error(String tips) {
        result = "-99";
        this.tips = tips;
    }
}
