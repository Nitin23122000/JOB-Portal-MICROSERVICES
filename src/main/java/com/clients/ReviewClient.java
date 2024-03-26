package com.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.Review;

@FeignClient(name = "REVIEW-MICROSERVICE",url = "${review-microservice.url}")
public interface ReviewClient {

	@GetMapping("/review/GetAllReviews")
	 List<Review> findAllReviews(@RequestParam("companyId") Long companyId);
}
