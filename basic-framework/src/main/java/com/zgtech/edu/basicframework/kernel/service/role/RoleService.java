package com.zgtech.edu.basicframework.kernel.service.role;

import com.zgtech.edu.basicframework.kernel.model.mapped.Role;

public interface RoleService {

    /*查询角色*/
    Role fetchRole(String rolename);
}
