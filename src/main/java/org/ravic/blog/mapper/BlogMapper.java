package org.ravic.blog.mapper;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.page.SearchCondition;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 博客DAO层
 */
@Repository
public interface BlogMapper {

    Blog getBlog(HashMap<String,Object> map);

    void saveBlog(Blog blog);

    void updateBlog(Blog blog);

    void deleteBlog(Long id);

    void deleteFkById(Long id);

    void deleteCommentByBlogId(Long blogId);

    //满足复合条件的记录的总数
    Long getCountByCondition(SearchCondition searchCondition);

    //满足复合条件的一页数据
    List<Blog> findByPage(HashMap<String,Object> map);

    void saveBlogJoinTag(HashMap<String,Object> map);

    int getCountFromJoinById(Long id);

    void deleteJoinByBlogId(Long id);

    List<Blog> listBlogTop(int size);

    //从评论表中查找某个blogId在评论表中对应的评论数量
    int getCountFromCommentById(Long id);

    //获取blog表中所有处于发表状态的博客
    List<Blog> getAllPublishedBlog();

    void updateViewsById(Long id);
}
