package org.ravic.blog.service.Type;

import org.ravic.blog.entity.Type;
import org.ravic.blog.entity.TypeAndNum;
import org.ravic.blog.page.Page;

import java.util.List;

public interface ITypeService {

    //新增分类
    boolean saveType(Type type);

    //获取单个分类
    Type getType(Long id);

    //获取某一页分类
    Page<Type> listType(int page,int size);

    //更新分类
    void updateType(Type type);

    //删除分类
    void deleteType(Long id);

    //获取所有分类
    List<Type> getAllType();

    //获取数量最多的size个TypeAndNum对象List
    List<TypeAndNum> listTypeTop(int size);
}
