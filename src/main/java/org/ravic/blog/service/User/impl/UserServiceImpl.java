package org.ravic.blog.service.User.impl;

import org.ravic.blog.entity.User;
import org.ravic.blog.mapper.UserMapper;
import org.ravic.blog.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class UserServiceImpl implements IUserService {
    //操作user数据表的DAO层对象
    @Autowired
    private UserMapper userMapper;

    /**
     * 在数据库中检查用户是否存在
     * @param username
     * @param password
     * @return
     */
    @Transactional
    @Override
    public User checkUser(String username, String password) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        User user = userMapper.getUserByUsernameAndPwd(map);
        return user;
    }
}
