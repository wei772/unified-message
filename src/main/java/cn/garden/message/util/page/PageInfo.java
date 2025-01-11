package cn.garden.message.util.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public class PageInfo {

    public static final int DEFAULT_PAGE_INDEX = 1;

    public static final int DEFAULT_PAGE_SIZE = 10;

    private Integer pageIndex;

    private Integer pageSize;

    private Long totalCount;

    public PageInfo() {
        setPageIndex(DEFAULT_PAGE_INDEX);
        setPageSize(DEFAULT_PAGE_SIZE);
    }

    public PageInfo(Integer pageIndex, Integer pageSize) {
        setPageIndex(pageIndex);
        setPageSize(pageSize);
    }

    @JsonIgnore
    public Integer getOffset() {
        return (this.pageIndex - 1) * this.pageSize;
    }

    /**
     * 从1开始索引
     */
    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        if (Objects.isNull(pageIndex)) {
            this.pageIndex = DEFAULT_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
        }
    }

    /**
     * 从0开始索引
     */
    @JsonIgnore
    public Integer getPageNumber() {
        return getPageIndex() - 1;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (Objects.isNull(pageSize) || pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 转换为Spring Data的Pageable
     */
    public Pageable toPageable() {
        return PageRequest.of(getPageNumber(), getPageSize());
    }
}
