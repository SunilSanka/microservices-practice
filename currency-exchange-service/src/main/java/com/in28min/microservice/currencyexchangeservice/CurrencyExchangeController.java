package com.in28min.microservice.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveCurrencyExchage(
			@PathVariable String from,
			@PathVariable String to) {
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L,"USD","INR", BigDecimal.valueOf(50.0));
		CurrencyExchange currencyExchange  = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for "+from+" to "+to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange; 	
	}
}