package com.example.commercepjt.review.dto.response;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewListResponse {

    private List<ReviewResponse> reviews;
    private PageInfo pageInfo;
}