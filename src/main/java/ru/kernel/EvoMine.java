package ru.kernel;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kernel.Database.DatabaseRouter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class EvoMine extends JavaPlugin {

    private static final String sep = File.separator;
    private final String messagesPath = Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "language" + sep + "messages.yml";
    private final File messages = new File(messagesPath);

    private final String sqlitePath = Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "EvoMine.db";
    private final File sqlite = new File(sqlitePath);

    private static EvoMine instance;
    private static String status;

    @Override
    public void onEnable() {
        // создаём первый раз конфиги и бд sqlite
        saveDefaultConfig();

        String status = getConfig().getConfigurationSection("MySQL").getString("Active");
        if(status.equals("true")) {
            this.status = "MySQL";
        } else {
           this.status = "SQLite";
        }

        checkFiles();

        instance = this;
    }

    @Override
    public void onDisable() {
        // финальное сохранение всех конфигов
        FileConfiguration msgconfig = YamlConfiguration.loadConfiguration(messages);
        saveCustomConfig(msgconfig, messages);

    }

    // далее работа с конфигами и базой данных

    private void checkFiles() {
        // проверка на целостность файлов (если нет, то создаём новые)
        if(!messages.exists()) {
            this.saveResource("language/messages.yml", false);
        }
        if(!sqlite.exists() && status == "SQLite") {
            try {
                sqlite.createNewFile();
                DatabaseRouter.writeDefault();
            } catch(IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveCustomConfig(FileConfiguration config, File filePath) {
        // сохранение других конфигов
        try {
            config.save(filePath);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // геттеры и сеттеры

    public static EvoMine getInstance() {
        return instance;
    }

    public static String getStatus() { return status; }

    public File getMessages() { return messages; }

    public static String getSep() { return sep; }

    public FileConfiguration setYamlConfig(File file) {
        // возвращаю обработанный YamlConfiguration
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }
}
