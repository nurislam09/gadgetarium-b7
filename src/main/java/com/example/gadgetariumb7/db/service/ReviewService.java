package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

   private final  ReviewRepository reviewRepository;



}
