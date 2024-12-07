package com.linkun.api.book.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.linkun.book.model.Book;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

@Data
public class BookDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String bookname;

    private String author;

    private BigDecimal price;

    private BigDecimal sellingPrice;

    public void forwardConvert(Book book) {
        if (Objects.isNull(book)) {
            book = new Book();
        }
        BeanUtils.copyProperties(this, book);
    }

    public Book buildBook() {
        Book book= new Book();
        BeanUtils.copyProperties(this, book);
        return book;
    }

    /**
     * 初id外有任何参数为空则返回true，否则为false
     */
    public boolean checkAnyParamsNull() {
        return StringUtils.isAnyBlank(bookname, author) || price == null || sellingPrice == null;
    }

    /**
     * 初id外有任何参数为非空则返回true，否则为false
     */
    public boolean checkAnyParamsNotNull() {
        return StringUtils.isNotBlank(bookname) || StringUtils.isNotBlank(author) || price != null || sellingPrice != null;
    }
}
