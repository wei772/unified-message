package cn.garden.message.client.user;

import cn.garden.message.domain.User;

import java.util.List;

/**
 * @author liwei
 */
public interface UserClient {

    List<User> getList(List<String> codes);

}
