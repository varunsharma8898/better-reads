package com.varun.selfstudy.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookResult {

    private String key;

    private int first_publish_year;

    private String cover_i;

    private String title;

    private List<String> author_name;

    private List<String> author_key;
}
