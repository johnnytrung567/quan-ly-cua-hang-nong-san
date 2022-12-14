package com.example.demo.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.example.demo.role.Role;

@Entity
public class User {
	private Long id;
	
	@NotEmpty(message = "Vui lòng nhập họ tên")
	private String fullname;
	
	@NotEmpty(message = "Vui lòng nhập email")
	@Email(message = "Vui lòng nhập email hợp lệ")
	private String email;
	
	@NotEmpty(message = "Vui lòng nhập mật khẩu")
	private String password;
	
	@NotEmpty(message = "Vui lòng nhập địa chỉ")
	private String address;
	
	@NotEmpty(message = "Vui lòng nhập số điện thoại")
	private String phone;
	
	
	private Role role;

	public User() {
		super();
	}

	public User(Long id, String fullname, String email, String password, String address, String phone, Role role) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@OneToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
