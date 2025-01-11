package cn.garden.message.client.sms.blacktel;

import cn.garden.message.client.sms.blacktel.api.BlacktelSendService;
import cn.garden.message.client.sms.blacktel.api.BlacktelServiceFactory;
import cn.garden.message.client.sms.blacktel.api.BlacktelStatusService;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendResponse;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusResponse;
import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.JsonUtil;
import cn.garden.message.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @author liwei
 */
@Service
public class BlacktelSmsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlacktelSmsClient.class);

    private final BlacktelSendService blacktelService;
    private final BlacktelStatusService blacktelStatusService;

    public BlacktelSmsClient() {
        this.blacktelService = BlacktelServiceFactory.createDefaultSendService();
        this.blacktelStatusService = BlacktelServiceFactory.createDefaultStatusService();
    }

    @Autowired
    public BlacktelSmsClient(BlacktelSendService blacktelService, BlacktelStatusService blacktelStatusService) {
        this.blacktelService = blacktelService;
        this.blacktelStatusService = blacktelStatusService;
    }

    public BlacktelSmsSendResponse send(BlacktelSmsSendRequest sendRequest) {
        if (Objects.isNull(sendRequest)) {
            throw ExceptionUtil.createDefaultException("BlacktelSmsSendRequest不能为空");
        }
        sendRequest.validate();

        sendRequest.setTimestamp(System.currentTimeMillis());

        String key = sendRequest.getEprId()
                + sendRequest.getUserId()
                + sendRequest.getPassword()
                + sendRequest.getTimestamp();
        key = DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
        sendRequest.setKey(key);

        if (Objects.isNull(sendRequest.getMsgId())) {
            sendRequest.setMsgId(UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY));
        }
        sendRequest.setPassword(StringUtils.EMPTY);

        BlacktelSmsSendResponse response = blacktelService.sendSms(sendRequest);
        if (response.isSucceed()) {
            response.setMsgId(sendRequest.getMsgId());
        }
        return response;
    }

    /**
     *
     */
    public BlacktelSmsStatusResponse getStatus(BlacktelSmsStatusRequest statusRequest) {
        String key = statusRequest.getEprId() + statusRequest.getUserId() + statusRequest.getPassword();
        key = DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
        statusRequest.setKey(key);

        //接口不规范，可能返回xml和json
        String responseString = blacktelStatusService.getSmsStatus(statusRequest);
        if (StringUtils.contains(responseString, "returnsms")) {
            try {
                return XmlUtil.toObject(responseString, BlacktelSmsStatusResponse.class);
            } catch (RuntimeException e) {
                LOGGER.warn("解析SmsStatus失败", e);
                BlacktelSmsStatusResponse blacktelSmsStatusResponse = new BlacktelSmsStatusResponse();
                blacktelSmsStatusResponse.error(e.getMessage());
                return blacktelSmsStatusResponse;
            }
        } else {
            return JsonUtil.toObject(responseString, BlacktelSmsStatusResponse.class);
        }
    }

}
