package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ReviewRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

public interface UserService {
    SimpleResponse addReview(ReviewRequest request);
}