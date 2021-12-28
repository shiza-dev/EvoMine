package ru.kernel.Events.GroundFurnace;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.kernel.Database.DatabaseRouter;
import ru.kernel.EvoMine;
import ru.kernel.Utils.GuiUtil;
import ru.kernel.Utils.ItemManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DirtFurnace implements Listener {

    @EventHandler
    public void event(PlayerInteractEvent e) {

        Block block = e.getClickedBlock();
        if(block.getType() == Material.HAY_BLOCK && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();

            try {
                ResultSet resSet = DatabaseRouter.read("SELECT * FROM ground_furnace WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "';");

                if (resSet != null) {
                    GuiUtil guiUtil = new GuiUtil();
                    EvoMine main = EvoMine.getPlugin(EvoMine.class);

                    FileConfiguration itemManager = main.setYamlConfig(main.getItemManager());

                    String guiName = itemManager.getConfigurationSection("Blocks").getConfigurationSection("ground_furnace").getString("guiName");

                    HashMap<Integer, ItemStack> arr = new HashMap<Integer, ItemStack>() {{
                        put(4, ItemManager.getFuel());
                        put(13, ItemManager.getMeltingItem());
                        put(22, ItemManager.getLitter());
                    }};
                    ItemStack airGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    HashMap<Integer, ItemStack> items = guiUtil.setItems(arr, airGlass, 27);
                    guiUtil.createGui(e.getPlayer(), guiName, 27, null, items);
                }
            } catch (SQLException | ClassNotFoundException err) {
                err.printStackTrace();
            }
        }
    }
}