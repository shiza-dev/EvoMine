package ru.kernel.Utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.kernel.EvoMine;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemManager {

    private static ItemStack litter;
    private static ItemStack fuel;
    private static ItemStack melting_item;
    private static ItemStack grass;
    private static ItemStack flint;
    private static ItemStack stick;
    private static ItemStack pebble;
    private static ItemStack dryGrass;
    private static final EvoMine main = EvoMine.getPlugin(EvoMine.class);
    private static final FileConfiguration itemManager = main.setYamlConfig(main.getItemManager());

    // сеттеры

    /*
     ОСТОРОЖНО! Говнокод. Пишу на быструю руку, поэтому нет времени заморачиваться над автоматизацией
     сеттеров. Пока-что здесь будет всё одинаковое, позже перепишу под общий случай.
    */

    private static void setFuel() {
        fuel = new ItemStack(Material.OAK_LOG);
        ItemMeta meta = fuel.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Blocks").getConfigurationSection("fuel").getString("setFuel")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Blocks").getConfigurationSection("fuel").getString("loreSetFuel")));
        fuel.setItemMeta(meta);
    }

    private static void setMeltingItem() {
        melting_item = new ItemStack(Material.BARRIER);
        ItemMeta meta = melting_item.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Blocks").getConfigurationSection("melting_item").getString("setMeltingItem")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Blocks").getConfigurationSection("melting_item").getString("loreSetItem")));
        melting_item.setItemMeta(meta);
    }

    private static void setLitter() {
        litter = new ItemStack(Material.DARK_OAK_PRESSURE_PLATE);
        ItemMeta meta = litter.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Blocks").getConfigurationSection("litter").getString("setLitter")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Blocks").getConfigurationSection("litter").getString("loreSetLitter")));
        litter.setItemMeta(meta);
    }

    private static void setGrass() {
        grass = new ItemStack(Material.GRASS);
        ItemMeta meta = grass.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Items").getConfigurationSection("grass").getString("itemName")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Items").getConfigurationSection("grass").getString("itemLore")));
        grass.setItemMeta(meta);
    }

    private static void setDryGrass() {
        dryGrass = new ItemStack(Material.GRASS);
        ItemMeta meta = dryGrass.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Items").getConfigurationSection("dry_grass").getString("itemName")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Items").getConfigurationSection("dry_grass").getString("itemLore")));
        dryGrass.setItemMeta(meta);
    }

    private static void setPebble() {
        pebble = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = pebble.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Items").getConfigurationSection("pebble").getString("itemName")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Items").getConfigurationSection("pebble").getString("itemLore")));
        pebble.setItemMeta(meta);
    }

    private static void setFlint() {
        flint = new ItemStack(Material.FLINT);
        ItemMeta meta = flint.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Items").getConfigurationSection("piece_flint").getString("itemName")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Items").getConfigurationSection("piece_flint").getString("itemLore")));
        flint.setItemMeta(meta);
    }

    private static void setStick() {
        stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        meta.setDisplayName(main.getColorText(itemManager.getConfigurationSection("Items").getConfigurationSection("stick").getString("itemName")));
        meta.setLore(getLore(itemManager.getConfigurationSection("Items").getConfigurationSection("stick").getString("itemLore")));
        stick.setItemMeta(meta);
    }

    // геттеры
    public static ItemStack getGrass() { setGrass(); return grass; }

    public static ItemStack getFuel() { setFuel(); return fuel; }

    public static ItemStack getMeltingItem() {  setMeltingItem(); return melting_item; }

    public static ItemStack getLitter() { setLitter(); return litter; }

    public static ItemStack getPebble() { setPebble(); return pebble; }

    public static ItemStack getStick() { setStick(); return stick; }

    public static ItemStack getPieceFlint() { setFlint(); return flint; }

    public static ItemStack getDryGrass() { setDryGrass(); return dryGrass; }

    private static ArrayList<String> getLore(String str) {
        String coloredStr = main.getColorText(str);
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(coloredStr.split("\n")));
        return lore;
    }

}
