package com.linkun.c.book.controller;

import javax.servlet.http.HttpServletRequest;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkun.c.book.service.IBookcService;
import com.linkun.c.book.view.BookView;
import com.linkun.c.core.controller.BaseController;
import com.linkun.c.core.exception.NeedLoginException;
import com.linkun.response.JsonResult;


@RestController
@RequestMapping("books")
public class BookController extends BaseController {

    @Autowired
    private IBookcService bookcService;

    /**
     * 查询用户信息
     *
     * @param request
     * @return
     * @throws NeedLoginException
     */
    @GetMapping("/{id}")
    @RequestMapping(method = RequestMethod.GET)
    public JsonResult<BookView> getById(HttpServletRequest request, @PathVariable Long id)
            throws NeedLoginException {
        Long userId = checkLogin(request);

        return new JsonResult<BookView>().success(bookcService.getBookViewById(id));
    }

    @PostMapping
    public JsonResult<Book> createBook(HttpServletRequest request, @RequestBody BookDto book)
            throws NeedLoginException, BookException {
        Long userId = checkLogin(request);
        return new JsonResult<Book>().success(bookcService.create(userId, book));
    }

    @PutMapping("/{id}")
    public JsonResult modifyBook(HttpServletRequest request, @PathVariable Long id, @RequestBody BookDto book)
            throws NeedLoginException, BookException {
        Long userId = checkLogin(request);
        book.setId(id);
        bookcService.modify(userId, book);
        return JsonResult.success();
    }


    @DeleteMapping("/{id}")
    public JsonResult deleteBook(HttpServletRequest request, @PathVariable Long id)
            throws NeedLoginException, BookException {
        Long userId = checkLogin(request);

        bookcService.deleteById(userId, id);

        return JsonResult.success();
    }


}
