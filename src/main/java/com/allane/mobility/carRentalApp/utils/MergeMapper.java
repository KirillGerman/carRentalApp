package com.allane.mobility.carRentalApp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public class MergeMapper {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public static <T> T patchValue(T existing, T input)  {
        String inputJson = mapper.writeValueAsString(input);
        ObjectReader objectReader = mapper.readerForUpdating(existing);
        return objectReader.readValue(inputJson);
    }

}
