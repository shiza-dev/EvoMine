package ru.kernel.Database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ru.kernel.EvoMine;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// откажусь от preparedstatement пока, ибо если писать в общем виде, то этот общий вид не подходит для остальных запросов

public class DatabaseRouter {
    private static java.sql.Connection conn;
    private static ResultSet resSet;
    private static Statement stmt;
    private static EvoMine main = EvoMine.getPlugin(EvoMine.class);

    // Открытие сессии
    private static void connect() throws ClassNotFoundException {

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
            ConfigurationSection mysqlSection = config.getConfigurationSection("DataSource").getConfigurationSection("MySQL");
            String host = mysqlSection.getString("Host");
            String login = mysqlSection.getString("Login");
            String password = mysqlSection.getString("Password");
            String database = mysqlSection.getString("Database");
            String port = mysqlSection.getString("Port");

            conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            try {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
                conn = DriverManager.getConnection(url, login, password);
            } catch(SQLException e) {
                logger.log(Level.WARNING, "Plugin catch SQLException! Please check your config");
                Bukkit.shutdown();
            }
        }
    }

    public static void writeDefault(String dbType) throws SQLException, ClassNotFoundException {
        connect();
        stmt = conn.createStatement();
        if(dbType.equals("SQLite")) {
            stmt.execute("CREATE TABLE if not exists ground_furnace(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "x INTEGER NOT NULL," +
                    "y INTEGER NOT NULL," +
                    "z INTEGER NOT NULL," +
                    "fuel INTEGER DEFAULT 0," +
                    "item VARCHAR(255)," +
                    "litter BOOLEAN DEFAULT FALSE);");
        } else {
            stmt.executeUpdate("CREATE TABLE if not exists ground_furnace(" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "x INTEGER NOT NULL," +
                    "y INTEGER NOT NULL," +
                    "z INTEGER NOT NULL," +
                    "fuel INTEGER DEFAULT 0," +
                    "item VARCHAR(255) DEFAULT NULL," +
                    "litter BOOLEAN DEFAULT FALSE);");
        }
        stmt.close();
        close();
    }
    // SQL запрос к базе
    public static void write(String sql) throws SQLException, ClassNotFoundException {

        connect();
        stmt = conn.createStatement();
        stmt.execute(sql);
        close();

    }

    // Чтение таблицы
    public static ResultSet read(String sql) throws SQLException, ClassNotFoundException {

        connect();

        stmt = conn.createStatement();
        resSet = stmt.executeQuery(sql);
        ResultSet localRes = resSet;

        close();

        return(localRes);
    }

    // Закрытие сессии
    private static void close() throws SQLException {
        if(resSet != null) { resSet.close(); }
        if(stmt != null) { stmt.close(); }
        if(conn != null) { conn.close(); }
    }

}
