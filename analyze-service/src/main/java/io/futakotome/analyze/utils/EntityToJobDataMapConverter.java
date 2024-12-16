package io.futakotome.analyze.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.JobDataMap;

import java.util.Map;

public final class EntityToJobDataMapConverter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JobDataMap convert(Object entity) {
        try {
            String json = MAPPER.writeValueAsString(entity);
            return new JobDataMap(MAPPER.readValue(json, Map.class));
        } catch (Exception e) {
            e.printStackTrace();
            return new JobDataMap();
        }
    }
}
