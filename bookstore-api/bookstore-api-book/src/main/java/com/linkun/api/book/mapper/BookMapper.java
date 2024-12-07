package com.linkun.api.book.mapper;

import com.linkun.book.model.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
 @Mapper
public interface BookMapper extends BaseMapper<Book> {

}
