package com.example.demo.service;

import com.example.demo.form.ContactForm;
import java.util.List;
import com.example.demo.entity.Contact;

public interface ContactService {

	void saveContact(ContactForm contactForm);
	List<Contact> getAllContacts();
	Contact getContactById(Long id);
	void updateContact(Long id, Contact contact);
	void deleteContact(Long id);
}