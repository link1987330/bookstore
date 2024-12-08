package com.linkun.c.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.linkun.c.core.controller.BaseController;
import com.linkun.c.user.service.IUserService;
import com.linkun.c.user.view.UserView;
import com.linkun.c.core.exception.NeedLoginException;
import com.linkun.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 查询用户信息
     *
     * @param request
     * @return
     * @throws NeedLoginException
     */
    @RequestMapping(value = "v1/info", method = RequestMethod.GET)
    public JsonResult<UserView> add(HttpServletRequest request)
            throws NeedLoginException {
        Long userId = checkLogin(request);

        return new JsonResult<UserView>().success(userService.getUserByUserId(userId));
    }

}
