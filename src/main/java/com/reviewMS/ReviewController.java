package com.reviewMS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.config.ReviewMessageProducer;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewMessageProducer reviewMessageProducer; 
	
	@GetMapping("/GetAllReviews")
	public ResponseEntity<List<Review>> findAllReviews(@RequestParam Long companyId){
		return new ResponseEntity<>(reviewService.findAllReviews(companyId),HttpStatus.OK);
	}
	
	@PostMapping("/AddReviews")
	public ResponseEntity<String> AddReviews(@RequestBody Review review){
		boolean status = reviewService.AddReviews(review);
		if(status) {
			this.reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<>("Review Added Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>("Review Not Added Successfully",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/GetReview/{reviewId}")
	public ResponseEntity<Review> getReviewById(@RequestParam Long companyId,@PathVariable("reviewId") Long reviewId ){
		 
		Review reviewResult = reviewService.getReviewById(companyId,reviewId);
		if(reviewResult!= null) {
			return new ResponseEntity<>(reviewResult,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	}
	
	@PutMapping("/UpdateReview/{reviewId}")
	public ResponseEntity<String> UpdateReview(@PathVariable("reviewId") Long reviewId,@RequestBody Review review ){
		
		boolean result = reviewService.UpdateReview(reviewId,review);
		if(result) {
			return new ResponseEntity<>("Review Updated Sucessfully",HttpStatus.OK);
		}
		return new ResponseEntity<>("Review Not Updated",HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/DeleteReview/{reviewId}")
	public ResponseEntity<String> DeleteReview(@PathVariable Long reviewId) {
		
		boolean result = reviewService.DeleteReview(reviewId);
		if(result) {
			return new ResponseEntity<>("Review Deleted Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>("Review Not Deleted Successfully",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/averageRating")
	public Double getAvgRating(@RequestParam Long companyId) {
		List<Review> reviews = this.reviewService.findAllReviews(companyId);
		
		 double OverallRating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
		 System.out.println("Over All Rating : "+OverallRating);
		 return OverallRating;
	}
}
