package com.salvadormontiel.cable.sample;

import com.salvadormontiel.cable.Component;
import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.internal.ComponentAdapter;
import com.salvadormontiel.cable.sample.db.ReportDB;
import com.salvadormontiel.cable.sample.db.UserDB;

import java.util.*;
import java.util.stream.Collectors;

@CableComponent
public class ReportFilter extends Component {
	@Wired
	public String search = "";
	@Wired
	public Integer assignee = 0;
	@Wired
	public Integer category = 0;
	@Wired
	public String status = "pending";
	@Wired(readOnly = true)
	public List<ReportDTO> reports = Collections.emptyList();

	public ReportFilter(ComponentAdapter<? extends Component> adapter) {
		super(adapter);
	}

	@Override
	public Map<String, Object> mount() {
		return Collections.singletonMap(
				"readonly",
				Collections.singletonMap("users", UserDB.getInstance().all())
		);
	}

	@Wired
	public String changeStatus(int reportId) {
		System.out.println("Updating report: " + reportId);
		ReportDTO report = ReportDB.getInstance().find(reportId);
		Map<String, String> m = new HashMap<>();
		m.put("pending", "resolved");
		m.put("resolved", "invalid");
		m.put("invalid", "pending");

		report.setStatus(m.get(report.getStatus()));
		ReportDB.getInstance().update(report);

		this.metadata("notification", "The status was changed to " + report.getStatus() + "!");

		return report.getStatus();
	}

	@Override
	public void dehydrate() {
		System.out.println("Dehydrating...");
		this.reports = ReportDB.getInstance().all().stream()
				.filter(r -> r.getName().contains(this.search))
				.filter(r -> r.getAssignee().getId() == this.assignee)
				.filter(r -> r.getCategory() == this.category)
				.filter(r -> r.getStatus().equals(this.status))
				.collect(Collectors.toList());
		System.out.println(reports);
//		this.reports = Report::query()
//			->where('name', 'LIKE', "%{$this->search}%")->with('assignee')
//            ->when(isset($this->assignee), fn (Builder $query) => $query->where('assignee_id', $this->assignee))
//            ->when(isset($this->category), fn (Builder $query) => $query->where('category', $this->category))
//            ->when(isset($this->status), fn (Builder $query) => $query->where('status', $this->status))
//            ->get();
	}
}
