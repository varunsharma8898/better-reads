package com.varun.selfstudy.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.varun.selfstudy.repository.BookRepository;
import com.varun.selfstudy.repository.UserBooksRepository;
import com.varun.selfstudy.util.ImageSize;
import com.varun.selfstudy.util.Util;
import com.varun.selfstudy.vo.Book;
import com.varun.selfstudy.vo.UserBooks;
import com.varun.selfstudy.vo.UserBooksPrimaryKey;

// Thymeleaf doesn't work with RestController, must use Controller here.
// The reason is that Thymeleaf is used for server-side rendering.
// RestController is for transferring data through rest (ex. json data).
// If you have some client-side rendering ui, use rest apis to provide data.
@Controller
public class BooksController {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private UserBooksRepository userBooksRepository;

    @GetMapping("/books/{bookId}")
    public String getBookById(@AuthenticationPrincipal OAuth2User principal, @PathVariable String bookId, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            String coverImageId = null;
            if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                coverImageId = book.getCoverIds().get(0);
            }
            String imageUrl = Util.getImageUrlForCoverId(coverImageId, ImageSize.LARGE);
            model.addAttribute("coverImage", imageUrl);
            model.addAttribute("book", book);

            if (principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);

                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setBookId(book.getId());
                key.setUserId(userId);
                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                if (userBooks.isPresent()) {
                    model.addAttribute("userBooks", userBooks.get());
                } else {
                    model.addAttribute("userBooks", new UserBooks());
                }
            }
            return "book";
        }
        return "book-not-found";
    }

}
