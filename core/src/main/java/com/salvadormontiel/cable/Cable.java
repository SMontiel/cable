package com.salvadormontiel.cable;

import com.salvadormontiel.cable.internal.AdapterHolder;

/**
 * @author Salvador Montiel
 */
public final class Cable {
	private static AdapterHolder adapterHolder;

	public static Component getComponent(String alias) {
		return adapterHolder.getComponent(alias);
	}

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
