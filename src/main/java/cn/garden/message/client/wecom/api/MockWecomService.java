package cn.garden.message.client.wecom.api;

import cn.garden.message.client.wecom.model.WecomAccessTokenResponse;
import cn.garden.message.client.wecom.model.WecomSendMessageRequest;
import cn.garden.message.client.wecom.model.WecomSendMessageResponse;
import cn.garden.message.util.JsonUtil;
import cn.garden.message.util.RandomStringUtil;
import cn.garden.message.util.TestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MockWecomService implements WecomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockWecomService.class);

    @Override
    public WecomAccessTokenResponse getAccessToken(String cropId, String cropSecret) {
        WecomAccessTokenResponse wecomAccessTokenResponse = new WecomAccessTokenResponse();
        wecomAccessTokenResponse.setExpiresIn(3600);
        wecomAccessTokenResponse.setAccessToken("abcd1234567");

        LOGGER.info("getAccessToken" + JsonUtil.toJson(wecomAccessTokenResponse));
        return wecomAccessTokenResponse;
    }

    @Override
    public WecomSendMessageResponse sendMessage(String accessToken, WecomSendMessageRequest wecomSendMessageRequest) {
        WecomSendMessageResponse wecomSendMessageResponse = new WecomSendMessageResponse();
        wecomSendMessageResponse.setMsgId(RandomStringUtil.randomAlphanumeric(24));

        List<String> users = wecomSendMessageRequest.getToUsers();
        List<String> invalidUsers = new ArrayList<>();
        for (String user : users) {
            if (TestUtil.isTestData(user)) {
                invalidUsers.add(user);
            }
        }
        if (CollectionUtils.isNotEmpty(invalidUsers)) {
            wecomSendMessageResponse.setInvalidUsers(invalidUsers);
        }

        // 全部非法才报这个异常
        if (CollectionUtils.size(users) == CollectionUtils.size(invalidUsers)) {
            //尽可能复制真实异常信息
            wecomSendMessageResponse.setErrCode(81013);
            wecomSendMessageResponse.setErrMsg("user & party & tag all invalid, more info at https://open.work.weixin.qq.com/devtool/query?e=81013");
        }

        LOGGER.info("sendMessage" + JsonUtil.toJson(wecomSendMessageResponse));
        return wecomSendMessageResponse;
    }
}
