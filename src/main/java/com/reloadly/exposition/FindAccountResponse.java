package com.reloadly.exposition;

import java.time.LocalDateTime;

import com.reloadly.enums.NotificationPreference;

public class FindAccountResponse {
	private Long id;
	private String name;
	private Boolean active;
	private String email;
	private String phone;
	private NotificationPreference notificationPreference;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
	public NotificationPreference getNotificationPreference() {
		return notificationPreference;
	}
	public void setNotificationPreference(NotificationPreference notificationPreference) {
		this.notificationPreference = notificationPreference;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	@Override
	public String toString() {
		return "FindAccountResponse [id=" + id + ", name=" + name + ", active=" + active + ", email=" + email
				+ ", phone=" + phone + ", notificationPreference=" + notificationPreference + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}
}