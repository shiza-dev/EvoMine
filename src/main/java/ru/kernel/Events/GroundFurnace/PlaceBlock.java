package ru.kernel.Events.GroundFurnace;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.kernel.Database.DatabaseRouter;
import ru.kernel.EvoMine;

import java.sql.SQLException;

public class PlaceBlock implements Listener {

    @EventHandler
    public void event(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        EvoMine main = EvoMine.getPlugin(EvoMine.class);

        ItemStack furnace = new ItemStack(Material.HAY_BLOCK);
        ItemMeta meta = furnace.getItemMeta();
        FileConfiguration messages = main.setYamlConfig(main.getMessages());
        meta.setDisplayName(main.getColorText(messages.getConfigurationSection("Blocks").getConfigurationSection("ground_furnace").getString("itemName")));
        furnace.setItemMeta(meta);

        ItemStack handItem = p.getInventory().getItemInMainHand();

        if(handItem.hasItemMeta() && handItem.equals(furnace)) {

            int x = e.getBlockPlaced().getX();
            int y = e.getBlockPlaced().getY();
            int z = e.getBlockPlaced().getZ();

            try {
                DatabaseRouter.write("INSERT INTO ground_furnace (x, y, z) VALUES (" + x + ", " + y + ", " + z + ");");
            } catch(SQLException | ClassNotFoundException err) {
                err.printStackTrace();
            }
        }
    }
}
