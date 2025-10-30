package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.Logger;
import game.entities.DraftEntityDTO;
import game.entities.Hero;
import game.skills.logic.EffectDTO;
import game.skills.logic.Stat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileWalker {


    public static Object getJsonAsObject(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }

    public static Map<String, List<DraftEntityDTO>> getDraftSet(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }
    public static <T> T mapJson(Class<T> clazz, String json) {
        try {
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, clazz);
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }
//    public static <T> Map<String, T> mapJson(Class<T> clazz, String json) {
//        try {
//            if (json != null) {
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.getTypeFactory()
//                        .constructMapType(Map.class, String.class, clazz);
//                return mapper.readValue(json, new TypeReference<>() {
//                });
//            }
//        } catch (Exception e) {
//            Logger.logLn(e.getMessage());
//        }
//        return null;
//    }
    public static Map<String, Map<String, Object>> getHeroes(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }
    public static Map<Stat, Integer> getStatJson(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }

    public static Map<Stat, Integer> getEquipmentStatJson(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, new TypeReference<>() {});
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }

    public static Map<String, String> loadJsonMap(String path) {
        try {
            String json = loadJson(path);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(json);
                Map<String, String> result = new HashMap<>();
                Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String valueAsString = objectMapper.writeValueAsString(entry.getValue());
                    result.put(entry.getKey(), valueAsString);
                }
                return result;
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }
    public static String loadJson(String path) {
        try {
            URL jsonURL = FileWalker.class.getClassLoader().getResource(path);
            if (jsonURL != null) {
                File jsonFile = new File(jsonURL.toURI());
                return FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            Logger.logLn(e.getMessage());
        }
        return null;
    }
}
