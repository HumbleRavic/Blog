package org.ravic.blog.mapper;

import org.ravic.blog.entity.Test;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {
    Test getTestById(int id);

    Test getTestByRecommend(Boolean recommend);
}
