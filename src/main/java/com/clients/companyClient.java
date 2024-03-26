package com.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.Company;


@FeignClient(name = "COMPANY-MICROSERVICE",url = "${company-microservice.url}")
public interface companyClient {

	@GetMapping("/company/GetAllCompanies/{id}")
	Company findById(@PathVariable("id") Long id);
}
