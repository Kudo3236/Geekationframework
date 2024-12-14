package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
    @NotBlank(message = "姓を入力してください")
    private String lastName;

    @NotBlank(message = "名を入力してください")
    private String firstName;

    @NotBlank(message = "有効なメールアドレスを入力してください")
    @Email(message = "メールアドレスは必須です")
    private String email;

    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    private String password;
}