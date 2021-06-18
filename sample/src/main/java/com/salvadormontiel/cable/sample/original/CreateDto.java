package com.salvadormontiel.cable.sample.original;

import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.Component;
import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.internal.ComponentAdapter;
import com.salvadormontiel.cable.sample.User;

import java.util.Collections;
import java.util.Map;

@CableComponent
public class CreateDto extends Component {
	@Wired
	public String name = "";
	@Wired(readOnly = true)
	public String email = "";

	public CreateDto(ComponentAdapter<? extends Component> adapter) {
		super(adapter);
	}

	@Wired
	public void aaa() {}

	@Wired
	public User bbb() {
		return new User(1, "SMT", "smt@smt.com", "12345");
	}

//	@Wired
//	public Map<String, Object> bbb() {
//		return Collections.singletonMap("user", new User(1, "John Doe", "jd@example.com"));
//		return Collections.singletonMap(
//				"readonly", Collections.singletonMap("user", new User(1, "John Doe", "jd@example.com"))
//		);
//	}

	@Wired
	public int ccc() {
		return 1997;
	}

	@Override
	public Map<String, Object> mount() {
//		return Collections.singletonMap("user", new User(1, "John Doe", "jd@example.com"));
		return Collections.singletonMap(
				"readonly", Collections.singletonMap("user", new User(1, "John Doe", "jd@example.com", "12345"))
		);
	}

	@Override
	public String toString() {
		return "CreateDto{" +
				"name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
