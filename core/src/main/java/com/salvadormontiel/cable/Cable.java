package com.salvadormontiel.cable;

import com.salvadormontiel.cable.internal.AdapterHolder;
import com.salvadormontiel.cable.internal.ComponentAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Salvador Montiel
 */
public final class Cable {
	private static AdapterHolder adapterHolder;

	public static Component getComponent(String alias) {
		return adapterHolder.getComponent(alias);
	}

//	private static final Map<String, Class<? extends Component>> components = new HashMap<>();
//
//	public static Class<? extends Component> getComponentClass(String name) {
//		return components.get(name);
//	}
//
//	public static Component getComponent(String alias) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//		return getComponentClass(alias).getConstructor().newInstance().initialize(new HashMap<>());
//	}

//	public static void registerComponent(String alias, Class<? extends Component> component) {
//		components.put(alias, component);
//	}

//	public static boolean hasComponent(String alias) {
//		return components.containsValue(alias);
//	}

	public static void init() {
		initAdapterHolder();
	}

	private static void initAdapterHolder() {
		try {
			Class<?> adapterClass = Class.forName(AdapterHolder.IMPL_CLASS_FQCN);
			adapterHolder = (AdapterHolder) adapterClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize Cable.", e);
		}
	}

	public static AdapterHolder getAdapterHolder() {
		if (adapterHolder == null) throw new RuntimeException("Call Cable.init() first.");

		return adapterHolder;
	}
}
