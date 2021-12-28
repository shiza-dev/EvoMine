package ru.kernel.Events.GroundFurnace;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.kernel.Database.DatabaseRouter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveBlock implements Listener {

    @EventHandler
    public void event(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(block.getType() == Material.HAY_BLOCK) {
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();

            try {
                ResultSet resSet = DatabaseRouter.read("SELECT * FROM ground_furnace WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "';");
                if(resSet != null) {
                    DatabaseRouter.write("DELETE FROM ground_furnace WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "';");
                }
            } catch(SQLException | ClassNotFoundException err) {
                err.printStackTrace();
            }
        }
    }
}
