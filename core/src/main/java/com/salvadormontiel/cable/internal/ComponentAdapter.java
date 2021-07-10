package com.salvadormontiel.cable.internal;

import com.salvadormontiel.cable.CableRequest;
import com.salvadormontiel.cable.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Salvador Montiel
 */
public interface ComponentAdapter<T extends Component> {

	void handle(CableRequest state, String target);

	T getComponent();

	void setComponent(T component);

	void resetAll();

	void reset(List<String> fieldNames);

	Function<List<Object>, Object> getMethodCall(String methodName);

	Map<String, Object> getData();

	List<String> getReadonlyFields();

	class NullObject {}
}
