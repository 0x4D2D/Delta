package me.saturn.delta.config;

import com.google.gson.Gson;
import me.saturn.delta.Delta;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class Container {
    public static File STORAGE = new File(Delta.c.runDirectory,"config.delta");
    private Storage    inner   = new Storage();
    public Storage getConfig() {
        return inner;
    }
    public void load() {
        try {
            loadInner();
        } catch (Exception e) {
            Delta.LOGGER.error("Failed to load config file");
            e.printStackTrace();
        }
        if (inner == null) inner = new Storage();
    }
    private void loadInner() throws Exception {
        if (!STORAGE.isFile()) STORAGE.delete();
        if (!STORAGE.exists()) {
            STORAGE.createNewFile();
            return;
        }
        String t = FileUtils.readFileToString(STORAGE,StandardCharsets.UTF_8);
        Gson gson = new Gson();
        inner = gson.fromJson(t,Storage.class);
    }

    public void save() {
        try {
            saveInner();
        } catch (Exception e) {
            Delta.LOGGER.error("Failed to save config file");
            e.printStackTrace();
        }
    }

    private void saveInner() throws Exception {
        Gson gson = new Gson();
        String c = gson.toJson(inner);
        FileUtils.writeStringToFile(STORAGE,c, StandardCharsets.UTF_8);
    }
}
