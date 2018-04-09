package com.zgtech.edu.basicframework.kernel.service;

import com.zgtech.edu.basicframework.kernel.functions.food.FoodDao;
import com.zgtech.edu.basicframework.kernel.functions.permission.PermissionDao;
import com.zgtech.edu.basicframework.kernel.functions.role.RoleDao;
import com.zgtech.edu.basicframework.kernel.functions.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    public UserDao userDao;

    @Autowired
    public RoleDao roleDao;

    @Autowired
    public FoodDao foodDao;

    @Autowired
    public PermissionDao permissionDao;
}
