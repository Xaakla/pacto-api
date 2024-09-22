package com.app.pactoapi.commons;

import com.app.pactoapi.configs.security.DbUserDetails;
import com.app.pactoapi.database.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static User loggedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ((DbUserDetails) authentication.getPrincipal()).getUser();
    }
}
