package com.salvadormontiel.cable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CableRequest {
	private final Map<String, ?> state;
	private final Map<String, ?> changes;
	private final Map<String, List<?>> calls;

	public CableRequest(Map<String, Object> state, Map<String, Object> changes, Map<String, List<?>> calls) {
		this.state = state;
		this.changes = changes;
		this.calls = calls;
	}

	public <T extends Object> T state(String key) {
		return (T) state.get(key);
	}

	public <T extends Object> T changes(String key) {
		return (T) changes.get(key);
	}

	public <T extends Object> List<T> calls(String key) {
		return (List<T>) calls.get(key);
	}

	public Map<String, ?> getState() {
		return state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CableRequest that = (CableRequest) o;
		return Objects.equals(state, that.state) && Objects.equals(changes, that.changes) && Objects.equals(calls, that.calls);
	}

	@Override
	public int hashCode() {
		return Objects.hash(state, changes, calls);
	}

	@Override
	public String toString() {
		return "CableRequest{" +
				"state=" + state +
				", changes=" + changes +
				", calls=" + calls +
				'}';
	}
}
