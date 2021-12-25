package com.in28min.microservice.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircutBreakerController {
	private Logger logger = 
			LoggerFactory.getLogger(CircutBreakerController.class);
	
	/*
	@GetMapping("/sample-api")
	public String sampleApi() {
		return "Sample API";
	}
	*/ //Sample API
/**
 *	@Retry : By default, if there is any failure in execution of this message, it would be retried 3 times 
 *  @CircuitBreaker(name = "default",fallbackMethod = "hardcodedRespose")
 *   -- watch curl http://localhost:8000/sample-api = Send requests for every 2 seconds
 *   -- watch -n 0.1 curl http://localhost:8000/sample-api = Send 10 request per second
 *   -- The Circut Breaker return the fallback response without calling the method after certain requests.
 *   -- How do I know when the service is up again?
 *   	-- go to: resilience4j.readme.io/docs/circuitbreaker
 *      -- A CircitBreaker can be in 3 states, CLOSED, OPEN and HALF_OPEN
 *      -- CLOSED: Always calling the dependent microservice (Switch to OPEN after a threshold state like 90% of the request are failing )
 *      -- OPEN: It will not call the dependent mictoservice, it will directly return the fallback response
 *      	-- After the configured wait duration, it will switch to HALF_OPEN.
 *      -- HALF_OPEN: A CircuitBreaker will return only percentage of requests to the microservice and 
 *                    the rest of the request it would the hard coded response
 *          -- Send configured percentage of request to microservice and if it gets proper response, then it will go back to CLOSED state
 *             else it will go back to OPEN state.
 *             
 *      -- Check different configuration parameters for CircuitBreaker in the document
 *   
 *  @RateLimiter
 *    --  In 10s we will allow only 1000 calls to the sample-api
 *    -- watch -n 1 curl http://localhost:8000/sample-api = Send request per 1 second
 *  
 *  @Bulkhead
 *    -- In addition to the ratelimiter we can also configure how many concurrent calls are allowed	
 *    
 *    
 */ 
	
	@GetMapping("/sample-api")
  //@Retry(name = "sample-api",fallbackMethod = "hardcodedRespose") 
	/*
   @CircuitBreaker(name = "default",fallbackMethod = "hardcodedRespose") 
   public String sampleApi() {
		logger.info("Sample API call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return forEntity.getBody();
	} */
	/*
	@RateLimiter(name="default")
	public String sampleApi() {
		logger.info("Sample API call received");
		return "sample api";
	} */
	
	@Bulkhead(name="sample-api")
	public String sampleApi() {
		logger.info("Sample API call received");
		return "sample api";
	}
	
	//If the retry fails all the 3 times then only it will fail.
	public String hardcodedRespose(Exception ex) {
		return "fallback-response";
	}
}
