package pl.akmf.ksef.sdk.api.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ModelSerializationTest {

    @Test
    void testAllModelClassesSerializationAndDeserialization() throws JsonProcessingException {
        Reflections reflections = new Reflections("pl.akmf.ksef.sdk.client.model",
                new SubTypesScanner(false), new TypeAnnotationsScanner());

        Set<Class<?>> modelClasses = reflections.getSubTypesOf(Object.class);

        ObjectMapper mapper = new ObjectMapper();

        for (Class<?> clazz : modelClasses) {

            if (clazz.isInterface() || clazz.isEnum() || clazz.isAnnotation()) {
                continue;
            }

            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception ignored) {
                // klasy bez domyślnego konstruktora pomijamy
                continue;
            }

            for (Field field : clazz.getDeclaredFields()) {

                String jsonPropertyName = field.isAnnotationPresent(JsonProperty.class)
                        ? field.getAnnotation(JsonProperty.class).value()
                        : field.getName();

                Object testValue = getDummyValue(field.getType());

                String json = mapper.writeValueAsString(Collections.singletonMap(jsonPropertyName, testValue));

                try {
                    Object obj = mapper.readValue(json, clazz);
                    String back = mapper.writeValueAsString(obj);

                    if (testValue != null) {
                        assertTrue(back.contains(jsonPropertyName),
                                "Property mapping broken in class " + clazz.getName() +
                                " for field: " + jsonPropertyName);
                    }
                } catch (Exception ex) {
                    fail("Getter/setter mapping broken in class " + clazz.getName() +
                         " for field: " + jsonPropertyName + " -> " + ex.getMessage());
                }
            }
        }
    }

    private static Object getDummyValue(Class<?> type) {
        Object testValue;
        if (type.equals(String.class)) {
            testValue = "test";
        } else if (type.equals(byte[].class)) {
            testValue = "test".getBytes(StandardCharsets.UTF_8);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            testValue = 123;
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            testValue = 123L;
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            testValue = true;
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            testValue = 123.45;
        } else if (type.equals(List.class)) {
            testValue = new ArrayList<>();
        } else {
            testValue = null;
        }
        return testValue;
    }
}
