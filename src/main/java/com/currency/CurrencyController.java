package com.currency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/currency")
	public ResponseObject getCurrency() throws JsonProcessingException {

		List<Double> uk = new ArrayList<>();
		List<Double> usa = new ArrayList<>();
		List<Double> hk = new ArrayList<>();
		ResponseObject response = new ResponseObject();
		for (int i = 0; i < 6; i++) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -i);

			Date todayDate = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			String requiredDate = sdf.format(todayDate);

			Example e = restTemplate.getForObject("https://api.ratesapi.io/api/" + requiredDate, Example.class);

			uk.add(e.getRates().getGBP());

			usa.add(e.getRates().getUSD());

			hk.add(e.getRates().getHKD());

		}
		response.setUk(uk);
		response.setUsa(usa);
		response.setHk(hk);
		printJson(response);
		return response;
	}

	@GetMapping("/singleObject")
	public Example getSingleObject() throws JsonProcessingException {

		Example e = restTemplate.getForObject("https://api.ratesapi.io/api/1999-01-17", Example.class);
		printJson(e);
		return e;
	}

	public void printJson(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		if(obj instanceof Example) {
			System.out.println(mapper.writeValueAsString(obj));
		}
		else if(obj instanceof ResponseObject) {
			System.out.println(mapper.writeValueAsString(obj));
		}
		

	}
}
