package org.ravic.blog.service.Comment.impl;

import org.ravic.blog.entity.Comment;
import org.ravic.blog.mapper.CommentMapper;
import org.ravic.blog.service.Comment.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * 保存一条评论
     * @param comment
     */
    @Transactional
    @Override
    public void saveComment(Comment comment){
        //为comment设置创建事件
        comment.setCreateTime(new Date());
        //为comment设置头像资源
        comment.setAvatar("../image/avatar.jpg");
        commentMapper.saveComment(comment);
    }

    /**
     * 获取所有顶级评论,且顶级评论作为根节点下的所有评论都传入顶级评论的commentList中
     * 并且所有评论的parentComment成员都进行了配置
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //获取指定博客下的所有顶级评论,已经设置了rootId
        List<Comment> topCommentList = commentMapper.listTopCommentByBlogId(blogId);
        //上级评论列表,从顶级评论开始
        List<Comment> firstCommentList = topCommentList;
        while(firstCommentList.size()!=0){
            //下级评论列表
            List<Comment> secondCommentList = new ArrayList<>();
            for(Comment firstComment : firstCommentList){
                HashMap<String,Object> map = new HashMap<>();
                map.put("blogId",blogId);
                map.put("cid",firstComment.getId());
                List<Comment> list = commentMapper.listCommentByCidAndBlogId(map);

                //如果list长度不为0,给list中的所有comment中parentComment设置为firstComment
                if(list.size() != 0){
                    for(Comment comment : list){
                        comment.setParentComment(firstComment);
                        //将comment的rootId设置为firstComment的rootId,保证根节点的信息能够传承下去
                        comment.setRootId(firstComment.getRootId());
                    }
                    //应该将list放到所属的根节点顶级评论的commentList中去
                    Comment topComment = getTopCommentByRootId(firstComment.getRootId(),topCommentList);
                    topComment.getCommentList().addAll(list);
                    //将list添加到secondCommentList中去
                    secondCommentList.addAll(list);
                }
            }
            //将firstCommentList替换成secondCommentList
            firstCommentList = secondCommentList;
        }
        //将顶级评论下的每个回复list按照createTime进行降序排序
        for(Comment topComment : topCommentList){
            sortCommentListByDate(topComment.getCommentList());
        }
        return topCommentList;
    }

    /**
     * 根据id删除一条评论,并且还需要删除以该id为cid的所有评论
     * @param id
     */
    @Transactional
    @Override
    public void deleteCommentById(Long id){
        //先删除cid=id的回复评论
        commentMapper.deleteCommentByCid(id);
        commentMapper.deleteCommentById(id);
    }

    /**
     * 根据rootId在顶级评论根节点中获取一个Comment
     * @return
     */
    public Comment getTopCommentByRootId(Long rootId, List<Comment> topCommentList){
        for(Comment topComment : topCommentList){
            if(topComment.getId()==rootId){
                return topComment;
            }
        }
        logger.debug("在根节点中找不到合适的");
        return null;
    }

    /**
     * 根据commentList中每个comment的发布时期进行降序排序（最新发布的放在前面）
     * 通俗的说就是回复评论列表的排序方式设置
     * @param commentList
     * @return
     */
    public void sortCommentListByDate(List<Comment> commentList){
        Collections.sort(commentList, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                Date dt1 = o1.getCreateTime();
                Date dt2 = o2.getCreateTime();
                if (dt1.getTime() > dt2.getTime()) {
                    return 1;
                } else if (dt1.getTime() == dt2.getTime()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}
