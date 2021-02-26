package com.reloadly.exposition;

public class SaveAccountResponse {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SaveAccountResponse [id=" + id + "]";
	}
}