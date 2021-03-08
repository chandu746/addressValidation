package com.sysnthesis.springboot.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sysnthesis.springboot.rest.exception.ContactNotFoundException;
import com.sysnthesis.springboot.rest.model.Contact;
import com.sysnthesis.springboot.rest.service.ContactService;


@RestController
public class ContactResourceController {
	
	@Autowired
	private ContactService contactService;
	
	
	@GetMapping("/getContacts")
	public List<Contact> retrieveAllContacts() {
		return contactService.retrieveAllContacts();
	}

	@GetMapping("/contactsById/{id}")
	public EntityModel<Contact> retrieveContact(@PathVariable long id) {
		return contactService.retrieveContact(id);
		 }

	@DeleteMapping("/deleteContacts/{id}")
	public void deleteContact(@PathVariable long id) {
		contactService.deleteContact(id);
	}

	@PostMapping("/saveContacts")
	public ResponseEntity<Object> createContact(@Valid @RequestBody Contact contact) {
		
		Contact savedContact = contactService.createContact(contact);
		if(savedContact != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedContact.getId()).toUri();	
			return ResponseEntity.created(location).build();
		}
		return ResponseEntity.unprocessableEntity().build();

		

	}
	
	@PutMapping("/updateContacts/{id}")
	public ResponseEntity<Object> updateContact(@Valid @RequestBody Contact contact, @PathVariable long id) {

		Optional<Contact> contactOptional = contactService.updateContact(contact, id);

		if (!contactOptional.isPresent())
			return ResponseEntity.notFound().build();

		contact.setId(id);
		
		
		Contact savedContact = contactService.createContact(contact);
		if(savedContact != null) {
			
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.unprocessableEntity().build();
	}
}
