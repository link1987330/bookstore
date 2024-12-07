package com.linkun.c.user.view;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.linkun.user.model.User;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
@Data
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private Boolean defunct;

    public UserView () {};

    public UserView(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.createTime = user.getCreateTime();
        this.createUserId = user.getCreateUserId();
        this.updateTime = user.getUpdateTime();
        this.updateUserId = user.getUpdateUserId();
    }

}
