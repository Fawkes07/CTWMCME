package at.fawkes;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class CTWMCMEListener implements Listener {

    public static int Diamond = 2;
    public static int Gold = 2;
    public static final short Redshort = 14;
    public static final short Blueshort = 11;
    public static final short Greenshort = 13;
    public static final byte Redbyte = 14;
    public static final byte Bluebyte = 11;
    public static final byte Greenbyte = 13;
    public static ItemStack Rwoolitem = new ItemStack(Material.WOOL, 1, Redshort);
    public static ItemStack Bwoolitem = new ItemStack(Material.WOOL, 1, Blueshort);
    public static ItemStack Gwoolitem = new ItemStack(Material.WOOL, 1, Greenshort);
    public static BlockFace DOWN = BlockFace.DOWN;
    public static String Rcarrier = "Gold team base";
    public static String Bcarrier = "Diamond team base";
    public static String Gcarrier = "No one";

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.WOOL) {
            Player carrier = event.getPlayer();
            Wool Pwool = new Wool(event.getBlock().getType(), event.getBlock().getData());
            String color = Pwool.getColor().toString().toUpperCase();
            Block under = event.getBlock().getRelative(DOWN);
            Material undertype = under.getType();
            if (undertype == Material.DIAMOND_BLOCK) {
                Diamond--;
                if (event.getBlock().getData() == 14) {
                    Rcarrier = "Diamond team base";
                }
                if (event.getBlock().getData() == 11) {
                    Bcarrier = "Diamond Team Base";
                }
                if (event.getBlock().getData() == 13) {
                    Gcarrier = "Diamond Team Base";
                }
                if (Diamond == 0) {
                    sendMessage(ChatColor.RED + "Diamond Team has aquired all the wool. GAME OVER, Diamond Team wins!");
                } else {
                    sendMessage(ChatColor.YELLOW + "Diamond Team has aquired the " + color + " wool, " + Diamond + " more to win!");
                }
            }
            if ((undertype == Material.GOLD_BLOCK)) {
                Gold--;
                if (event.getBlock().getData() == 14) {
                    Rcarrier = "Gold Team Base";
                }
                if (event.getBlock().getData() == 11) {
                    Bcarrier = "Gold Team Base";
                }
                if (event.getBlock().getData() == 13) {
                    Gcarrier = "Gold Team Base";
                }
                if (Gold == 0) {
                    sendMessage(ChatColor.RED + "Gold Team has aquired all the wool. GAME OVER, Gold Team wins!");
                } else {
                    sendMessage(ChatColor.YELLOW + "Gold Team has aquired the " + color + " wool, " + Gold + " more to win!");
                }
            } else if (!(undertype == Material.GOLD_BLOCK || undertype == Material.DIAMOND_BLOCK) && !(carrier.hasPermission("wool.place"))) {
                carrier.sendMessage(ChatColor.RED + "You can only place this on a podium!");
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUp(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().equals(Rwoolitem)) {
            sendMessage(ChatColor.GREEN + event.getPlayer().getName() + " has aquired the RED wool!");
            setCarrier(executor, "red");
        }
        if (event.getItem().getItemStack().equals(Bwoolitem)) {
            sendMessage(ChatColor.GREEN + event.getPlayer().getName() + " has aquired the BLUE wool!");
            setCarrier(executor, "blue");
        }
        if (event.getItem().getItemStack().equals(Gwoolitem)) {
            sendMessage(ChatColor.GREEN + event.getPlayer().getName() + " has aquired the GREEN wool!");
            setCarrier(executor, "green");
        }
    }
    Block blok;
    Collection<ItemStack> drops;
    Player executor;
    String Wcolor;
    Wool wool1;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.WOOL) {
            blok = event.getBlock();
            wool1 = new Wool(event.getBlock().getType(), event.getBlock().getData());
            Wcolor = wool1.getColor().toString().toUpperCase();
            drops = blok.getDrops();
            executor = event.getPlayer();
        }
    }
    
    public void onDeath(PlayerDeathEvent event) {
        Player died = event.getEntity();
        executor = died.getKiller();
        if (!(died.getKiller() instanceof Player) && died.getInventory().contains(Rwoolitem)){
            byte red = 14;
            Block replace = event.getEntity().getLocation().getBlock();
            replace.setTypeIdAndData(35, red, true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void removeent(ItemSpawnEvent event) {
        Block spawnloc = event.getLocation().getBlock();
        Block underblok = blok.getRelative(DOWN);
        Material blokundertype = underblok.getType();
        if (spawnloc.equals(blok)) {
            event.setCancelled(true);
            if (drops.contains(Rwoolitem)) {
                setCarrier(executor, "red");
            }
            if (drops.contains(Bwoolitem)) {
                setCarrier(executor, "blue");
            }
            if (drops.contains(Gwoolitem)) {
                setCarrier(executor, "green");
            }
            executor.getInventory().addItem(drops.iterator().next());
            if (blok.getRelative(DOWN).getType() == Material.DIAMOND_BLOCK) {
                Diamond++;
                sendMessage(ChatColor.DARK_RED + executor.getName() + " has stolen the " + wool1.getColor().toString().toUpperCase() + " wool from Diamond team!");
            }
            if (blok.getRelative(DOWN).getType() == Material.GOLD_BLOCK) {
                Gold++;
                sendMessage(ChatColor.DARK_RED + executor.getName() + " has stolen the " + wool1.getColor().toString().toUpperCase() + " wool from Gold team!");
            } else if (!(blokundertype == Material.GOLD_BLOCK || blokundertype == Material.DIAMOND_BLOCK)) {
                sendMessage(ChatColor.GREEN + executor.getName() + " has aquired the " + wool1.getColor().toString().toUpperCase() + " wool!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThrow(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack throwing = event.getItemDrop().getItemStack();
        if (throwing.equals(Rwoolitem) || throwing.equals(Bwoolitem) || throwing.equals(Gwoolitem)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Don't drop the wool dummy!");
        }
    }

    public void sendMessage(String msg) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(msg);
        }

    }

    public void setCarrier(Player player, String color) {
        if (color.equalsIgnoreCase("red")) {
            Rcarrier = player.getName();
        }
        if (color.equalsIgnoreCase("blue")) {
            Bcarrier = player.getName();
        }
        if (color.equalsIgnoreCase("green")) {
            Gcarrier = player.getName();
        }
    }
}
