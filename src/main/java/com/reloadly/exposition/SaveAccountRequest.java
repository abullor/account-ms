package com.reloadly.exposition;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.reloadly.enums.NotificationPreference;


public class SaveAccountRequest {
	@NotNull
	@Size(min = 5, max = 20)
	private String name;
	@NotNull
	@Size(min = 5, max = 20)
	private String password;
	@NotNull
	@Email
	@Size(min = 6, max = 50)
	private String email;
	@NotNull
	@Size(min = 10, max = 20)
	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
	private String phone;
	@NotNull
	private NotificationPreference notificationPreference;
	
	public NotificationPreference getNotificationPreference() {
		return notificationPreference;
	}
	public void setNotificationPreference(NotificationPreference notificationPreference) {
		this.notificationPreference = notificationPreference;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "SaveAccountRequest [name=" + name + ", password=" + password + ", email=" + email + ", phone=" + phone
				+ ", notificationPreference=" + notificationPreference + "]";
	}
}
