package com.test.emailservice.modules.users.presenters;


import java.util.ArrayList;
import java.util.List;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.resources.UserResource;
import java.sql.SQLException;

public class UserPresenter {

    public List<UserResource> map(List<User> userList) throws SQLException {

        List<UserResource> userResourcelist = new ArrayList<>();
        userList.forEach(user->{
            userResourcelist.add(UserResource.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .website(user.getWebsite())
                    .mobileNumber(user.getMobileNumber())
                    .build()
            );
        });
        return userResourcelist;
    }
}
