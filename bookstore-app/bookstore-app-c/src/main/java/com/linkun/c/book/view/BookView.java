package com.linkun.c.book.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.linkun.book.model.Book;
import com.linkun.user.model.User;

import io.swagger.annotations.ApiModelProperty;
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
public class BookView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String bookname;

    private String author;

    private BigDecimal price;

    private BigDecimal sellingPrice;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private Boolean defunct;

    public BookView() {}

    public BookView(Book book) {
        if (book != null) {
            BeanUtils.copyProperties(book, this);
        }
    }

}
