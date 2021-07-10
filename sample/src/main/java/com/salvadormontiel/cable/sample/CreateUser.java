package com.salvadormontiel.cable.sample;

import com.salvadormontiel.cable.Component;
import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.internal.ComponentAdapter;
import com.salvadormontiel.cable.sample.db.UserDB;

@CableComponent
public class CreateUser extends Component {
	@Wired
	public String name = "";
	@Wired
	public String email = "";
	@Wired
	public String password = "";

	public CreateUser(ComponentAdapter<? extends Component> adapter) {
		super(adapter);
	}

	@Wired
	public User create() {
		User u = UserDB.getInstance().add(new User(12, name, email, password));
		this.metadata("notification", "The user " + u.getName() + " has been created with id " + u.getId() + "!");
		this.resetAll();

		return u;
	}
}
