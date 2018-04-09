package com.zgtech.edu.basicframework.kernel.functions.user.impl;

import com.zgtech.edu.basicframework.kernel.functions.BaseDao;
import com.zgtech.edu.basicframework.kernel.functions.user.UserDao;
import com.zgtech.edu.basicframework.kernel.model.mapped.User;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl extends BaseDao implements UserDao{

    @Override
    public User fatchUser(String username) {
        return dao.fetchLinks(dao.fetch(User.class,username),"roles");
    }
}
