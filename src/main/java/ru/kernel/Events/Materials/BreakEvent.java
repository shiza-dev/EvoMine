package ru.kernel.Events.Materials;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ru.kernel.Utils.ItemManager;

import java.util.Random;

public class BreakEvent implements Listener {

    @EventHandler
    public void event(BlockBreakEvent e) {

        Random r = new Random();
        Location loc = e.getBlock().getLocation();
        double c = r.nextDouble();

        switch (e.getBlock().getType()) {
            case GRASS:
            case TALL_GRASS:
                if(c <= 0.6 && c > 0.3) {
                    ItemStack grass = ItemManager.getGrass();
                    loc.getWorld().dropItem(loc, grass);
                } else if(c <= 0.3) {
                    ItemStack dryGrass = ItemManager.getDryGrass();
                    loc.getWorld().dropItem(loc, dryGrass);
                }
                break;
            case STONE_BUTTON:
                ItemStack pebble = ItemManager.getPebble();
                loc.getWorld().dropItem(loc, pebble);
                break;
            case ACACIA_LEAVES:
            case AZALEA_LEAVES:
            case BIRCH_LEAVES:
            case DARK_OAK_LEAVES:
            case FLOWERING_AZALEA_LEAVES:
            case JUNGLE_LEAVES:
            case OAK_LEAVES:
            case SPRUCE_LEAVES:
                if(c <= 0.5) {
                    ItemStack stick = ItemManager.getStick();
                    loc.getWorld().dropItem(loc, stick);
                }
                break;
            case GRAVEL:
                if(c <= 0.3) {
                    ItemStack flint = ItemManager.getPieceFlint();
                    loc.getWorld().dropItem(loc, flint);
                }
        }
    }
}
