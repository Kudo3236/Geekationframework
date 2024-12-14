package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

import java.util.List;
import java.sql.Timestamp;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();
		
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		
		contactRepository.save(contact);
	}
	
	@Override
    public List<Contact> getAllContacts() {
        // リポジトリを使って全データを取得
        return contactRepository.findAll();
    }
	
	@Override
	public Contact getContactById(Long id) {
		return contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact ID: " + id));
	}
	
	@Override
    public void updateContact(Long id, Contact updateContact) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact ID: " + id));
        contact.setLastName(updateContact.getLastName());
        contact.setFirstName(updateContact.getFirstName());
        contact.setEmail(updateContact.getEmail());
        contact.setPhone(updateContact.getPhone());
        contact.setZipCode(updateContact.getZipCode());
        contact.setAddress(updateContact.getAddress());
        contact.setBuildingName(updateContact.getBuildingName());
        contact.setContactType(updateContact.getContactType());
        contact.setBody(updateContact.getBody());
        contact.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        contactRepository.save(contact);
    }
    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}