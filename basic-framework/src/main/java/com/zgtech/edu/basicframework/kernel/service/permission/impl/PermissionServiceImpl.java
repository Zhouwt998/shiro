package com.zgtech.edu.basicframework.kernel.service.permission.impl;

import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;
import com.zgtech.edu.basicframework.kernel.service.BaseService;
import com.zgtech.edu.basicframework.kernel.service.permission.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Override
    public List<Permission> findAllPermission() {
        return permissionDao.findAllPermission();
    }
}
