package com.test.emailservice.modules.auth.threads;


import com.test.emailservice.modules.users.entities.User;

public class AuthUserThread extends ThreadLocal<User>  {
    private static  final ThreadLocal<User> authUserContext = new ThreadLocal<>();
    private static User userResource;

    public static final User  getContext() {
        return authUserContext.get();
    }

    public static final void setContext(User userResource) {
        authUserContext.set(userResource);
    }

    public static final void clearContext() {
        authUserContext.remove();
    }
}
