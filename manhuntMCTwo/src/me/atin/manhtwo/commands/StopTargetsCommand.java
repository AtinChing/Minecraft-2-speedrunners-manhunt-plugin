package me.atin.manhtwo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.atin.manhtwo.Fourth;
import org.bukkit.ChatColor;

public class StopTargetsCommand implements CommandExecutor{
	private Fourth plugin; 
 public StopTargetsCommand(Fourth plugin) {
	this.plugin = plugin;
	plugin.getCommand("stoptargets").setExecutor(this);
 }
@Override
 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(sender instanceof Player) {
		Player p = (Player) sender;
		if(!(args.length == 0)) {
			p.sendMessage(ChatColor.RED + "/stoptargets doesn't take any arguements!");
			return true;
		}
		else if (!p.hasPermission("stoptargets.use")) {
			p.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
			return true;
		}
		else if(!plugin.manhuntIsOn) {
			p.sendMessage(ChatColor.RED + "2 Speedrunner manhunt is already turned off."); // If manhunt is already on.
			return true;
		}
		
		else if(args.length == 0 && p.hasPermission("stoptargets.use")) {
			plugin.manhuntIsOn = false; // If the conditions are met to turn of the manhunt.
			Bukkit.broadcastMessage(ChatColor.GREEN + "2 speedrunner manhunt has successfully been turned off.");
			
			return true;
		}
		
		else {
			p.sendMessage(ChatColor.RED + "Sorry, you haven't used the command properly.");
			return true;
		}
	}
	else {
		sender.sendMessage(ChatColor.RED + "Only players can use this command!");
	}
	
	return false;
 }
}
