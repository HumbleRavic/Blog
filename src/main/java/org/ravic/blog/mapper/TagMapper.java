package org.ravic.blog.mapper;

import org.ravic.blog.entity.Tag;
import org.ravic.blog.entity.TagAndNum;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TagMapper {
    void saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> findByPage(HashMap<String,Object> map);

    Long count();

    void updateTag(Tag tag);

    void deleteFkById(Long id);

    void deleteTag(Long id);

    //获取指定名字的标签数目
    int getNumByTagName(String name);

    List<Tag> getAllTag();

    List<TagAndNum> listTagTop(int size);
}
