package com.city.parkingMeter.infrastructure.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.base.AbstractInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class InstantSerializer extends JsonSerializer<AbstractInstant> {
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd-MM HH:mm:ss");

    @Override
    public void serialize(AbstractInstant value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(formatter.print(value));
    }
}
