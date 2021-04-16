package org.ravic.blog.service.Blog;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.page.Page;

import java.util.List;


public interface IBlogService {

    //根据筛选条件分页获取微博
    Page<Blog> listBlog(SearchCondition searchCondition, int page, int size);

    //根据博客Id获取博客内联Type,Tag
    Blog getBlog(Long id);

    void saveBlog(Blog blog,Integer[] tagIds);

    void updateBlog(Blog blog,Integer[] tagIds);

    void deleteBlog(Long id);

    List<Blog> listBlogTop(int size);

    //获取将markdown文本转换成html的blog对象
    Blog getConvertBlog(Long id);

    List<List<Blog>> getAllPublishedBlog();

    //获取所有处于发表状态的博客的数目
    int getCountAllPublishedBlog();
}
