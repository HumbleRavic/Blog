package org.ravic.blog.service.Blog.impl;

import org.ravic.blog.entity.Blog;
import org.ravic.blog.mapper.BlogMapper;
import org.ravic.blog.page.SearchCondition;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Blog.IBlogService;
import org.ravic.blog.util.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Blog业务层
 */
@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private MarkDownUtils markDownUtils;

    /**
     * 根据用户输入的复合搜索条件
     * @param searchCondition 复合搜索条件
     * @param page 满足搜索条件结果的当前页码
     * @param size 页面大小
     * @return
     */
    @Transactional()
    @Override
    public Page<Blog> listBlog(SearchCondition searchCondition, int page, int size) {
        Page<Blog> blogPage = new Page<>();
        blogPage.setPage(page);
        blogPage.setSize(size);
        //获取满足条件记录的总数
        Long total = blogMapper.getCountByCondition(searchCondition);
        blogPage.setTotal(total);
        //获取满足条件的当前页面的内容
        HashMap<String,Object> map = new HashMap();
        map.put("start",(page-1)*size);
        map.put("size",size);
        map.put("title", searchCondition.getTitle());
        map.put("typeId", searchCondition.getTypeId());
        map.put("tagId",searchCondition.getTagId());
        map.put("published",searchCondition.getPublished());
        map.put("likeTitle",searchCondition.getLikeTitle());
        map.put("recommend", searchCondition.getRecommend());
        List<Blog> blogList = blogMapper.findByPage(map);
        //设置当前页的内容
        blogPage.setRows(blogList);
        //设置总页数
        int totalPage = new Long(total % size == 0 ? (total / size) : (total / size + 1)).intValue();
        blogPage.setTotalPage(totalPage);
        //设置两个判断
        if (page == 1) {
            blogPage.setFirst(true);
        } else {
            blogPage.setFirst(false);
        }
        if (page == totalPage) {
            blogPage.setLast(true);
        } else {
            blogPage.setLast(false);
        }
        return blogPage;
    }

    /**
     * 根据博客id获取包含type和tag较为完整的Blog对象
     * 因为较为完整的Blog对象包括blog关联的type和tag,但是blog有可能没有tag
     * 因此需要加以判断
     * @param id 博客编号
     * @return 查询到的单个博客对象
     */
    @Transactional()
    @Override
    public Blog getBlog(Long id){
        //判断该博客是否关联标签
        int countTag = blogMapper.getCountFromJoinById(id);
        //判断该博客是否关联评论
        int countComment = blogMapper.getCountFromCommentById(id);
        HashMap<String,Object> map = new HashMap<>();
        map.put("countTag",countTag);
        map.put("id",id);
        map.put("countComment",countComment);
        return blogMapper.getBlog(map);
    }

    /**
     * 向blog数据表存储博客
     * 还会收到博客中的标签id,因此要将标签的id加入到blogjointag中
     * @param blog
     */
    @Transactional
    @Override
    public void saveBlog(Blog blog,Integer[] tagIds){
        //为Blog对象条件createTime和updateTime属性值
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        //插入一条新创建的博客,浏览量一定是0
        blog.setViews(0);
        //blog中的id是新增blog后数据表记录的id
        blogMapper.saveBlog(blog);
        //向blogjointag中间表中添加关联blog的id和tag的id的数据
        for(Integer tagId : tagIds){
            HashMap<String,Object> map = new HashMap<>();
            map.put("blogId",blog.getId());
            map.put("tagId",tagId);
            blogMapper.saveBlogJoinTag(map);
        }
    }

    /**
     * 向blog数据表更新已有的博客
     */
    @Transactional
    @Override
    public void updateBlog(Blog blog,Integer[] tagIds){
        //为Blog对象条件updateTime属性值
        blog.setUpdateTime(new Date());
        blogMapper.updateBlog(blog);
        //在blogjointag表中添加新的标签blog关联
        //首先需要先将blogjointag中与该blogId关联的数据全部删除
        blogMapper.deleteJoinByBlogId(blog.getId());
        for(Integer tagId : tagIds){
            HashMap<String,Object> map = new HashMap<>();
            map.put("blogId",blog.getId());
            map.put("tagId",tagId);
            blogMapper.saveBlogJoinTag(map);
        }
    }

    /**
     * 删除博客
     * 级联删除,级联删除blog和blogjointag中的数据
     * 但是需要考虑如果如果该博客没有关联标签
     * 还需要考虑要级联删除与blog关联的comment
     * @param id
     */
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        //判断该博客是否关联标签
        int countTag = blogMapper.getCountFromJoinById(id);
        //判断该博客是否关联了评论
        int countComment = blogMapper.getCountFromCommentById(id);
        if(countTag != 0){
            blogMapper.deleteFkById(id);
        }
        if(countComment != 0){
            blogMapper.deleteCommentByBlogId(id);
        }
        blogMapper.deleteBlog(id);
    }

    /**
     * 获取size个数据库中最新的推荐博客
     * @param size
     * @return
     */
    @Transactional
    @Override
    public List<Blog> listBlogTop(int size){
        return blogMapper.listBlogTop(size);
    }

    /**
     * 根据blog的id获取blog内容转换后的blog对象
     * 为该blog的views做+1
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getConvertBlog(Long id){
        Blog blog = getBlog(id);
        String content = blog.getContent();
        String convertContent = markDownUtils.markdownToHtmlExtensions(content);
        blog.setContent(convertContent);
        //views+1
        blogMapper.updateViewsById(id);
        return blog;
    }

    /**
     * 获取blog表中所有处于发表状态的博客,并将这些博客根据发表的年和月进行排布
     * @return
     */
    @Transactional
    @Override
    public List<List<Blog>> getAllPublishedBlog(){
        List<Blog> blogList = blogMapper.getAllPublishedBlog();
        List<List<Blog>> yearList = new ArrayList<>();
        List<Blog> listNowUsing = null;
        List<Blog> monthList = new ArrayList<>();
        if(blogList.size()!=0){
            //如果获取到的所有博客数量不是0的话,先将第一个blog放到monthList中
            monthList.add(blogList.get(0));
        }else{
            return yearList;
        }
        //设置当前使用的list为此时的monthList
        listNowUsing = monthList;
        for(int i=1;i<blogList.size();i++){
            //比较当前选中的blog和上一个blog的年份是否一样
            if(isYearSame(blogList,i)){
                //如果一样就将当前选中的blog加入listNowUsing(也就是当前正在使用的月List)
                listNowUsing.add(blogList.get(i));
            }else{
                //如果不一样,需要先将当前使用的listNowUsing加入yearList表示一年的blog已经汇总完毕
                //然后创建新的monthList,并让listNowUsing指向这个新创建的monthList,将当前选中的blog加入新创建的listNowUsing
                yearList.add(listNowUsing);
                monthList = new ArrayList<>();
                listNowUsing = monthList;
                listNowUsing.add(blogList.get(i));
            }
        }
        //将最后一组listNowUsing也添加到yearList中去
        yearList.add(listNowUsing);
        return yearList;
    }

    /**
     * 获取所有已经发表的博客的数目
     * @return
     */
    @Transactional
    @Override
    public int getCountAllPublishedBlog(){
        return blogMapper.getAllPublishedBlog().size();
    }

    /**
     * 比较bloglist中当前index的blog和index-1的blog的createTime属性的年份是否相同
     * @param blogList
     * @param index
     * @return
     */
    public boolean isYearSame(List<Blog> blogList, int index){
        Blog blogNow = blogList.get(index);
        Blog blogPre = blogList.get(index-1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String yearNow = format.format(blogNow.getCreateTime());
        String yearPre = format.format(blogPre.getCreateTime());
        return yearNow.equals(yearPre);
    }

}
