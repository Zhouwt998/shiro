package com.zgtech.edu.basicframework.kernel.functions.permission.impl;

import com.zgtech.edu.basicframework.kernel.functions.BaseDao;
import com.zgtech.edu.basicframework.kernel.functions.permission.PermissionDao;
import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionDaoImpl extends BaseDao implements PermissionDao {
    @Override
    public List<Permission> findAllPermission() {
        return dao.query(Permission.class,null);
    }
}
