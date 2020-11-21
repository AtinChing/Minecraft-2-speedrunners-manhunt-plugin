package me.atin.manhtwo.commands;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.atin.manhtwo.Fourth;
import net.md_5.bungee.api.ChatColor;
public class TargetsCommand implements CommandExecutor{
	private Fourth plugin;
	private Player cp; // currentplayer
	private List<Player> playerList;
	private int playerAmount;
	private int i;
	public ItemStack compass = new ItemStack(Material.COMPASS, 1);
	public TargetsCommand(Fourth plugin) {
		this.plugin = plugin;
		plugin.getCommand("targets").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
		Player p = (Player) sender; // Making an instance of the sender.	
			if(!p.hasPermission("targets.use")) {
			p.sendMessage("You need to have permission to use this command!");
			return true;
			}	
			else if(!(args.length == 2)) {
				p.sendMessage("Invalid!");
				return true;
			}
			else if(args.length  == 2 && p.hasPermission("targets.use")) { // If there are 2 arguments and the player has permission.
				Player vict1 =  Bukkit.getPlayer(args[0]); // Instance of first victim
				Player vict2 = Bukkit.getPlayer(args[1]); // Instance of second victim
				plugin.setVictims(vict1, vict2); // Setting the victims
				playerList = Bukkit.getServer().getWorld("world").getPlayers();
				playerAmount = playerList.size();
				plugin.manhuntIsOn = true;
				i = 0;
				while(i < playerAmount) {
					cp = playerList.get(i);
					if(!(cp == vict1 || cp == vict2) && !cp.getInventory().contains(Material.COMPASS)) {
						cp.getInventory().addItem(compass);
					}
					else if ((cp == vict1 || cp == vict2) && cp.getInventory().contains(Material.COMPASS)) {
						cp.getInventory().remove(Material.COMPASS);
					}
					i++;
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + "2 speedrunner Manhunt has successfully been turned on! The speedrunners are" + vict1.getName() + " and " + vict2.getName() + "!");
				return true;
			}
		}
		else {
			sender.sendMessage("You need to be a player to use this command");
		
		}
		return false;
	}
}
