package com.linkun.api.book.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.book.model.Book;
import com.linkun.api.book.service.IBookService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.linkun.utils.NumberUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  remote服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class BookRemoteService implements IBookRemoteService {

    @Autowired
    private IBookService bookService;

    @Override
    public Book getById(Long id) {
        if (NumberUtils.isInvalid(id)) {
            return null;
        }
        return bookService.getById(id);
    }

    @Override
    public void deleteById(Long operatorId,Long id) {
        Book book = new Book();
        book.setDefunct(true);
        book.setId(id);
        book.setUpdateTime(new Date());
        book.setUpdateUserId(operatorId);
        bookService.updateById(book);
    }

    @Override
    public Book create(@Min(1) @NotNull Long operatorId, BookDto bookDto) throws BookException {
        if(operatorId == null || bookDto == null || bookDto.checkAnyParamsNull()) {
            throw new BookException(BookException.ILLEGAL_PARAMS);
        }
        // 检查是否已存在
        Book book = bookService.getByName(bookDto.getBookname());
        if (book != null) {
            throw new BookException(BookException.BOOK_EXISTENCE);
        }

        book = bookDto.buildBook();
        book.setCreateUserId(operatorId);
        book.setUpdateUserId(operatorId);

        bookService.save(book);

        return book;
    }

    @Override
    public boolean modify(@Min(1) @NotNull Long operatorId, BookDto bookDto) throws BookException {
        if (bookDto == null || bookDto.getId() == null || !bookDto.checkAnyParamsNotNull()) {
            throw new BookException(BookException.ILLEGAL_PARAMS);
        }

        // 校验书名
        if (StringUtils.isNotBlank(bookDto.getBookname()) && bookService.countByNameExcludeId(bookDto.getBookname(), bookDto.getId()) > 0) {
            throw new BookException(BookException.BOOK_EXISTENCE);
        }
        Book book = bookDto.buildBook();
        book.setUpdateUserId(operatorId);

        return bookService.updateById(book);
    }

    @Override
    public Book getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return bookService.getByName(name);
    }

    @Override
    public List<Book> listByIds(List<Long> bookIds) {
        if (CollectionUtils.isEmpty(bookIds)) {
            return Collections.emptyList();
        }
        return bookService.listByIds(bookIds);
    }


}

