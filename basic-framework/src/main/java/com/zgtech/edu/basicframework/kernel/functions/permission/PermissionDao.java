package com.zgtech.edu.basicframework.kernel.functions.permission;

import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;

import java.util.List;

public interface PermissionDao {
    /*查询权限*/
    List<Permission> findAllPermission();
}
