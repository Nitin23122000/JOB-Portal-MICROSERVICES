package com.reviewMS;

import java.util.List;

public interface ReviewService {
	
	List<Review> findAllReviews(Long companyId);
	boolean AddReviews(Review review);
	Review getReviewById(Long companyId,Long reviewId);
	boolean UpdateReview(Long reviewId, Review review);
	boolean DeleteReview(Long reviewId);

}
