package org.ravic.blog.entity;

/**
 * 分类名和该分类下博客数目的实体类
 */
public class TypeAndNum {
    //分类编号
    private Long id;

    //分类名称
    private String name;

    //分类的数量
    private int num;

    //是否被选中
    private Boolean selected = false;

    public TypeAndNum() {
    }

    public TypeAndNum(Long id, String name, int num) {
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
