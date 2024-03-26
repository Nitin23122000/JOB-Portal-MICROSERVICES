package com.jobms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	private JobService jobservice;
	
	@GetMapping("/jobs")
	public ResponseEntity<List<Job>> findAll(){
		List<Job> findAll = jobservice.findAll();
		return new ResponseEntity<List<Job>>(findAll,HttpStatus.OK);
		
	}
	
	@PostMapping("/addjob")
	public ResponseEntity<String> addJob(@RequestBody Job job) {
		 String addJob = jobservice.addJob(job);
		 return new ResponseEntity<String>(addJob,HttpStatus.OK);
	}
	
	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> findById(@PathVariable("id") Long id) {
		
		Job findById = jobservice.findById(id);
		if(findById != null) {
			return new ResponseEntity<>(findById,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deletejob/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable("id") Long id){
		
		Boolean deleteJob = jobservice.deleteJob(id);
		if(deleteJob) {
			return new ResponseEntity<String>("Job Deleted Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/updateJob/{id}")
	public ResponseEntity<String> updateJob(@PathVariable("id") Long id,
											@RequestBody Job updatedJob ){
		
		Boolean updateJob = jobservice.updateJob(id,updatedJob);
		if(updateJob) {
			return new ResponseEntity<String>("Job Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Job Not Updated Successfully",HttpStatus.NOT_MODIFIED);
	}
}
	