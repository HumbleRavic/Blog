package org.ravic.blog.page;

import java.util.List;

/**
 * 分页获取一页内容时,存放页级数据内容的实体类
 * 页级数据内容需要考虑：
 * 1.数据总数目
 * 2.总页数
 * 3.每页记录数
 * 4.当前页码
 * 5.当前页的数据内容
 * 额外新增：
 * 1.当前页面是否为首页
 * 2.当前页面是否为尾页
 */
public class Page<T> {
    //数据总数目
    private Long total;

    //总页数
    private int totalPage;

    //每页记录数
    private int size;

    //当前页码
    private int page;

    //当前页的数据内容
    private List<T> rows;

    //当前页面是否为首页
    private boolean isFirst;

    //当前页面是否为尾页
    private boolean isLast;

    public Page() {
    }

    public Page(Long total, int totalPage, int size, int page, List<T> rows) {
        this.total = total;
        this.totalPage = totalPage;
        this.size = size;
        this.page = page;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
