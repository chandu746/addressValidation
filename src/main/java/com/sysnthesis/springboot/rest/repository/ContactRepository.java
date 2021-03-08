package com.sysnthesis.springboot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysnthesis.springboot.rest.model.Contact;


@Repository
public interface ContactRepository  extends JpaRepository<Contact, Long> {

	
	
}
