package org.ravic.blog.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 标签的实体类
 */
public class Tag {
    //标签的编号
    //tag数据表的主键,要关联blogjointag数据表的tagId外键
    private Long id;

    //标签的名字
    @NotNull(message = "name不能为空")
    private String name;

    //标签是否选中
    private Boolean selected;

    public Tag() {
    }

    public Tag(Long id, String name) {
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
        return "Tag{" +
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
