package com.reloadly.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.reloadly.enums.NotificationPreference;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account", uniqueConstraints = @UniqueConstraint(name = "unique_account_name", columnNames = { "name"}))
public class Account {
	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 20, nullable = false)
    private String name;
	@Column(length = 60, nullable = false)
	private String password;
	@Column(nullable = false)
	private Boolean active;
	@Column(length = 50, nullable = false)
	private String email;
	@Column(length = 20, nullable = false)
	private String phone;
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationPreference notificationPreference;
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdOn;
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedOn;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", password=" + password + ", active=" + active + ", email="
				+ email + ", phone=" + phone + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
