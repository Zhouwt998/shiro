package com.zgtech.edu.basicframework.kernel.service.permission;

import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;

import java.util.List;

public interface PermissionService {
    /*查询权限*/
    List<Permission> findAllPermission();
}
