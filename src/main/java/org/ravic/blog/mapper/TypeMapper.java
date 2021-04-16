package org.ravic.blog.mapper;

import org.ravic.blog.entity.Type;
import org.ravic.blog.entity.TypeAndNum;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TypeMapper {
    void saveType(Type type);

    Type getType(Long id);

    List<Type> findByPage(HashMap<String,Object> map);

    Long count();

    void updateType(Type type);

    void deleteType(Long id);

    //获取到一个属于该type的blogId数组
    Long[] getIdFromBlogFk(Long id);

    //获取指定名字的分类数目
    int getNumByTypeName(String name);

    List<Type> getAllType();

    //根据size大小获取分类下博客最多的几个分类
    //用TypeAndName的List来接收
    List<TypeAndNum> listTypeTop(int size);
}
