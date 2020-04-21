package com.microservice.currencycalculationservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.currencycalculationservice.domain.CurrencyExchangeValue;
import com.microservice.currencycalculationservice.dto.ExchangeValue;

@RestController
@RequestMapping(value = "/api/currency-calculation-service")
public class CurrencyConvertionController {

	@GetMapping(value = "/from/{from}/to/{to}/quality/{quantity}")
	public CurrencyExchangeValue fetchCurrencyConvertion(@PathVariable String from, @PathVariable String to,
			@PathVariable Double quantity) {

		// Using ResTemplate
		Map<String, String> responseType = new HashMap<>();
		responseType.put("from", from);
		responseType.put("to", to);

		String url = "http://localhost:2222/api/currency-exchange-service/from/{from}/to/{to}";
		ResponseEntity<ExchangeValue> forEntity = new RestTemplate().getForEntity(url, ExchangeValue.class,
				responseType);
		ExchangeValue obj = forEntity.getBody(); 
		
		return new CurrencyExchangeValue(obj.getId(), obj.getFrom(), obj.getTo(),
				obj.getConversionMultiple(), quantity, obj.getConversionMultiple() * quantity, obj.getPort());
	}
	
}