package org.ravic.blog.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description:评论的实体类
 */
public class Comment {
    //评论的编号
    private Long id;

    //评论者的昵称
    private String nickname;

    //评论者的邮箱
    private String email;

    //评论内容
    private String content;

    //评论者的头像
    private String avatar;

    //评论发表的时间
    private Date createTime;

    //该评论是否为博主发表的,true表示是管理员,false表示是普通用户
    private Boolean admin = false;

    //当前评论下的回复评论
    private List<Comment> commentList = new ArrayList<>();

    //当前评论的上级评论
    private Comment parentComment;

    //comment多对一blog,这里是关联blog主键的外键
    private Long blogId;

    //comment自连接,cid表示这条评论是回复那一条的
    private Long cid;

    //该comment所属的顶级根节点评论的id
    private Long rootId;

    public Comment() {
    }

    public Comment(Long id, String nickname, String email, String content, String avatar, Date createTime) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.avatar = avatar;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
