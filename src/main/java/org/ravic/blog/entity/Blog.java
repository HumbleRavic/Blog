package org.ravic.blog.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * description:博客实体类
 */
public class Blog {
    //博客编号
    //blog数据表的主键,要关联blogjointag数据表的blogId外键
    private Long id;

    //博客标题
    @NotEmpty(message = "标题不能为空")
    private String title;

    //博客内容
    @NotEmpty(message = "内容不能为空")
    private String content;

    //博客首图地址
    @NotEmpty(message = "首图地址不能为空")
    private String firstPicture;

    //是否为原创,true表示原创,false表示转载
    @NotNull(message = "必须声明原创或者转载")
    private Boolean flag = true;

    //博客被浏览的次数
    private Integer views;

    //博客是否开启打赏
    private Boolean appreciation = false;

    //博客是否允许转发
    private Boolean shareStatement = false;

    //博客是否开启评论
    private Boolean commentabled = false;

    //博客此时的状态,true表示已经发表,false表示处于暂存
    private Boolean published = false;

    //博客是否被推荐
    private Boolean recommend = false;

    //博客发布时间
    private Date createTime;

    //博客更新时间
    private Date updateTime;

    //博客描述信息
    private String description;

    //blog多对一type
    private Type type;

    //blog多对多tag
    private List<Tag> tagList;

    //blog多对一user
    private User user;

    //blog一对多comment
    private List<Comment> commentList;

    @NotNull(message = "分类不能为空")
    //blog多对一type,这里是关联type表id主键的外键
    private Long typeId;

    //blog多对一user,这里是关联user表id主键的外键
    private Long userId;

    public Blog() {
    }

    public Blog(Long id, String title, String content, String firstPicture, Boolean flag, Integer views, Boolean appreciation, Boolean shareStatement, Boolean commentabled, Boolean published, Boolean recommend, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.views = views;
        this.appreciation = appreciation;
        this.shareStatement = shareStatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Boolean commentabled) {
        this.commentabled = commentabled;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
