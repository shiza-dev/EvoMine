package ru.kernel.Events.GroundFurnace.Gui;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.kernel.EvoMine;

public class ClickEvent implements Listener {

    private static EvoMine main = EvoMine.getPlugin(EvoMine.class);
    private static FileConfiguration itemManager = main.setYamlConfig(main.getItemManager());

    @EventHandler
    public void event(InventoryClickEvent e) {

        System.out.println(e.getView().getTitle());
        System.out.println(itemManager.getConfigurationSection("Blocks").getConfigurationSection("ground_furnace").getString("guiName"));

        if(e.getView().getTitle().equalsIgnoreCase(itemManager.getConfigurationSection("Blocks").getConfigurationSection("ground_furnace").getString("guiName"))) {
            System.out.println("1");
            e.setCancelled(true);
        }
    }
}
