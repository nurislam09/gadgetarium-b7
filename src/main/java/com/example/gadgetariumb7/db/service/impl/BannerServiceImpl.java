package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Banner;
import com.example.gadgetariumb7.db.repository.BannerRepository;
import com.example.gadgetariumb7.db.service.BannerService;
import com.example.gadgetariumb7.dto.request.BannerRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    public SimpleResponse addBanner(BannerRequest bannerRequest) {
        List<Banner> banners = bannerRequest.getImage().stream()
                .map(imageUrl -> {
                    Banner banner = new Banner();
                    banner.setImage(imageUrl);
                    return banner;
                })
                .collect(Collectors.toList());
        bannerRepository.saveAll(banners);
        return new SimpleResponse("Banners added successfully", "ok");
    }
}
