package ru.kernel.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.kernel.EvoMine;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        EvoMine main = EvoMine.getPlugin(EvoMine.class);
        Player p = (Player) sender;
        if(args.length == 1 && p instanceof Player) {
            if(args[0].equalsIgnoreCase("reload") && p.hasPermission("evomine.reload")) {
                main.getPluginLoader().disablePlugin(main);
                main.getPluginLoader().enablePlugin(main);
                p.sendMessage();
            }
        }
        return true;
    }
}
