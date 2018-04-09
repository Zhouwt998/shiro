package com.zgtech.edu.basicframework.kernel.functions.role.impl;

import com.zgtech.edu.basicframework.kernel.functions.BaseDao;
import com.zgtech.edu.basicframework.kernel.functions.role.RoleDao;
import com.zgtech.edu.basicframework.kernel.model.mapped.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleDaoImpl extends BaseDao implements RoleDao {
    @Override
    public Role fetchRole(String rolename) {
        return dao.fetchLinks(dao.fetch(Role.class,rolename),"permissions");
    }
}
