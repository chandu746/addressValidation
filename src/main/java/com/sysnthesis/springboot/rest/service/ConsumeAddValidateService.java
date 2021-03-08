package com.sysnthesis.springboot.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sysnthesis.springboot.rest.model.Address;
import com.sysnthesis.springboot.rest.model.Contact;
@Component
public class ConsumeAddValidateService {
	
	@Autowired
	RestTemplate restTemplate;

	
	   public String validateAddress(Contact contact) {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.set("API-Key", "TEST_HZ9ThHFVakBvAUtGtaVEesH2g/Pi1+8gGHnGfk22HqI");
	      HttpEntity<List<Address>> entity = new HttpEntity<List<Address>>(createAddress(contact),headers);
	     //Sandbox url may not work, my assumption is boolean (verified(true)/unverified(false)) 
	      return restTemplate.exchange(
	         "http://api.shipengine.com/v1/addresses/validate", HttpMethod.POST, entity, String.class).getBody();
	   }
	   
	   public List<Address> createAddress(Contact contact){
		   
		   List<Address> adList = new ArrayList<>();
		   Address address = new Address();
		   address.setAddress_line1(contact.getStreet());
		   address.setCity_locality(contact.getCity());
		   address.setState_province(contact.getProvinceState());
		   address.setCountry_code(contact.getCountry());
		   adList.add(address);
		 return   adList;
	   }

}
