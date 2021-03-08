package com.sysnthesis.springboot.rest.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.sysnthesis.springboot.rest.exception.ContactNotFoundException;
import com.sysnthesis.springboot.rest.model.Contact;
import com.sysnthesis.springboot.rest.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ConsumeAddValidateService consumeWebService;
	
	public List<Contact> retrieveAllContacts() {
		return contactRepository.findAll();
	}

	public EntityModel<Contact> retrieveContact(long id) {
		Optional<Contact> contact = contactRepository.findById(id);

		if (!contact.isPresent())
			throw new ContactNotFoundException("id-" + id);
		

		EntityModel<Contact> resource = EntityModel.of(contact.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllContacts());

		resource.add(linkTo.withRel("all-contacts"));

		return resource;
	}

	public void deleteContact(long id) {
		contactRepository.deleteById(id);
	}

	
	public Contact createContact(Contact contact) {
		String status= null;
		Contact savedContact = null;
		status  = consumeWebService.validateAddress(contact); // this way we can validate not sure sandbox service will get the call even return type is my assumption 
		
		try {
			JSONArray array = new JSONArray(status);
		
		 
		JSONObject object = array.getJSONObject(0);  
		System.out.println(object.getString("status"));  
		
		if (object!=null && object.getString("status")!=null && object.getString("status").toString().equals("verified") )	
		savedContact = contactRepository.save(contact);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	    return savedContact;

	}
	
	public Optional<Contact> updateContact(Contact contact, long id) {

		Optional<Contact> contactOptional = contactRepository.findById(id);

		return contactOptional;
	}

}
