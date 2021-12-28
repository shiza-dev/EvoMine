package ru.kernel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kernel.Commands.MainCommand;
import ru.kernel.Database.DatabaseRouter;
import ru.kernel.Events.GroundFurnace.DirtFurnace;
import ru.kernel.Events.GroundFurnace.Gui.ClickEvent;
import ru.kernel.Events.GroundFurnace.PlaceBlock;
import ru.kernel.Events.GroundFurnace.RemoveBlock;
import ru.kernel.Events.Materials.BreakEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class EvoMine extends JavaPlugin {

    private static final String sep = File.separator;
    private final String messagesPath = Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "language" + sep + "messages.yml";
    private final File messages = new File(messagesPath);

    private final String itemManagerPath = Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "language" + sep + "itemmanager.yml";
    private final File itemManager = new File(itemManagerPath);

    private final String sqlitePath = Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "EvoMine.db";
    private final File sqlite = new File(sqlitePath);

    private static String status;

    @Override
    public void onEnable() {
        // создаём первый раз конфиги и бд sqlite, mysql

        saveDefaultConfig();

        String status = getConfig().getConfigurationSection("DataSource").getString("Backend");
        if(status.toLowerCase().equals("mysql")) {
            this.status = "MySQL";
            try {
                DatabaseRouter.writeDefault("MySQL");
            } catch(SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
           this.status = "SQLite";
        }

        checkFiles();
        RegisterEvents();

        this.getCommand("evomine").setExecutor(new MainCommand());
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
        if(!itemManager.exists()) {
            this.saveResource("language/itemmanager.yml", false);
        }
        if(!sqlite.exists() && status == "SQLite") {
            try {
                sqlite.createNewFile();
                DatabaseRouter.writeDefault("SQLite");
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

    public void RegisterEvents() {
        getServer().getPluginManager().registerEvents(new PlaceBlock(), this);
        getServer().getPluginManager().registerEvents(new RemoveBlock(), this);
        getServer().getPluginManager().registerEvents(new DirtFurnace(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
    }

    // геттеры и сеттеры

    public File getItemManager() { return itemManager; }

    public static String getStatus() { return status; }

    public File getMessages() { return messages; }

    public static String getSep() { return sep; }

    public FileConfiguration setYamlConfig(File file) {
        // возвращаю обработанный YamlConfiguration
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public String getColorText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
