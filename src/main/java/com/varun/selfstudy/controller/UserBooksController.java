package com.varun.selfstudy.controller;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.varun.selfstudy.repository.UserBooksRepository;
import com.varun.selfstudy.vo.UserBooks;
import com.varun.selfstudy.vo.UserBooksPrimaryKey;

@Controller
public class UserBooksController {

    @Resource
    private UserBooksRepository userBooksRepository;

    @PostMapping("/user/books")
    public ModelAndView addBookForUser(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody MultiValueMap<String, String> formData,
            Model model
    ) {

        if (principal == null || principal.getAttribute("login") == null) {
            return null;
        }

        String userId = principal.getAttribute("login");
        String bookId = formData.getFirst("bookId");

        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        key.setUserId(userId);
        key.setBookId(bookId);

        System.out.println("formData = " + formData);

        userBooks.setPrimaryKey(key);
        if (formData.getFirst("startedDate") != null) {
            userBooks.setStartedDate(LocalDate.parse(formData.getFirst("startedDate")));
        }
        if (formData.getFirst("completedDate") != null) {
            userBooks.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        }
        if (formData.getFirst("rating") != null) {
            userBooks.setRating(Integer.parseInt(formData.getFirst("rating")));
        }
        if (formData.getFirst("review") != null) {
            userBooks.setReview(formData.getFirst("review"));
        }
        if (formData.getFirst("readingStatus") != null) {
            userBooks.setReadingStatus(formData.getFirst("readingStatus"));
        }

        userBooksRepository.save(userBooks);

        return new ModelAndView("redirect:/books/" + bookId);
    }
}
