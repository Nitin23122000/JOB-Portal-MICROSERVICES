package com.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewMessageDto {

	private Long id;
	private String title;
	private String description;
	private double rating;
	
	private Long companyid;
}
