package cn.garden.message.repository.implementation;

import cn.garden.message.domain.Message;
import cn.garden.message.repository.MessageParam;
import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.factory.MessageRepositoryFactory;
import cn.garden.message.sender.MessageSenderChannelName;
import cn.garden.message.util.IdGenerator;
import cn.garden.message.util.page.PageInfo;
import cn.garden.message.util.page.PagedList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class MemoryMessageRepositoryTest {

    MessageRepository messageRepository = MessageRepositoryFactory.createMemory();

    @Test
    public void save() {
        Message message = new Message();
        String id = IdGenerator.generateRandom();
        message.setId(id);
        message.setChannelName(MessageSenderChannelName.EMAIL.getName());
        messageRepository.save(message);

        Message dbMessage = messageRepository.getById(id);
        assertNotNull(dbMessage);
        assertEquals(message.getChannelName(), dbMessage.getChannelName());
    }


    @Test
    public void getPageList() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(20);
        MessageParam messageParam = new MessageParam();
        PagedList<Message> pageList = messageRepository.getPageList(messageParam, pageInfo);

        assertEquals(20, pageList.size());
        assertTrue(pageList.getTotalCount() >= 100);
    }


    /**
     * MemoryMessageRepository 和其它单元测试是共享状态的
     */
    @Test
    public void getListWithParam() {
        Message firstMessage = new Message();
        String firstId = IdGenerator.generateRandom();
        firstMessage.setId(firstId);
        firstMessage.setChannelName(MessageSenderChannelName.EMAIL.getName());
        messageRepository.save(firstMessage);

        MessageParam messageParam = new MessageParam();
        messageParam.setId(firstId);
        List<Message> list = messageRepository.getList(messageParam);

        assertEquals(1, list.size());
        assertEquals(firstId, list.get(0).getId());
    }

}
