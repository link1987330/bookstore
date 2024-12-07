package com.linkun.c.book.service.impl;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.api.book.remote.IBookRemoteService;
import com.linkun.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkun.c.book.service.IBookService;
import com.linkun.c.book.view.BookView;


@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRemoteService bookRemoteService;


    @Override
    public BookView getBookViewById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        Book book = bookRemoteService.getById(id);
        return book == null ? null : new BookView(book);
    }

    @Override
    public Book create(Long operatorId, BookDto bookDto) throws BookException {
        if (bookDto == null || bookDto.checkAnyParamsNull()) {
            throw new BookException(BookException.ILLEGAL_PARAMS);
        }
        return bookRemoteService.create(operatorId, bookDto);
    }

    @Override
    public boolean modify(Long operatorId, BookDto bookDto) throws BookException {
        if (bookDto == null || bookDto.checkAnyParamsNull()) {
            throw new BookException(BookException.ILLEGAL_PARAMS);
        }
        return bookRemoteService.modify(operatorId, bookDto);
    }

    @Override
    public void deleteById(Long operatorId, Long id) {
        bookRemoteService.deleteById(operatorId, id);
    }
}
