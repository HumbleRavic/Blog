package org.ravic.blog.entity;

/**
 * blog和tag多对多,该类作为二者中间表的实体类
 */
public class BlogJoinTag {
    //中间表编号
    //中间表主键
    private Long id;

    //中间表关联blog数据表中id主键的外键
    private int blogId;

    //中间表关联tag数据表中id主键的外键
    private int tagId;

    public BlogJoinTag() {}

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
