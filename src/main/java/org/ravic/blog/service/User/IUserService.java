package org.ravic.blog.service.User;

import org.ravic.blog.entity.User;

public interface IUserService {
    //检查用户是否存在
    User checkUser(String username,String password);
}
