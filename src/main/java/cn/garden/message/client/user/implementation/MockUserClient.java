package cn.garden.message.client.user.implementation;

import cn.garden.message.client.user.UserClient;
import cn.garden.message.domain.User;
import cn.garden.message.util.TestUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwei
 */
public class MockUserClient implements UserClient {

    @Override
    public List<User> getList(List<String> codes) {
        List<User> users = new ArrayList<>();
        for (String code : codes) {
            if (TestUtil.isTestData(code)) {
                continue;
            }
            User user = new User();
            user.setCode(code);
            user.setEmail(RandomStringUtils.secure().nextNumeric(11) + "@qq.com");
            user.setMobile(RandomStringUtils.secure().nextNumeric(11));

            users.add(user);
        }
        return users;
    }
}
