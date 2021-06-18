package com.salvadormontiel.cable.sample;

import java.util.Objects;

public class Report {
	private int id;
	private String name;
	private int assigneeId;
	private int category;
	private String status;

	public Report() {}

	public Report(int id, String name, int assigneeId, int category, String status) {
		this.id = id;
		this.name = name;
		this.assigneeId = assigneeId;
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

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
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
		Report report = (Report) o;
		return id == report.id && assigneeId == report.assigneeId && category == report.category && Objects.equals(name, report.name) && Objects.equals(status, report.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, assigneeId, category, status);
	}

	@Override
	public String toString() {
		return "Report{" +
				"id=" + id +
				", name='" + name + '\'' +
				", assigneeId=" + assigneeId +
				", category=" + category +
				", status='" + status + '\'' +
				'}';
	}
}
