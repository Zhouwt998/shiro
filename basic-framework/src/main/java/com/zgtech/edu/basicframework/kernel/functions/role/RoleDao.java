package com.zgtech.edu.basicframework.kernel.functions.role;

import com.zgtech.edu.basicframework.kernel.model.mapped.Role;

public interface RoleDao {

    /*查询角色*/
    Role fetchRole(String rolename);
}
