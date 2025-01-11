package cn.garden.message.sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liwei
 */
public class MessageSenderResponse {

    private Boolean succeed = true;

    private String errorMessage;

    private List<SenderDetail> details;

    public boolean succeed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public void error(String message) {
        succeed = false;
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<SenderDetail> getDetails() {
        if (Objects.isNull(details)) {
            return new ArrayList<>();
        }
        return details;
    }

    public void setDetails(List<SenderDetail> details) {
        this.details = details;
    }

}
