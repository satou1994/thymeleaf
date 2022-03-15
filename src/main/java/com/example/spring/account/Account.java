package com.example.spring.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;


@Entity
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private int id;

	@NotEmpty(message="ユーザー名を入力してください。")
	@Size(max=20,message="20文字以内で入力してください")
	private String username;

	@NotEmpty(message="パスワードを入力してください。")
	private String password;

	@NotEmpty(message="メールアドレスを入力してください。")
	@Email(message="メールアドレスが不正です。")
	private String email;

	@Column(nullable=true)
	private MultipartFile image;

	@Size(max=400,message="400文字以内で入力してください。")
	private String profile;

	private String role="ROLE_USER";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image=image;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setAccount(ValidateAccount validateAccount) {
		this.username= validateAccount.getUsername();
//		this.password= validateAccount.getPassword();
		this.email= validateAccount.getEmail();
		this.profile= validateAccount.getProfile();
		this.role= validateAccount.getRole();
	}
}
