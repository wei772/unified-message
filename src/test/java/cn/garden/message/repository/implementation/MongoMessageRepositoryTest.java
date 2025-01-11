package cn.garden.message.repository.implementation;

import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.MongoUtil;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PagedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class MongoMessageRepositoryTest {

    private static final List<String> ids = new ArrayList<>();

    static MessageRepository messageRepository = MessageRepositoryFactory.createMongo(MongoUtil.getMongoTemplate());

    @AfterAll
    public static void cleanUp() {
        messageRepository.remove(ids);
    }

    /**
     * 异常
     * MongoDB问题Map key a.b contains dots but no replacement was configured解决
     * <a href="https://blog.csdn.net/lonelymanontheway/article/details/124889298">...</a>
     */
    @Test
    public void save() {
        Message message = new Message();
        String id = IdGenerator.generateRandom();
        message.setId(id);
        message.setChannelName(MessageSenderChannelName.EMAIL.getName());
        messageRepository.save(message);
        ids.add(id);

        Message dbMessage = messageRepository.getById(id);

        assertNotNull(dbMessage);
        assertEquals(message.getChannelName(), dbMessage.getChannelName());
    }

    @Test
    public void getPageList() {
        initMessageRepository(100);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(20);
        MessageParam messageParam = new MessageParam();
        PagedList<Message> pageList = messageRepository.getPageList(messageParam, pageInfo);

        assertEquals(20, pageList.getDataList().size());
        assertTrue(pageList.getPageInfo().getTotalCount() >= 100);
    }


    @Test
    public void getListWithParam() {
        Message firstMessage = new Message();
        String firstId = IdGenerator.generateRandom();
        firstMessage.setId(firstId);
        firstMessage.setChannelName(MessageSenderChannelName.EMAIL.getName());
        messageRepository.save(firstMessage);
        ids.add(firstId);

        initMessageRepository(100);

        MessageParam messageParam = new MessageParam();
        messageParam.setId(firstId);
        List<Message> list = messageRepository.getList(messageParam);

        assertEquals(1, list.size());
        assertEquals(firstId, list.get(0).getId());
    }

    private void initMessageRepository(Integer count) {
        for (int i = 0; i < count; i++) {
            Message message = new Message();
            String id = IdGenerator.generateRandom();
            message.setId(id);
            message.setChannelName(MessageSenderChannelName.EMAIL.getName());
            messageRepository.save(message);
            ids.add(id);
        }
    }
}
