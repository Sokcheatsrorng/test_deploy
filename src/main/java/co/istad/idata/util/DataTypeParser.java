package co.istad.idata.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class DataTypeParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object parse(String value, String targetType) {
        return switch (targetType.toLowerCase()) {
            case "integer", "int" -> Integer.parseInt(value);
            case "double" -> Double.parseDouble(value);
            case "float" -> Float.parseFloat(value);
            case "long" -> Long.parseLong(value);
            case "short" -> Short.parseShort(value);
            case "byte" -> Byte.parseByte(value);
            case "boolean" -> Boolean.parseBoolean(value);
            case "char" -> {
                if (value.length() != 1) {
                    throw new IllegalArgumentException("Invalid char value: " + value);
                }
                yield value.charAt(0);
            }
            case "string" -> value;
            case "object" -> parseJsonObject(value);
            case "array" -> parseJsonArray(value);
            default -> throw new IllegalArgumentException("Unsupported data type: " + targetType);
        };
    }

    private static Object parseJsonObject(String value){
        try {

            return objectMapper.readValue(value, Map.class);

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON object: " + e.getMessage());
        }
    }

    private static Object parseJsonArray(String value){
//        try {
//
//            return objectMapper.readValue(value, List.class);
//
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Failed to parse JSON array: " + e.getMessage());
//        }

        try {
            // Check if the value is a JSON array
            if (value.trim().startsWith("[") && value.trim().endsWith("]")) {
                return objectMapper.readValue(value, List.class);
            }
            // If not a JSON array, return the value as it is
            return value;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON array: " + e.getMessage());
        }
    }


}
