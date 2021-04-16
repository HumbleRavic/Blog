package org.ravic.blog.mapper;

import org.ravic.blog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * 用户user的mapper接口
 */
@Repository
public interface UserMapper {
    User getUserByUsernameAndPwd(HashMap map);
}
