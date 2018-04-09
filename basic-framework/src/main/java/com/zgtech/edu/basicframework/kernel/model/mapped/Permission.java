package com.zgtech.edu.basicframework.kernel.model.mapped;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;
import java.util.List;

@Table("nm_permission")
public class Permission implements Serializable {
    @Id
    private Integer perId;
    @Name
    private String permission;

    private String perUrl;

    @ManyMany(relation = "nm_role_permission",from = "perId",to = "roleId")
    private List<Role> roles;

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
