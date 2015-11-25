package com.example.domain.serializers;

import com.example.domain.Rate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by mileslux on 11/13/2015.
 */
public class RateSerializer extends JsonSerializer<Rate> {
    @Override
    public void serialize(Rate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("code", value.getCode());
        jgen.writeObjectField("rate", value.getRate().toString());
        jgen.writeObjectField("date", value.getDate().format(DateTimeFormatter.ISO_DATE));
        jgen.writeEndObject();
    }
}
