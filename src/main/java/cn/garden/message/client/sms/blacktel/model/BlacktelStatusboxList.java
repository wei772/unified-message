package cn.garden.message.client.sms.blacktel.model;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwei
 */
@XmlRootElement
public class BlacktelStatusboxList {

    /**
     *
     */
    @XmlElement(name = "statusbox")
    private List<BlacktelStatusbox> statusboxes = new ArrayList<>();

    @XmlTransient
    public List<BlacktelStatusbox> getStatusboxes() {
        return statusboxes;
    }

    public void setStatusboxes(List<BlacktelStatusbox> statusboxes) {
        this.statusboxes = statusboxes;
    }
}
