/**
 * Type adapter to be used by Gson serializer
 * It tell the Gson how it should serialize java.sql.Date objects
 */
package com.example.demo.config.interceptor;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeAdapter extends TypeAdapter<Date> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Here we override the write method, so we tell the json what to write
     *
     * @param out   writer that holds the serialized json
     * @param value the Java object to write. May be null.
     * @throws IOException when unable to write using the writer {@code out}
     */
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(dateFormat.format(value));
        }
    }

    /**
     * Here we override the write method, so we tell the json what to read the data
     *
     * @param in Json reader that holds the data to be deserialized
     * @return the deserialized data
     * @throws IOException when error occurred by the {@code JsonReader}
     */
    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return dateFormat.parse(in.nextString());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}