package cn.garden.message.client.wecom;

import cn.garden.message.client.wecom.api.WecomServiceFactory;
import cn.garden.message.client.wecom.model.WecomSendMessageRequest;
import cn.garden.message.client.wecom.model.WecomSendMessageResponse;
import cn.garden.message.client.wecom.model.WecomTextMessage;
import cn.garden.message.util.cache.MemoryCache;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.garden.message.config.SenderConfig.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class WecomMessageClientTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(WecomMessageClientTest.class);

    /**
     * 异常1：
     * 接口api: sendMessage code: 60020 msg:not allow to access from your ip,
     * 解决 需要在企业微信的应用设置里面配置ip白名单
     * <p>
     * 异常2：
     * 接口api: getAccessToken code: 40013 msg:invalid corpid
     */
    @Test
    public void sendConfigError() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            WecomMessageClient wecomMessageClient = new WecomMessageClient();
            WecomSendMessageRequest wecomSendMessageRequest = new WecomSendMessageRequest();
            wecomSendMessageRequest.setCorpId(getWecomCorpId());
            wecomSendMessageRequest.setCorpSecret(null);
            wecomSendMessageRequest.setAgentId(getWecomAgentId());
            wecomSendMessageRequest.setToUser(getWecomUsers());
            wecomSendMessageRequest.setText(new WecomTextMessage("测试消息"));

            wecomMessageClient.sendMessage(
                    wecomSendMessageRequest);
        });

        LOGGER.warn(runtimeException.getMessage());
    }


    /**
     * 异常1：
     * 40001 msg:invalid credential
     * 需要企业微信应用启用
     * <p>
     * 异常2：
     * 40008 msg:Warning: wrong json format. invalid message type
     * more info at <a href="https://open.work.weixin.qq.com/devtool/query?e=40008">...</a>
     */
    @Test
    public void send() {
        WecomMessageClient wecomMessageClient = new WecomMessageClient();
        WecomSendMessageRequest wecomSendMessageRequest = new WecomSendMessageRequest();
        wecomSendMessageRequest.setCorpId(getWecomCorpId());
        wecomSendMessageRequest.setCorpSecret(getWecomCorpSecret());
        wecomSendMessageRequest.setAgentId(getWecomAgentId());
        wecomSendMessageRequest.setToUser(getWecomUsers());
        wecomSendMessageRequest.setText(new WecomTextMessage("测试消息"));

        WecomSendMessageResponse wecomSendMessageResponse = wecomMessageClient.sendMessage(
                wecomSendMessageRequest);
        assertTrue(wecomSendMessageResponse.succeed());
    }


    /**
     * 异常1：
     * 访问企业微信失败: not allow to access from your ip, hint: [1735980497142830326019081], from ip: 119.130.200.25
     * 经常需要添加 企业可信IP
     */
    @Test
    @Disabled
    public void sendWithWecomService() {

        WecomMessageClient wecomMessageClient = new WecomMessageClient(WecomServiceFactory.createService(), new MemoryCache<>());
        WecomSendMessageRequest wecomSendMessageRequest = new WecomSendMessageRequest();
        wecomSendMessageRequest.setCorpId(getWecomCorpId());
        wecomSendMessageRequest.setCorpSecret(getWecomCorpSecret());
        wecomSendMessageRequest.setAgentId(getWecomAgentId());
        wecomSendMessageRequest.setToUser(getWecomUsers());
        wecomSendMessageRequest.setText("测试消息");

        WecomSendMessageResponse wecomSendMessageResponse = wecomMessageClient.sendMessage(
                wecomSendMessageRequest);
        assertTrue(wecomSendMessageResponse.succeed());
    }
}
