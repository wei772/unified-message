package cn.garden.message.util.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {

    private final PageInfo pageInfo;
    private final List<T> dataList;

    public PagedList(PageInfo pageInfo) {
        this(pageInfo, new ArrayList<>());
    }

    public PagedList(PageInfo pageInfo, List<T> dataList) {
        this.pageInfo = pageInfo;
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public int size() {
        return dataList.size();
    }

    @JsonIgnore
    public Long getTotalCount() {
        return pageInfo.getTotalCount();
    }
}
