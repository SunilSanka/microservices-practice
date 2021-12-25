package com.in28minutes.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//This class is used to build custom routes
// We can match request on any request parameter
@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRoute(RouteLocatorBuilder builder) {
		//return builder.routes().build(); //This is when we are not customize any routes 
        
		// To build custom routes
		/*
		Function<PredicateSpec, Buildable<Route>> routeFunction 
			= p -> p.path("/get")  // If the path of the request is '/get', then redirect the request to a different URI
					.uri("http://httpbin.org:80");
					
		return builder.routes()
				.route(routeFunction)
				.build();
		
		*/
			
		// To build custom routes
		return builder.routes()
				// We can match request based on Headers / Host / Request Method /Query Parameter
				// We can define predicates and filters
				// Spring Cloud Gateway integrates with Spring Cloud Discovery Client (Load Balancing)
				// We can also do Path Rewriting
                .route(p -> p.path("/get")
							.filters(f -> f
									 .addRequestHeader("MyHeader","MyValue")
									 .addRequestParameter("Param","MyVal")
									 )
							.uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
					.uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
    					.uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-fiegn/**")
    					.uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                		.filters(f -> f.rewritePath(
                			 "/currency-conversion-new/(?<segment>.*)", 
                			 "/currency-conversion-fiegn/${segment}"))
                			/*	"/currency-conversion-new/", 
                				"/currency-conversion-fiegn/")) */
                		.uri("lb://currency-conversion"))
				.build();	
	}
		
}
