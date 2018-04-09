package com.zgtech.edu.basicframework.kernel.service.user.impl;

import com.zgtech.edu.basicframework.kernel.functions.user.UserDao;
import com.zgtech.edu.basicframework.kernel.model.mapped.Role;
import com.zgtech.edu.basicframework.kernel.model.mapped.User;
import com.zgtech.edu.basicframework.kernel.service.BaseService;
import com.zgtech.edu.basicframework.kernel.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements UserService{


    @Override
    public User fatchUser(String username) {
        User user = userDao.fatchUser(username);
        List<Role> roles = user.getRoles();
        for(Role role : roles){
            role.setPermissions(roleDao.fetchRole(role.getRolename()).getPermissions());
        }
        return user;
    }
}
