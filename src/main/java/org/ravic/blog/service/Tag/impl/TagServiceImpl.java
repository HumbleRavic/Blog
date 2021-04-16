package org.ravic.blog.service.Tag.impl;

import org.ravic.blog.entity.Tag;
import org.ravic.blog.entity.TagAndNum;
import org.ravic.blog.mapper.TagMapper;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Tag.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class TagServiceImpl implements ITagService {
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public boolean saveTag(Tag tag) {
        //判断新增的分类名字在数据库是否唯一
        int num = tagMapper.getNumByTagName(tag.getName());
        if(num == 0){
            //DAO层只需要用到type对象的name
            tagMapper.saveTag(tag);
            return true;
        }else {
            return false;
        }
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(int page, int size) {
        Page<Tag> tagPage = new Page<>();
        tagPage.setSize(size);
        tagPage.setPage(page);
        Long total = tagMapper.count();
        tagPage.setTotal(total);
        HashMap<String,Object> map = new HashMap<>();
        map.put("start",(page-1)*size);
        map.put("size",size);
        List<Tag> tagList = tagMapper.findByPage(map);
        tagPage.setRows(tagList);
        int totalPage = new Long(total%size==0?(total/size):(total/size+1)).intValue();
        tagPage.setTotalPage(totalPage);
        if(page==1){
            tagPage.setFirst(true);
        }
        else{
            tagPage.setFirst(false);
        }
        if(page==totalPage){
            tagPage.setLast(true);
        }
        else{
            tagPage.setLast(false);
        }
        return tagPage;
    }

    @Transactional
    @Override
    public void updateTag(Tag tag) {
        tagMapper.updateTag(tag);
    }

    /**
     * 由于存在外键关联到tag的主键上,所以在删除tag时需要采取级联删除
     * @param id
     */
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteFkById(id);
        tagMapper.deleteTag(id);
    }

    @Transactional
    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }

    @Transactional
    @Override
    public List<TagAndNum> listTagTop(int size){
        return tagMapper.listTagTop(size);
    }
}
