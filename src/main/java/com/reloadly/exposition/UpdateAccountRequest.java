package com.reloadly.exposition;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.reloadly.enums.NotificationPreference;

public class UpdateAccountRequest {
	@Email
	@Size(min = 6, max = 50)
	private String email;
	@Size(min = 10, max = 20)
	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
	private String phone;
	private NotificationPreference notificationPreference;
	
	public NotificationPreference getNotificationPreference() {
		return notificationPreference;
	}
	public void setNotificationPreference(NotificationPreference notificationPreference) {
		this.notificationPreference = notificationPreference;
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
		return "UpdateAccountRequest [email=" + email + ", phone=" + phone + ", notificationPreference="
				+ notificationPreference + "]";
	}
}
