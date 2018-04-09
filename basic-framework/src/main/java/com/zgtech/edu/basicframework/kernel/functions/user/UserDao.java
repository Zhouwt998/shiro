package com.zgtech.edu.basicframework.kernel.functions.user;

import com.zgtech.edu.basicframework.kernel.model.mapped.User;

public interface UserDao {
    /*查询用户*/
    User fatchUser(String username);
}
