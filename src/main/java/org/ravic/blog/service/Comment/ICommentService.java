package org.ravic.blog.service.Comment;

import org.ravic.blog.entity.Comment;

import java.util.List;

public interface ICommentService {

    void saveComment(Comment comment);

    List<Comment> listCommentByBlogId(Long blogId);

    //根据id删除一条评论,并且还需要删除以该id为cid的所有评论
    void deleteCommentById(Long id);
}
