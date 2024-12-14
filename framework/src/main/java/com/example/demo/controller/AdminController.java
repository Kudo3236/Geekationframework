package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.service.AdminService;
import com.example.demo.form.AdminForm;
import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("AdminForm") // セッションにAdminFormを永続化
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ContactService contactService;
	
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("adminForm", new AdminForm());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult errorresult, HttpServletRequest request) {
    	if(errorresult.hasErrors()) {
    		//エラーがあったらもう一度登録画面を表示
    		return "signup";
    	}
    	
    	adminService.saveAdmin(adminForm);
    	HttpSession session = request.getSession();
    	session.setAttribute("adminForm", adminForm);
    	
    	return "redirect:/admin/signin";
    	
    }
    
    @GetMapping("/signin")
    public String signin(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	AdminForm adminForm = (AdminForm) session.getAttribute("adminForm");
    	
    	if (adminForm == null) {
            adminForm = new AdminForm(); // セッションに存在しない場合、新規作成
            session.setAttribute("adminForm", adminForm);
        }
    	
    	model.addAttribute("adminForm", adminForm);
    	return "signin";
    }
        
    @GetMapping("/contacts")
    public String contacts(Model model) {
        // ContactService を使って、データベースからすべてのコンタクトを取得
        List<Contact> contacts = contactService.getAllContacts();
        
        if (contacts == null || contacts.isEmpty()) {
            System.out.println("Contacts list is empty or null");
            contacts = new ArrayList<>(); // 空のリストを追加
        } else {
        	System.out.println("Contacts fetched from DB: " + contacts);
        }
        
        // 取得した cをモデルに追加して、ビューに渡す
        model.addAttribute("contacts", contacts);
        
        // contacts.html を返す
        return "contacts";
    }
    
    @GetMapping("/contacts/{id}")
    public String contactDetail(@PathVariable("id") Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact);
        return "contact-detail"; // 詳細画面
    }

    @GetMapping("/contacts/{id}/edit")
    public String editContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact);
        return "contact-edit"; // 編集画面
    }

    @PostMapping("/contacts/{id}/edit")
    public String updateContact(@PathVariable("id") Long id, @ModelAttribute Contact contact) {
        contactService.updateContact(id, contact);
        return "redirect:/admin/contacts"; // 更新後に一覧画面へ
    }

    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
        return "redirect:/admin/contacts"; // 削除後に一覧画面へ
    }
}