package com.salvadormontiel.cable;

import java.util.HashMap;
import java.util.Map;

public class CableResponse {
	public final Map<String, Object> data = new HashMap<>();
	public final Map<String, Object> metadata = new HashMap<>();

	@Override
	public String toString() {
		return "CableResponse{" +
				"data=" + data +
				", metadata=" + metadata +
				'}';
	}
}
