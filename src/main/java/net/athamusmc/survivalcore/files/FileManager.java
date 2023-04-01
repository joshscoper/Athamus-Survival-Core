package net.athamusmc.survivalcore.files;

import net.athamusmc.survivalcore.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    private final Core core;
    private final File directory;
    private final File file;
    private final String fileName;
    private final FileConfiguration config;

    public FileManager(Core core, String fileName){
        this.core = core;
        this.fileName = fileName;
        this.directory = new File(core.getDataFolder().getPath());
        this.file = new File(directory, fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void setupFile(){
        if (!directory.exists()){
            directory.mkdirs();
        }
        if (!file.exists()) {
            loadFile();
        }

    }

    // Load File

    public void loadFile(){
        try {
            InputStreamReader reader = new InputStreamReader(core.getResource(fileName));
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(fileConfig);
            config.options().copyDefaults(true);
            reader.close();
            saveFile();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //Save File

    public void saveFile(){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExists(){
        return file.exists();
    }

    public FileConfiguration getFile(){
        return config;
    }
}
