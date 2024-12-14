package com.example.demo.entity;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="admins")
public class Admin {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;   // 姓
    
    @Column(name = "first_name", nullable =false)
    private String firstName;  // 名
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;      // メールアドレス
    
    @Column(name = "password", nullable = false)
    private String password;   // パスワード
    
    @Column(name = "current_sign_in_at")
    private Timestamp currentSignInAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;
    
 // レコード作成時に作成日時を設定
    @PrePersist
    protected void onCreate() {
        Timestamp now = Timestamp.from(Instant.now());
        this.createdAt = now;
        this.updateAt = now;
    }

    // レコード更新時に更新日時を設定
    @PreUpdate
    protected void onUpdate() {
        this.updateAt = Timestamp.from(Instant.now());
    }

}




