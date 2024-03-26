package com.jobms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clients.ReviewClient;
import com.clients.companyClient;
import com.dto.Company;
import com.dto.Review;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl  implements JobService{
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	private companyClient companyClient;
	
	@Autowired
	private ReviewClient reviewClient;
	
	int attempt=1;

	//private List<Job> jobs = new ArrayList<>();
	//this is to keep tracking of objects
	
	@Override
	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	@Override
	public String addJob(Job job) {
		
		//job.setId(nextId++);
		try {
			 jobRepository.save(job);
			 return "Job Added Succesfully";
		} catch (Exception e) {

			return "Job Not Added Succesfully";
		}
				
	}

	@Override
	//@CircuitBreaker(name = "companybreaker",fallbackMethod = "companybreakerfallbackMethod")
	//@Retry(name = "companybreaker",fallbackMethod = "companybreakerfallbackMethod")
	@RateLimiter(name = "companybreaker",fallbackMethod = "companybreakerfallbackMethod()")
	public Job findById(Long id) {	
		
		System.out.println("Attempt: "+ ++attempt);
		 Job job = jobRepository.findById(id).orElse(null);
		 Company company = companyClient.findById(job.getCompanyid());
		 List<Review> reviews = reviewClient.findAllReviews(job.getCompanyid());
		 job.setCompany(company);
		 job.setReviews(reviews);
		 return job;
	}
	
	
	public List<String> companybreakerfallbackMethod(Exception e){
		List<String> list = new ArrayList<>();
		list.add("Sorry For Inconvience,It Will Not Happen Again");
		return list;
	}

	@Override
	public Boolean deleteJob(Long id) {

		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	

	public Boolean updateJob(Long id, Job updatedJob) {

		Optional<Job> op = jobRepository.findById(id);
		
			if(op.isPresent()) {
				Job jobObj = op.get();
				jobObj.setTitle(updatedJob.getTitle());
				jobObj.setDescription(updatedJob.getDescription());
				jobObj.setMinSalary(updatedJob.getMinSalary());
				jobObj.setMaxSalary(updatedJob.getMaxSalary());
				jobRepository.save(jobObj);
				return  true;
			}
		
		return false;
	}
	

}
