package cn.garden.message.repository;

import java.util.List;

public class MessageParam {

    private String id;

    private List<String> ids;

    public MessageParam() {

    }

    public MessageParam(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
