package cn.garden.message.repository.implementation;

import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PagedList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 内存消息仓库
 * 默认会创建100条数据
 *
 * @author liwei
 */
public class MemoryMessageRepository extends MessageRepository {

    private static final MemoryMessageRepository instance = new MemoryMessageRepository();
    private final List<Message> messages = new ArrayList<>();

    private MemoryMessageRepository() {
        //默认生成100条数据
        initMessageRepository(100);
    }

    public static MemoryMessageRepository getInstance() {
        return instance;
    }

    private void initMessageRepository(Integer count) {
        for (int i = 0; i < count; i++) {
            Message message = new Message();
            String id = IdGenerator.generateRandom();
            message.setId(id);
            message.setChannelName(MessageSenderChannelName.EMAIL.getName());
            save(message);
        }
    }

    @Override
    public PagedList<Message> getPageList(MessageParam messageParam, PageInfo pageInfo) {
        List<Message> list = getList(messageParam)
                .stream()
                .skip(pageInfo.getOffset())
                .limit(pageInfo.getPageSize())
                .collect(Collectors.toList());
        pageInfo.setTotalCount(count(messageParam));
        return new PagedList<>(pageInfo, list);
    }

    @Override
    public List<Message> getList(MessageParam messageParam) {
        return filterMessages(messageParam);
    }

    @Override
    public Long count(MessageParam messageParam) {
        return (long) (filterMessages(messageParam).size());
    }


    @Override
    public Message getById(String id) {
        return messages
                .stream()
                .filter(m -> StringUtils.equals(m.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveCore(Message message) {
        if (StringUtils.isEmpty(message.getId())) {
            message.setId(IdGenerator.generateRandom());
            messages.add(message);
            return;
        }

        Message dbMessage = getById(message.getId());
        if (Objects.isNull(dbMessage)) {
            messages.add(message);
            return;
        }

        dbMessage.update(message);
    }

    @Override
    public void remove(List<String> ids) {
        messages.removeIf(m -> ids.contains(m.getId()));
    }

    private List<Message> filterMessages(MessageParam messageParam) {
        Stream<Message> messageStream = messages.stream();
        if (StringUtils.isNotEmpty(messageParam.getId())) {
            messageStream = messageStream.filter(m -> StringUtils.equals(m.getId(), messageParam.getId()));
        }

        return messageStream
                .sorted(Comparator.comparing(Message::getUpdateTime).reversed())
                .toList();
    }
}
