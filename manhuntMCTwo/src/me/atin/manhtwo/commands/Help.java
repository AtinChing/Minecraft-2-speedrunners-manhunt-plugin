package me.atin.manhtwo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.atin.manhtwo.Fourth;

public class Help implements CommandExecutor{
	public Help(Fourth plugin) {
	plugin.getCommand("helptargets").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
		if(send instanceof Player) {
			Player p = (Player) send;
			if(args.length != 0){
				p.sendMessage(ChatColor.RED + "/helptargets does not take any arguments!");
			}
			else if(args.length == 0) {
				p.sendMessage(ChatColor.BLUE + "This plugin is the 2-speedrunner manhunt, which is a remake of the original Minecraft Manhunt plugin made by Dream intended for only one speedrunner. This plugin lets you play minecraft manhunt with two players.");
				p.sendMessage(ChatColor.BLUE + "To activate the manhunt, you need to use /enabletargets [playername1] [playername2]. Instead of [playername1] and [playername2], you have to insert the actual names of the two speedrunners.");
				p.sendMessage(ChatColor.BLUE + "Once the manhunt is activated, hunters that don't have a compass are given a compass to them while speedrunners that do have a compass get their compass taken away from them.");
				p.sendMessage(ChatColor.BLUE + "Hunters can right click while holding the compass to point to the a speedrunner. Everytime you right click, you alternate between the two speedrunners.");
				p.sendMessage(ChatColor.BLUE + "The hunters can't obtain another compass through any method. The speedrunners can't get a compass through any method either.");
				p.sendMessage(ChatColor.BLUE + "To stop the manhunt, you have to use /stoptargets. /stoptargets does not take any arguments.");
				p.sendMessage(ChatColor.YELLOW + "Made by AtinChing on GitHub and SpigotMC.");
			}
			else {
				p.sendMessage(ChatColor.RED + "Sorry, it looks you haven't used this command properly.");
			}
		}
		else {
			send.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
		}
		return false;
	}
}
