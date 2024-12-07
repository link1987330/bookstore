package com.linkun.api.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linkun.book.model.Book;
import com.linkun.api.book.mapper.BookMapper;
import com.linkun.api.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Override
    public Book getByName(String bookname) {
        if (StringUtils.isBlank(bookname)) {
            return null;
        }
        final LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookname, bookname).eq(Book::getDefunct, false);

        return this.getOne(wrapper);
    }

    @Override
    public int countByNameExcludeId(String bookname, Long excludeId) {
        if (StringUtils.isBlank(bookname)) {
            return 0;
        }
        final LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookname, bookname).eq(Book::getDefunct, false).ne(Book::getId, excludeId);

        return this.count(wrapper);
    }
}
