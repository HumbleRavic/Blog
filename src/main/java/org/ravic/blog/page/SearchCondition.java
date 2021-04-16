package org.ravic.blog.page;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 博客复合搜索的搜索条件
 */
public class SearchCondition {
    @NotEmpty
    //博客标题
    private String title = null;

    @NotNull
    //博客类型编号
    private Long typeId = null;

    //博客是否被推荐
    private Boolean recommend = null;

    //博客标签编号
    private Long tagId = null;

    //博客是否已经发布
    private Boolean published = null;

    //模糊搜索的title
    private String likeTitle = null;

    public SearchCondition() {}

    public SearchCondition(String title, Long typeName, Boolean recommend) {
        this.title = title;
        this.typeId = typeName;
        this.recommend = recommend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getLikeTitle() {
        return likeTitle;
    }

    public void setLikeTitle(String likeTitle) {
        this.likeTitle = likeTitle;
    }
}
