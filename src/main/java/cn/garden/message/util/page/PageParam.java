package cn.garden.message.util.page;

public class PageParam<T> {

    private T param;

    private PageInfo pageInfo;

    public PageParam() {
    }

    public PageParam(T param, PageInfo pageInfo) {
        this.param = param;
        this.pageInfo = pageInfo;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
