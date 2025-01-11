package cn.garden.message.client.email.model;

import cn.garden.message.util.ExceptionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author liwei
 */
public class EmailSendRequest {

    /**
     * 服务器
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 扩展参数
     */
    private Map<String, Object> properties;

    /**
     * 邮件标题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件接收人
     */
    private List<String> receivers;

    /**
     * TODO考虑一下去重
     */
    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <a href="https://www.hncloud.com/supports/7761.html">...</a>
     * 端口25是SMTP的默认端口
     */
    public Integer getPort() {
        if (Objects.isNull(port)) {
            // SMTP默认端口
            return 25;
        }
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void validate() {
        if (StringUtils.isEmpty(getHost())) {
            throw ExceptionUtil.createDefaultException("邮件服务器地址不能为空");
        }
        if (StringUtils.isEmpty(getUsername())) {
            throw ExceptionUtil.createDefaultException("用户名称不能为空");
        }
        if (StringUtils.isEmpty(getPassword())) {
            throw ExceptionUtil.createDefaultException("用户密码不能为空");
        }
        if (StringUtils.isEmpty(getSubject())) {
            throw ExceptionUtil.createDefaultException("邮件标题不能为空");
        }
        if (StringUtils.isEmpty(getContent())) {
            throw ExceptionUtil.createDefaultException("邮件内容不能为空");
        }
        if (CollectionUtils.isEmpty(getReceivers())) {
            throw ExceptionUtil.createDefaultException("邮件接收者不能为空");
        }
    }
}
