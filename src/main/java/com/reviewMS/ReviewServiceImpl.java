package com.reviewMS;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;


@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository  reviewRepository;  
	
	
	
	
	@Override
	public List<Review> findAllReviews(Long companyId) {
		return reviewRepository.findByCompanyid(companyId);
	}

	@Override
	public boolean AddReviews( Review review) {

		if(review!= null) {
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	
	//very Important API for learn 
	@Override
	public Review getReviewById(Long companyId, Long reviewId) {
		
		 List<Review> reviews = reviewRepository.findByCompanyid(companyId);
		 return reviews.stream().filter(review->review.getId().equals(reviewId)).findFirst().orElse(null);
	}

	@Override
	public boolean UpdateReview( Long reviewId , Review review) {
		Review rvw = this.reviewRepository.findById(reviewId).orElse(null);
		if(reviewId!=null && review!=null) {
			rvw.setTitle(review.getTitle());
			rvw.setDescription(review.getDescription());
			rvw.setRating(review.getRating());
			rvw.setCompanyid(review.getCompanyid());
			reviewRepository.save(rvw);
			return true;
		}
		return false;
	}

	@Override
	public boolean DeleteReview( Long reviewId) {
		
		if(reviewRepository.existsById(reviewId)) {
			
			Review review = reviewRepository.findById(reviewId).orElse(null);
			reviewRepository.delete(review);
			return true;
		}
		return false;
	}
	
}
