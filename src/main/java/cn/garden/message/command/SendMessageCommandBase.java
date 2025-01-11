package cn.garden.message.command;

import cn.garden.message.client.user.UserClient;
import cn.garden.message.client.user.implementation.UserClientFactory;
import cn.garden.message.domain.*;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.*;
import cn.garden.message.sender.implementation.factory.MessageSenderFactory;
import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.IdGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @author liwei
 */
public abstract class SendMessageCommandBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageCommandBase.class);
    protected final MessageRepository messageRepository;
    private final UserClient userClient;
    private final MessageSenderFactory messageSenderFactory;
    int scheduleNumber = 2;
    private Timer timer;

    public SendMessageCommandBase() {
        this.messageRepository = MessageRepositoryFactory.createDefault();
        this.userClient = UserClientFactory.createDefault();
        this.messageSenderFactory = new MessageSenderFactory();
    }


    public SendMessageCommandBase(MessageRepository messageRepository
            , UserClient userClient
            , MessageSenderFactory messageSenderFactory
    ) {
        this.messageRepository = messageRepository;
        this.userClient = userClient;
        this.messageSenderFactory = messageSenderFactory;
    }

    protected void send(Message message) {
        MessageSender messageSender = messageSenderFactory.create(message.getChannelName());
        MessageSenderChannel channel = MessageSenderChannel.getChannel(message.getChannelName());

        List<MessageDetail> messageDetails = getSourceRecipients(message);
        message.setId(IdGenerator.generateRandom());

        MessageSenderRequest senderRequest = new MessageSenderRequest();
        senderRequest.setRecipients(getSourceRecipients(messageDetails));
        senderRequest.setContent(message.getContent());
        senderRequest.addAllProperty(message.getProperties());
        senderRequest.setMessageId(message.getId());

        MessageSenderResponse senderResponse = messageSender.send(channel, senderRequest);
        if (senderResponse.succeed()) {
            message.setStatus(MessageStatus.DONE.getName());
        } else {
            message.setStatus(MessageStatus.FAIL.getName());
        }
        message.setStatusMessage(senderResponse.getErrorMessage());
        message.setDetails(messageDetails);
        for (SenderDetail detail : senderResponse.getDetails()) {
            message.updateMessageDetail(detail);
        }

        messageRepository.save(message);

        queryStatus(messageSender);
    }


    private void queryStatus(MessageSender messageSender) {
        if (messageSender instanceof QueryMessageStatus queryMessageStatus) {
            //第一次同步执行
            queryStatus(queryMessageStatus);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //剩余次数异步定时器执行
                    scheduleNumber--;
                    try {
                        queryStatus(queryMessageStatus);
                    } catch (Exception ex) {
                        LOGGER.info("定时查询queryStatus异常", ex);
                    }

                    if (scheduleNumber <= 0) {
                        timer.cancel();
                    }
                }
            }, 1000L, 1000L);
        }
    }

    private void queryStatus(QueryMessageStatus messageSender) {
        //存在跨消息的返回
        List<SenderDetail> senderDetails = messageSender
                .queryStatus();
        if (CollectionUtils.isNotEmpty(senderDetails)) {
            List<String> messageIds = senderDetails
                    .stream()
                    .map(SenderDetail::getMessageId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();
            if (CollectionUtils.isNotEmpty(messageIds)) {
                MessageParam messageParam = new MessageParam();
                messageParam.setIds(messageIds);
                List<Message> messages = messageRepository.getList(messageParam);
                for (Message message : messages) {
                    List<SenderDetail> messageSenderDetails = senderDetails
                            .stream()
                            .filter(m -> Objects.equals(m.getMessageId(), message.getId()))
                            .toList();
                    for (SenderDetail senderDetail : messageSenderDetails) {
                        message.updateMessageDetail(senderDetail);
                    }
                    messageRepository.save(message);
                }
            }
        }
    }

    private List<MessageDetail> getSourceRecipients(Message message) {
        if (Objects.isNull(message)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(message.getRecipients())) {
            return new ArrayList<>();
        }

        RecipientType recipientType = RecipientType.of(message.getRecipientType());
        switch (recipientType) {
            case SOURCE -> {
                return message
                        .getRecipients()
                        .stream()
                        .map(m -> new MessageDetail(m, m))
                        .toList();

            }
            case USER -> {
                List<User> users = userClient.getList(message.getRecipients());
                if (CollectionUtils.isEmpty(users)) {
                    throw ExceptionUtil.createDefaultException("找不到用户");
                }
                return getSourceRecipients(users, message.getChannelName(), message.getRecipients());
            }
        }
        return new ArrayList<>();
    }

    private List<String> getSourceRecipients(List<MessageDetail> messageDetails) {
        return messageDetails.stream().map(MessageDetail::getSourceRecipient).toList();
    }

    private List<MessageDetail> getSourceRecipients(List<User> users,
                                                    String channelName,
                                                    List<String> recipients) {
        List<MessageDetail> details = new ArrayList<>();
        MessageSenderChannelName messageSenderChannelName = MessageSenderChannelName.of(channelName);
        for (String recipient : recipients) {
            User user = users
                    .stream()
                    .filter(m -> StringUtils.equals(m.getCode(), recipient))
                    .findFirst()
                    .orElse(null);
            MessageDetail messageDetail = new MessageDetail();
            details.add(messageDetail);

            messageDetail.setRecipient(recipient);
            if (Objects.isNull(user)) {
                messageDetail.error("找不到用户");
                continue;
            }

            switch (messageSenderChannelName) {
                case WECOM -> messageDetail.setSourceRecipient(user.getCode());
                case SMS, BLACKTEL -> {
                    messageDetail.setSourceRecipient(user.getMobile());
                    if (StringUtils.isEmpty(messageDetail.getStatusMessage())) {
                        messageDetail.error("用户未配置手机");
                    }
                }
                case EMAIL -> {
                    messageDetail.setSourceRecipient(user.getEmail());
                    if (StringUtils.isEmpty(messageDetail.getSourceRecipient())) {
                        messageDetail.error("用户未配置邮箱");
                    }
                }
            }
        }

        return details;
    }
}
