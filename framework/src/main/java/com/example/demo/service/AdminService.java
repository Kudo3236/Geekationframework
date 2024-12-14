package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;


public interface AdminService {
	
        void saveAdmin(AdminForm adminForm);
        Admin findByEmail(String email);
        void updateSignInTimestamp(Admin admin);
        boolean authenticate(String email, String password); 
}