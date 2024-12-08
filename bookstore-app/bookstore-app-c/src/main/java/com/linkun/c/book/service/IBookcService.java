package com.linkun.c.book.service;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.book.model.Book;
import com.linkun.c.book.view.BookView;

public interface IBookcService {

    BookView getBookViewById(Long id);

    Book create(Long operatorId, BookDto bookDto) throws BookException;

    boolean modify(Long operatorId, BookDto bookDto) throws BookException;

    void deleteById(Long operatorId, Long id);
}
