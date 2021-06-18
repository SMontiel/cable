package com.salvadormontiel.cable.internal;

import com.salvadormontiel.cable.Component;

/**
 * Used internally to hold mapping information.
 */
public interface AdapterHolder {
    String IMPL_CLASS_PACKAGE = "com.salvadormontiel.cable";
    String IMPL_CLASS_NAME = "AdapterHolderImpl";
    String IMPL_CLASS_FQCN = IMPL_CLASS_PACKAGE + "." + IMPL_CLASS_NAME;

    <T extends Component> T getComponent(String component);
}
