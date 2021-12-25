package ru.kernel.Database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import ru.kernel.EvoMine;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseRouter {
    private static java.sql.Connection conn;
    private static ResultSet resSet;
    private static EvoMine main = EvoMine.getInstance();

    // Открытие сессии
    public static void connect() throws ClassNotFoundException {

        Logger logger = Bukkit.getLogger();

        if(EvoMine.getStatus() == "SQLite") {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            try {
                String sep = main.getSep();
                conn = DriverManager.getConnection("jdbc:sqlite:" + Bukkit.getServer().getWorldContainer().getPath() + sep + "plugins" + sep + "EvoMine" + sep + "EvoMine.db");
            } catch(SQLException e) {
                logger.log(Level.WARNING, "Plugin catch SQLException! Please check your EvoMine.db file into EvoMine folder!");
                Bukkit.shutdown();
            }
        } else {

            FileConfiguration config = main.getConfig();
            String login = config.getConfigurationSection("MySQL").getString("Login");
            String password = config.getConfigurationSection("MySQL").getString("Password");
            String database = config.getConfigurationSection("MySQL").getString("Database");
            String port = config.getConfigurationSection("MySQL").getString("Port");

            conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + database + "?user=" + login + "&password=" + password);
            } catch(SQLException e) {
                logger.log(Level.WARNING, "Plugin catch SQLException! Please check your config");
                Bukkit.shutdown();
            }
        }
    }

    public static void writeDefault() throws SQLException, ClassNotFoundException {
        connect();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE EvoMine (`haha` varchar(32));");
        stmt.close();
        close();
    }
    // SQL запрос к базе
    public static void write(String sql, String... args) throws SQLException, ClassNotFoundException {

        connect();
        PreparedStatement p = conn.prepareStatement(sql);
        for(String s : args) {
            for(int i=1;i<args.length;i++) {
                p.setString(i, s);
            }
        }
        resSet = p.executeQuery(sql);
        close();
    }

    // Чтение таблицы
    public static ResultSet read(String sql, String... args) throws SQLException, ClassNotFoundException {

        connect();
        PreparedStatement p = conn.prepareStatement(sql);
        for(int i=1;i<args.length;i++) {
            for(String s : args) {
                p.setString(i, s);
            }
        }

        resSet = p.executeQuery(sql);
        close();

        return(resSet);
    }

    // Закрытие сессии
    private static void close() throws SQLException {
        if(resSet != null) { resSet.close(); }
        if(conn != null) { conn.close(); }
    }

}
