package com.salvadormontiel.cable.sample.db;

import com.salvadormontiel.cable.sample.Report;
import com.salvadormontiel.cable.sample.ReportDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportDB {
	private static ReportDB INSTANCE;

	public static ReportDB getInstance() {
		if (INSTANCE == null) INSTANCE = new ReportDB();
		return INSTANCE;
	}

	private final Map<Integer, Report> reports;
	private int i = 0;

	public ReportDB() {
		reports = new HashMap<>();
	}

	public Report add(Report r) {
		int index = ++i;
		Report nr = new Report(index, r.getName(), r.getAssigneeId(), r.getCategory(), r.getStatus());
		reports.put(index, nr);

		return nr;
	}

	public void update(ReportDTO r) {
		update(new Report(r.getId(), r.getName(), r.getAssignee().getId(), r.getCategory(), r.getStatus()));
	}

	public void update(Report r) {
		reports.put(r.getId(), r);
	}

	public ReportDTO find(int id) {
		return reports.values().stream()
				.filter(report -> report.getId() == id)
				.map(r -> new ReportDTO(r.getId(), r.getName(), UserDB.getInstance().find(r.getAssigneeId()), r.getCategory(), r.getStatus()))
				.findFirst()
				.orElse(null);
	}

	public List<ReportDTO> all() {
		return reports.values().stream()
				.map(r -> new ReportDTO(r.getId(), r.getName(), UserDB.getInstance().find(r.getAssigneeId()), r.getCategory(), r.getStatus()))
				.collect(Collectors.toList());
	}
}
