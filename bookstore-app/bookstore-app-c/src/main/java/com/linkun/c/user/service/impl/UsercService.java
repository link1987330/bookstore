package com.linkun.c.user.service.impl;

import com.linkun.api.user.remote.IUserRemoteService;
import com.linkun.c.user.service.IUsercService;
import com.linkun.c.user.view.UserView;
import com.linkun.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsercService implements IUsercService {

    @Autowired
    private IUserRemoteService userRemoteService;


    @Override
    public UserView getUserByUserId(Long userId) {
        if(userId == null || userId <= 0) {
            return null;
        }
        User user = userRemoteService.getById(userId);
        if (user == null) {
            return null;
        }

        return new UserView(user);
    }

    @Override
    public UserView getByPhone(String phone) {
        return null;
    }
}
