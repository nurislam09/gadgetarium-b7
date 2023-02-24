package com.example.gadgetariumb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMainResponse {
    private Long id;
    private String userReview;
    private String responseOfReview;
    private LocalDateTime reviewTime;
    private Double productGrade;
    private UserMainResponse userMainResponse;

}
