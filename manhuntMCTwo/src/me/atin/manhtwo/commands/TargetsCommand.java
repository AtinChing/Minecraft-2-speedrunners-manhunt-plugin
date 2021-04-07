package me.atin.manhtwo.commands;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;

import me.atin.manhtwo.Fourth;
public class TargetsCommand implements CommandExecutor{
	private Fourth plugin;
	private Player player; // currentplayer
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
				p.sendMessage(ChatColor.RED + "You need to have permission to use this command!");
				return true;
			}	
			else if(!(args.length == 2 || args.length == 1)) {
				p.sendMessage(ChatColor.RED + "Invalid!");
				return true;
			}
			else if(args.length == 1 && p.hasPermission("targets.use")){ // If only one argument is passed in, then the player who called the command and the player who was passed in as an argument become the speedrunners
				Player victim1 = p; // One of the speedrunners gets set to the player that called the command itself
				Player victim2 = Bukkit.getPlayer(args[0]); // Other speedrunner is set to the one who was mentioned in the command call.
				plugin.setVictims(victim1, victim2);
				plugin.manhuntIsOn = true;
				for(Player player : p.getWorld().getPlayers()){
					if(player != victim1 && player != victim2 && !player.getInventory().contains(Material.COMPASS)){
						player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
					}
					else if((player == victim1 || player == victim2) && player.getInventory().contains(Material.COMPASS)){
						player.getInventory().remove(Material.COMPASS);
					}
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + "2 speedrunner Manhunt has successfully been turned on! The speedrunners are " + victim1.getName() + " and " + victim2.getName() + "!");
				return true;
			}
			else if(args.length  == 2 && p.hasPermission("targets.use")) { // If there are 2 arguments and the player has permission.
				Player victim1 =  Bukkit.getPlayer(args[0]); // Instance of first victim
				Player victim2 = Bukkit.getPlayer(args[1]); // Instance of second victim
				plugin.setVictims(victim1, victim2); // Setting the victims
				plugin.manhuntIsOn = true;
				for(Player player : p.getWorld().getPlayers()) {
					if(!(player == victim1 || player == victim2) && !player.getInventory().contains(Material.COMPASS)) {
						player.getInventory().addItem(compass);
					}
					else if ((player == victim1 || player == victim2) && player.getInventory().contains(Material.COMPASS)) {
						player.getInventory().remove(Material.COMPASS);
					}
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + "2 speedrunner Manhunt has successfully been turned on! The speedrunners are " + victim1.getName() + " and " + victim2.getName() + "!");
				return true;
			}
		}
		else {
			sender.sendMessage("You need to be a player to use this command");
		}
		return false;
	}
}
