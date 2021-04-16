package org.ravic.blog.entity;

/**
 * 标签名和该标签下博客数目的实体类
 */
public class TagAndNum {
    private Long id;

    private String name;

    private int num;

    //是否被选中
    private Boolean selected = false;

    public TagAndNum() {
    }

    public TagAndNum(Long id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
