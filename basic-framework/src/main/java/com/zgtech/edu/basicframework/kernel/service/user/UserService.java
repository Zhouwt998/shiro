package com.zgtech.edu.basicframework.kernel.service.user;

import com.zgtech.edu.basicframework.kernel.model.mapped.User;

public interface UserService {

    /*查询用户*/
    User fatchUser(String username);
}
