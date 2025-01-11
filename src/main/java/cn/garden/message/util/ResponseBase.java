package cn.garden.message.util;

/**
 * 统一返回
 *
 * @author liwei
 */
public class ResponseBase {

    private Boolean succeed = true;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public void error(String errorMessage) {
        succeed = false;
        this.errorMessage = errorMessage;
    }
}
