package com.in28minutes.productcatalogueservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource; 
	
	@GetMapping(path="/hello-world")
	public String heloWorld() {
		return  "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldPathVariable() {
		return new HelloWorldBean("HelloWorld");
	}
	
	@GetMapping(path="/hello-world/path-variable/{}")
	public HelloWorldBean helloWorldPAthVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

	/*
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized(
			@RequestHeader(name="Accept-Language", required =false) Locale locale
			) {
		//Get the message based on the locale
		//en = Hello World
		//nl = Goede Morgen
		//fr = Bonjour
		// Store the values in property files(Spring Message Bundles), pick up the values and return them back.
		
		return messageSource.getMessage("good.morning.message", null, "Default Message",locale);
	} */
	

	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized() {
		
		//Get the message based on the locale
		//en = Hello World
		//nl = Goede Morgen
		//fr = Bonjour
		// Store the values in property files(Spring Message Bundles), pick up the values and return them back.
		
		//return messageSource.getMessage("good.morning.message", null, "Default Message",locale);
		
		//It is difficult for each method to get the locale, so get the locale from LocaleContextHanlder.
		return messageSource.getMessage("good.morning.message", null, "Default Message",LocaleContextHolder.getLocale());
	}
}
