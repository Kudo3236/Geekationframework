package com.example.demo.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void saveAdmin(AdminForm adminForm) {
		Admin admin = new Admin();
		
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		// パスワードを暗号化
        admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
		
        // 保存前に確認ログを追加
        System.out.println("Admin before saving: " + admin);
		adminRepository.save(admin);
		// 保存後に確認ログを追加
	    System.out.println("Admin after saving: " + admin);
	}
	
	 @Override
	    public Admin findByEmail(String email) {
	        // メールアドレスで管理者を検索
	        return adminRepository.findByEmail(email);
	    }

	 @Override
	    public void updateSignInTimestamp(Admin admin) {
	        // 最終ログイン日時を現在時刻に更新
	        admin.setCurrentSignInAt(new Timestamp(System.currentTimeMillis()));
	        adminRepository.save(admin);
	    }
	 
	 @Override
	 public boolean authenticate(String email, String password) {
	     Admin admin = findByEmail(email);
	     if (admin != null) {
	         return passwordEncoder.matches(password, admin.getPassword());
	     }
	     return false;
	 }
	 
}
