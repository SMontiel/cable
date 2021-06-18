package com.salvadormontiel.cable.sample;

import java.util.Objects;

public class ReportDTO {
	private int id;
	private String name;
	private User assignee;
	private int category;
	private String status;

	public ReportDTO() {}

	public ReportDTO(int id, String name, User assignee, int category, String status) {
		this.id = id;
		this.name = name;
		this.assignee = assignee;
		this.category = category;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReportDTO reportDTO = (ReportDTO) o;
		return id == reportDTO.id && category == reportDTO.category && Objects.equals(name, reportDTO.name) && Objects.equals(assignee, reportDTO.assignee) && Objects.equals(status, reportDTO.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, assignee, category, status);
	}

	@Override
	public String toString() {
		return "ReportDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", assignee=" + assignee +
				", category=" + category +
				", status='" + status + '\'' +
				'}';
	}
}
