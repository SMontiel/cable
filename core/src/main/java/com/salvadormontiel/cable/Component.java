package com.salvadormontiel.cable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salvadormontiel.cable.internal.ComponentAdapter;

import java.util.*;
import java.util.function.Function;

/**
 * @author Salvador Montiel
 */
public abstract class Component {
	private final ComponentAdapter<Component> adapter;
	private final Map<String, Object> data = new HashMap<>();
	private final Map<String, Object> metadata = new HashMap<>();
	private final Map<String, Object> calls = new HashMap<>();
	private final List<String> readonly = new ArrayList<>();
	private CableRequest request = null;

	public Component(ComponentAdapter<? extends Component> adapter) {
		this.adapter = (ComponentAdapter<Component>) adapter;
		this.adapter.setComponent(this);
	}

	public CableResponse handle(CableRequest request, String target) {
		this.request = request;
		adapter.handle(request, target);
		if (target.equals("mount")) {
			Map<String, Object> map = (Map<String, Object>) adapter.getMethodCall(target).apply(request.calls(target));
			Map<String, Object> ro = (Map<String, Object>) map.get("readonly");
			if (ro != null) {
				readonly.addAll(ro.keySet());
				calls.putAll(Collections.singletonMap(target, ro));
			} else {
				calls.putAll(Collections.singletonMap(target, map));
			}
		} else {
			Function<List<Object>, Object> methodCall = adapter.getMethodCall(target);
			if (methodCall != null) {
				Object o = methodCall.apply(request.calls(target));
				if (o instanceof Map) {
					Map<String, Object> ro = (Map<String, Object>) ((Map<String, Object>) o).get("readonly");
					if (ro != null) {
						readonly.addAll(ro.keySet());
						calls.putAll(Collections.singletonMap(target, ro));
					} else {
						calls.putAll(Collections.singletonMap(target, o));
					}
				} else {
					if (!(o instanceof ComponentAdapter.NullObject)) {
						calls.putAll(Collections.singletonMap(target, o));
					}
				}
			}
		}

		adapter.getMethodCall("dehydrate").apply(Collections.emptyList());

		data.putAll(adapter.getData());
		readonly.addAll(adapter.getReadonlyFields());
		metadata.put("readonly", readonly);
		metadata.put("calls", calls);

		CableResponse ar = new CableResponse();
		ar.data.putAll(data);
		ar.metadata.putAll(metadata);

		return ar;
	}

	public Object changes(String key) {
		return request.changes(key);
	}

	// TODO: pasarle field names para resetear
	public void reset() {
		adapter.reset();
	}

	public Map<String, Object> mount() {
		return Collections.emptyMap();
	}

	public void dehydrate() {}

	public Component metadata(String key, Object value) {
		this.metadata.put(key, value);
		return this;
	}

	// TODO: maybe delete this method
//	public ComponentAdapter<Component> getAdapter() {
//		return adapter;
//	}

	//	public final Map<String, Object> data = new HashMap<>();
//	public final Map<String, Object> metadata = new HashMap<>();
//
//	public Component initialize(Map<String, Object> state) {
//		System.out.println(">>>");
//		return this;
//	}
}
