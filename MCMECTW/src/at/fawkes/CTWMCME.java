package at.fawkes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CTWMCME extends JavaPlugin {

    @Override
    public void onEnable() {
        //registering Listener
        getServer().getPluginManager().registerEvents(new CTWMCMEListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        //Identify the player
        Player player = sender.getServer().getPlayer(sender.getName());
        if (cmd.getName().equalsIgnoreCase("find")) {
            String color = args[0];
            String Rname = CTWMCMEListener.Rcarrier;
            String Bname = CTWMCMEListener.Bcarrier;
            String Gname = CTWMCMEListener.Gcarrier;
            if ((color.equalsIgnoreCase("red")) && Rname != null) {
                player.sendMessage(ChatColor.RED + Rname + " has the red wool.");
            }
            if ((color.equalsIgnoreCase("blue")) && Bname != null) {
                player.sendMessage(ChatColor.BLUE + Bname + " has the blue wool.");
            }
            if ((color.equalsIgnoreCase("green")) && Gname != null) {
                player.sendMessage(ChatColor.GREEN + Gname + " has the green wool.");
            }
            if (!(color.equalsIgnoreCase("red") || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green")) && !(args.length == 0)) {
                player.sendMessage(ChatColor.WHITE + color + " is not a valid color");
            } else if (color.isEmpty()) {
                player.sendMessage(ChatColor.RED + Rname + " has the red wool.");
                player.sendMessage(ChatColor.BLUE + Bname + " has the blue wool.");
                player.sendMessage(ChatColor.GREEN + Gname + " has the green wool.");
            }

            return true;
        }
        return true;
    }
}
