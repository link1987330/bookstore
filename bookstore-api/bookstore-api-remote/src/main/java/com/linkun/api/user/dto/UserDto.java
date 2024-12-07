package com.linkun.api.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import com.linkun.user.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author linkun
 * @since 2024-12-05
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private String password;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private Boolean defunct;

    public void forwardConvert(User user) {
        if (Objects.isNull(user)) {
            return;
        }
        BeanUtils.copyProperties(this, user);
    }
}
