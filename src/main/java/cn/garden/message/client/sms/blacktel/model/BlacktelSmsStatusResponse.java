package cn.garden.message.client.sms.blacktel.model;


import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author liwei
 */
@XmlRootElement(name = "returnsms")
public class BlacktelSmsStatusResponse extends BlacktelResponseBase {

    private Integer count;

    private BlacktelStatusboxList list = new BlacktelStatusboxList();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BlacktelStatusboxList getList() {
        return list;
    }

    public void setList(BlacktelStatusboxList list) {
        this.list = list;
    }
}
