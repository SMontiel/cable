package com.salvadormontiel.cable.sample;

import com.salvadormontiel.cable.Component;
import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.internal.ComponentAdapter;
import com.salvadormontiel.cable.sample.db.ReportDB;
import com.salvadormontiel.cable.sample.db.UserDB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CableComponent
public class CreateReport extends Component {
	@Wired
	public String name;
	@Wired
	public Integer assignee;
	@Wired
	public Integer category;

	public CreateReport(ComponentAdapter<? extends Component> adapter) {
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
	public void assignee() {
		if (this.changes("name") != null) {
			// Don't override user-initiated name changes
			return;
		}

		this.name = "Report for " + UserDB.getInstance().find(assignee).getName();
	}

	@Wired
	public Report create() {
		Report u = ReportDB.getInstance().add(new Report(1, name, assignee, category, "pending"));
		this.metadata("notification", "The report " + u.getName() + " has been created with id " + u.getId() + "!");
		this.resetAll();

		return u;
	}
}
