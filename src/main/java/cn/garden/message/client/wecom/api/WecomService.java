package cn.garden.message.client.wecom.api;

import cn.garden.message.client.wecom.model.WecomAccessTokenResponse;
import cn.garden.message.client.wecom.model.WecomSendMessageRequest;
import cn.garden.message.client.wecom.model.WecomSendMessageResponse;
import feign.Body;
import feign.Param;
import feign.RequestLine;

/**
 * 描述:
 *
 * @author liwei
 */
public interface WecomService {

    /**
     * 获取accessToken
     * <a href="https://developer.work.weixin.qq.com/document/path/91039">...</a>
     */
    @RequestLine("GET /cgi-bin/gettoken")
    WecomAccessTokenResponse getAccessToken(
            @Param("corpid") String cropId,
            @Param("corpsecret") String cropSecret
    );


    /**
     * 推送文本、图片、视频、文件、图文等类型
     * <a href="https://developer.work.weixin.qq.com/document/path/90236">...</a>
     */
    @RequestLine("POST /cgi-bin/message/send?access_token={accessToken}")
    @Body("{body}")
    WecomSendMessageResponse sendMessage(
            @Param("accessToken") String accessToken,
            @Param("body") WecomSendMessageRequest wecomSendMessageRequest
    );
}
