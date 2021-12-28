package ru.kernel.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiUtil {

    private Inventory inv;

    public void createGui(Player p, String name, Integer size, InventoryHolder holder, HashMap<Integer, ItemStack> items) {

        inv = Bukkit.createInventory(holder, size, ChatColor.translateAlternateColorCodes('&', name));

        for(Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            int place = entry.getKey();
            ItemStack item = entry.getValue();
            inv.setItem(place, item);
        }
        p.openInventory(inv);
    }
    public HashMap<Integer, ItemStack> setItems(HashMap<Integer, ItemStack> exceptionItems, ItemStack sameItem, Integer size) {
        HashMap<Integer, ItemStack> map = new HashMap<>();
            for (int i = 0; i < size; i++) {
                if (!exceptionItems.containsKey(i)) {
                    map.put(i, sameItem);
                } else {
                    map.put(i, exceptionItems.get(i));
                }
            }
        return map;
    }

}
