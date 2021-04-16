package org.ravic.blog.service.Tag;

import org.ravic.blog.entity.Tag;
import org.ravic.blog.entity.TagAndNum;
import org.ravic.blog.page.Page;

import java.util.List;

public interface ITagService {

    //新增标签
    boolean saveTag(Tag tag);

    //获取单个标签
    Tag getTag(Long id);

    //获取某一页标签
    Page<Tag> listTag(int page, int size);

    //更新标签
    void updateTag(Tag tag);

    //删除标签
    void deleteTag(Long id);

    //获取所有标签
    List<Tag> getAllTag();

    //获取数量最多的size个TagAndNum对象List
    List<TagAndNum> listTagTop(int size);
}
