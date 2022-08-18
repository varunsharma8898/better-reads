package com.varun.selfstudy.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.varun.selfstudy.util.ImageSize;
import com.varun.selfstudy.util.Util;
import com.varun.selfstudy.vo.BookResult;
import com.varun.selfstudy.vo.SearchResult;

@Controller
public class SearchController {

    @Value("${better.reads.search.api.url:http://openlibrary.org/search.json}")
    private String searchApiUrl;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {

        if (StringUtils.hasText(query)) {
            SearchResult searchResult = restTemplate.getForObject(getSearchUrl(query), SearchResult.class);
            List<BookResult> bookResults = searchResult.getDocs()
                    .stream()
                    .map(bookResult -> {
                        bookResult.setKey(bookResult.getKey().replace("/works/", ""));

                        String imageUrl = Util.getImageUrlForCoverId(bookResult.getCover_i(), ImageSize.MEDIUM);
                        bookResult.setCover_i(imageUrl);
                        return bookResult;
                    })
                    .collect(Collectors.toList());

            model.addAttribute("bookResults", bookResults);
            return "search";
        }

        return "search-error";
    }

    private String getSearchUrl(String query) {
        return searchApiUrl + "?q=" + query;
    }
}
