package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.Logger;
import game.entities.DraftEntityDTO;
import game.skills.Stat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    private static String loadJson(String path) {
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
