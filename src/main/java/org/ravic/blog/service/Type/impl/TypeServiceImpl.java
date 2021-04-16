package org.ravic.blog.service.Type.impl;

import org.ravic.blog.entity.Type;
import org.ravic.blog.entity.TypeAndNum;
import org.ravic.blog.mapper.BlogMapper;
import org.ravic.blog.mapper.TypeMapper;
import org.ravic.blog.page.Page;
import org.ravic.blog.service.Blog.impl.BlogServiceImpl;
import org.ravic.blog.service.Type.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogServiceImpl blogService;

    /**
     *
     * @param type
     * @return 如果添加分类成功返回true,否则返回false
     */
    @Transactional
    @Override
    public boolean saveType(Type type) {
        //判断新增的分类名字在数据库是否唯一
        int num = typeMapper.getNumByTypeName(type.getName());
        if(num == 0){
            //DAO层只需要用到type对象的name
            typeMapper.saveType(type);
            return true;
        }else {
            return false;
        }
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    /**
     * 获取页级内容
     * @param page 当前页码
     * @param size 页面大小
     * @return
     */
    @Transactional
    @Override
    public Page<Type> listType(int page, int size) {
        Page<Type> typePage = new Page<>();
        //设置页面大小
        typePage.setSize(size);
        //设置当前页码
        typePage.setPage(page);
        //设置记录总数
        Long total = typeMapper.count();
        typePage.setTotal(total);
        //获取当前页面内容
        HashMap<String,Object> map = new HashMap();
        map.put("start",(page-1)*size);
        map.put("size",size);
        List<Type> typeList = typeMapper.findByPage(map);
        //设置当前页面内容
        typePage.setRows(typeList);
        //设置总页数
        int totalPage = new Long(total%size==0?(total/size):(total/size+1)).intValue();
        typePage.setTotalPage(totalPage);
        //设置两个判断
        if(page==1){
            typePage.setFirst(true);
        }
        else{
            typePage.setFirst(false);
        }
        if(page==totalPage){
            typePage.setLast(true);
        }
        else{
            typePage.setLast(false);
        }
        return typePage;
    }

    @Transactional
    @Override
    public void updateType(Type type) {
        typeMapper.updateType(type);
    }

    /**
     * type的主键关联了blog的typeId外键,需要级联删除
     * 而因为要删除blog但是blog中主键id与blogjointag中的blogId相关联
     * 因此删除顺序为blogjointag(这个typeId在blog中对应的id)-->blog(typeid)-->type(id)
     * @param id 删除type的编号
     */
    @Transactional
    @Override
    public void deleteType(Long id) {
        Long[] blogIds = typeMapper.getIdFromBlogFk(id);
        for(Long blogId : blogIds){
            blogService.deleteBlog(blogId);
        }
         typeMapper.deleteType(id);
    }

    @Transactional
    @Override
    public List<Type> getAllType(){
        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public List<TypeAndNum> listTypeTop(int size){
        return typeMapper.listTypeTop(size);
    }
}
