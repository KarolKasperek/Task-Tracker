package com.taskTracker.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.taskTracker.enums.UserPermission.*;

public enum Role {
    ADMIN(Sets.newHashSet(SECURITY_CONFIGURATION_ACCESS, EDIT_WORKBENCH)),
    USER(Sets.newHashSet(EDIT_WORKBENCH));
    private final Set<UserPermission> permissions;

    Role(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }
}
