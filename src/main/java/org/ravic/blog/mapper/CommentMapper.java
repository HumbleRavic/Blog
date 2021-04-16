package org.ravic.blog.mapper;

import org.ravic.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CommentMapper {
    void saveComment(Comment comment);

    List<Comment> listCommentByBlogId(Long blogId);

    List<Comment> listTopCommentByBlogId(Long blogId);

    List<Comment> listCommentByCidAndBlogId(HashMap<String,Object> map);

    void deleteCommentById(Long id);

    void deleteCommentByCid(Long cid);
}
