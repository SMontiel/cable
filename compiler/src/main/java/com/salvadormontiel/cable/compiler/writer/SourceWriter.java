package com.salvadormontiel.cable.compiler.writer;

import java.io.IOException;
import java.io.Writer;

public interface SourceWriter<T> {

    String createSourceName(T element);

    void writeSource(Writer writer, T element) throws IOException;
}
