package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationOrderResponse {
    private int currentPage;
    private int totalPage;
    private List<OrderResponse> orderResponses;
}
