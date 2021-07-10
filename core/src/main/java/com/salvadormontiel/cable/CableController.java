package com.salvadormontiel.cable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Salvador Montiel
 */
@RestController
public class CableController {

	@ResponseBody
	@PostMapping(value = "/airwire/{component}/{target}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CableResponse airwire(@PathVariable String component,
								 @PathVariable(required = false) String target,
								 @RequestBody Map<Object, Object> inputMap) {
								 //@RequestBody CableRequest input) {
		System.out.println("Component: " + component);
		System.out.println("Target: " + target);
		System.out.println("Input map: " + inputMap);
		Map<String, Object> errors = new HashMap<>();
		CableResponse response = new CableResponse();
		Object calls = inputMap.get("calls");
		List<String> callsErrors = new ArrayList<>();
		if (!(calls instanceof Map)) {
			callsErrors.add("Calls must be an array.");
			calls = Collections.emptyMap();
		}
		((Map<?, ?>) calls).entrySet().forEach(entry -> {
			if (!(entry.getKey() instanceof String))
				callsErrors.add("[Calls] Method name must be a string, " + entry.getKey().getClass() + " given.");
		});
		if (!callsErrors.isEmpty()) errors.put("calls", callsErrors);

		Object changes = inputMap.get("changes");
		List<String> changesErrors = new ArrayList<>();
		if (!(changes instanceof Map)) {
			changesErrors.add("Changes must be an array.");
			changes = Collections.emptyMap();
		}
		((Map<?, ?>) changes).entrySet().forEach(entry -> {
			if (!(entry.getKey() instanceof String))
				changesErrors.add("[Changes] Property name must be a string, " + entry.getKey().getClass() + " given.");
		});
		if (!changesErrors.isEmpty()) errors.put("changes", changesErrors);

		Object state = inputMap.get("state");
		List<String> stateErrors = new ArrayList<>();
		if (!(state instanceof Map)) {
			stateErrors.add("State must be an array.");
			state = Collections.emptyMap();
		}
		((Map<?, ?>) state).entrySet().forEach(entry -> {
			if (!(entry.getKey() instanceof String))
				stateErrors.add("[State] Property name must be a string, " + entry.getKey().getClass() + " given.");
		});
		if (!stateErrors.isEmpty()) errors.put("state", stateErrors);

		Component c = Cable.getComponent(component);
		if (c == null) {
			errors.put("component", Collections.singletonList("Component " + component + " not found."));
		}
		//validate(inputMap, errors);

		CableRequest input = new CableRequest(
				(Map<String, Object>) state,
				(Map<String, Object>) changes,
				(Map<String, List<?>>) calls
		);
		System.out.println("Input: " + input);
		if (!errors.isEmpty()) {
			response.data.putAll(input.getState());
			response.metadata.put("errors", errors);

			return response;
		}
		response = c.handle(input, target);
		System.out.println("Response: " + response);

		return response;
	}
}
