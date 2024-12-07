package com.linkun.api.book.service;

import com.linkun.book.model.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
public interface IBookService extends IService<Book> {

    Book getByName(String bookname);

    /**
     * 统计同名的书本数量
     * @param bookname 书本名称
     * @param excludeId 排除该id的书
     * @return
     */
    int countByNameExcludeId(String bookname, Long excludeId);
}
