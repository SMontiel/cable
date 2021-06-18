package com.salvadormontiel.cable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Salvador Montiel
 */
@RestController
public class CableController {
private String a = "";
	@ResponseBody
	@PostMapping(value = "/airwire/{component}/{target}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CableResponse airwire(@PathVariable String component,
								   @PathVariable(required = false) String target,
								   @RequestBody CableRequest input) throws JsonProcessingException {
		System.out.println("Component: " + component);
		System.out.println("Target: " + target);
		System.out.println("Input: " + input);

		// TODO: check if componentAdapter is not null
		Component c = Cable.getComponent(component);
		CableResponse cr = c.handle(input, target);
		System.out.println("Response: " + cr);

		return cr;
	}
}
