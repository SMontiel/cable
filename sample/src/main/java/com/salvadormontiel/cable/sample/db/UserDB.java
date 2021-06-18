package com.salvadormontiel.cable.sample.db;

import com.salvadormontiel.cable.sample.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDB {
	private static UserDB INSTANCE;

	public static UserDB getInstance() {
		if (INSTANCE == null) INSTANCE = new UserDB();
		return INSTANCE;
	}

	private final Map<Integer, User> users;
	private int i = 0;

	public UserDB() {
		users = new HashMap<>();
	}

	public User add(User r) {
		int index = ++i;
		User nu = new User(index, r.getName(), r.getEmail(), r.getPassword());
		users.put(index, nu);

		return nu;
	}

	public void update(User r) {
		users.put(r.getId(), r);
	}

	public User find(int id) {
		return users.values().stream().filter(report -> report.getId() == id).findFirst().orElse(null);
	}

	public List<User> all() {
		return new ArrayList<>(users.values());
	}
}
