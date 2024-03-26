package com.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.dto.ReviewMessageDto;
import com.reviewMS.Review;

@Service
public class ReviewMessageProducer {

	
	private RabbitTemplate rabbitTemplate;

	public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}
	
	
	public void sendMessage(Review review) {
		ReviewMessageDto messageDto = new ReviewMessageDto();
		messageDto.setId(review.getId());
		messageDto.setTitle(review.getTitle());
		messageDto.setDescription(review.getDescription());
		messageDto.setRating(review.getRating());
		messageDto.setCompanyid(review.getCompanyid());
		 rabbitTemplate.convertAndSend( "companyRatingQueue",messageDto);
	}
}
