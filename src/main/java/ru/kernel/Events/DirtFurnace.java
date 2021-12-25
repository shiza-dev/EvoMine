package ru.kernel.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.kernel.EvoMine;
import ru.kernel.Utils.GuiUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DirtFurnace implements Listener {

    @EventHandler
    public void event(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        if(block.getType() == Material.HAY_BLOCK) {
            GuiUtil guiUtil = new GuiUtil();
            EvoMine main = EvoMine.getInstance();

            FileConfiguration messages = main.setYamlConfig(main.getMessages());

            String guiName = messages.getConfigurationSection("GroundFurnace").getString("guiName");

            ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(4, 13, 22));
            ItemStack airGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            HashMap<ItemStack, Integer> items = guiUtil.setSameItem(arr, airGlass, 27);
            guiUtil.createGui(e.getPlayer(), guiName, 27, null, items);
        }
    }
}