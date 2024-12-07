package com.linkun.api.book.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.book.model.Book;


/**
 * <p>
 *  remote类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */

public interface IBookRemoteService{
     /**
     * 根据ID获取详情
     *
     * @param id 主键
     * @return
     */
    Book getById(Long id);

    /**
    * 根据ID删除
    *
    * @param id 主键
    * @param operatorId 操作者
    */
    void deleteById(@Min(1) @NotNull Long operatorId, @Min(1) @NotNull Long id);

    Book create(@Min(1) @NotNull Long operatorId, BookDto bookDto) throws BookException;

    boolean modify(@Min(1) @NotNull Long operatorId, BookDto bookDto) throws BookException;

    Book getByName(String name);

    /**
     * 批量查询书本信息
     * @param bookIds
     * @return
     */
    List<Book> listByIds(List<Long> bookIds);
}

