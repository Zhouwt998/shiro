package com.zgtech.edu.basicframework.kernel.service.role.impl;

import com.zgtech.edu.basicframework.kernel.model.mapped.Role;
import com.zgtech.edu.basicframework.kernel.service.BaseService;
import com.zgtech.edu.basicframework.kernel.service.role.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseService implements RoleService{


    @Override
    public Role fetchRole(String rolename) {
        return roleDao.fetchRole(rolename);
    }
}
