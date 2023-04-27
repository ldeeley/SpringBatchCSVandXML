package com.example.springbatchcsvandxml.config;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class MyISOHeaderWriter implements FlatFileHeaderCallback {
    private final String header;

    MyISOHeaderWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
