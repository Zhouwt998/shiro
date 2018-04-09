package com.zgtech.edu.basicframework.kernel.model.mapped;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;
import java.util.List;

@Table("nm_role")
public class Role  implements Serializable {
    @Id
    private Integer roleId;

    @Name
    private String rolename;

    @ManyMany(relation = "nm_user_role",from = "roleId",to = "userId")
    private List<User> users;

    @ManyMany(relation = "nm_role_permission",from = "roleId",to = "perId")
    private List<Permission> permissions;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
