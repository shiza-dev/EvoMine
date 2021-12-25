package ru.kernel.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiUtil {

    private Inventory inv;

    public void createGui(Player p, String name, Integer size, InventoryHolder holder, HashMap<ItemStack, Integer> items) {

        inv = Bukkit.createInventory(holder, size, ChatColor.translateAlternateColorCodes('&', name));

        for(Map.Entry<ItemStack, Integer> entry : items.entrySet()) {
            int place = entry.getValue();
            ItemStack item = entry.getKey();
            inv.setItem(place, item);
        }
        p.openInventory(inv);
    }
    public HashMap<ItemStack, Integer> setSameItem(ArrayList<Integer> exceptionSlots, ItemStack sameItem, Integer size) {
        HashMap<ItemStack, Integer> map = new HashMap<>();
        for(int i=0;i<size;i++) {
            if(!exceptionSlots.contains(i)) {
                map.put(sameItem, i);
            }
        }

        return map;
    }

}
