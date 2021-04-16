package org.ravic.blog.entity;

import javax.validation.constraints.NotEmpty;

/**
 * description:分类的实体类
 * 进行后端校验
 */
public class Type {
    //分类的编号
    //blog多对一type,type数据表的主键,关联blog的typeid外键
    private Long id;

    //分类的名字
    @NotEmpty(message = "name不能为空")
    private String name;

    //是否被选中
    private Boolean selected;

    public Type() {
    }

    public Type(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
