package com.example.gadgetariumb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long id;
    private String responseOfReview;

    public ReviewRequest(String responseOfReview) {
        this.responseOfReview = responseOfReview;
    }
}
