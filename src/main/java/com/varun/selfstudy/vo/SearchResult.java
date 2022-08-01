package com.varun.selfstudy.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchResult {

    private int num_found;

    private List<BookResult> docs;
}
