package me.atin.manhtwo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.atin.manhtwo.commands.Help;
import me.atin.manhtwo.commands.StopTargetsCommand;
import me.atin.manhtwo.commands.TargetsCommand;

public class Fourth extends JavaPlugin implements Listener {
	public Location location1;
	public Location location2;
	public Player victim1;
	public Player victim2;
	public boolean whichPlayer = false;
	public boolean manhuntIsOn = false;
	@Override
	public void onEnable() {
		new TargetsCommand(this);
		new StopTargetsCommand(this);
		new Help(this);
		Bukkit.getPluginManager().registerEvents(this, this); // Registers the plugin and listener (which we've defined in the declaration of this class).
	}
	public void setVictims(Player victim1, Player victim2) {
		this.victim1 = victim1;
		this.victim2 = victim2;
	}
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) { // When player interacts with something.
		if(manhuntIsOn) {
			Action action = event.getAction();
			Player player = event.getPlayer();
			ItemStack item = event.getItem();
			if(item.getType() != Material.COMPASS) return; // If the item is anything other than a compass.
			if((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) && !(player == victim1 || player == victim2)) { // If the player isn't a victim and they've right clicked the air AND if they're holding a compass.
				location1 = victim1.getLocation(); // Storing the locations of the 2 players.
				location2 = victim2.getLocation();
				whichPlayer = !whichPlayer; // Reversing the value of which player, so that we can use an if statement to return a different players position on every single right click with the compass. 
				if(whichPlayer) { // If whichplayer is true then compasses point to player1's position. 
					player.setCompassTarget(location1); // compasses (only for the player that right clicked with the compass) update to player1's position.
					player.sendMessage(ChatColor.GREEN + "Compass is now pointing to " + victim1.getName());
				}
				else if (!whichPlayer) { // If whichplayer is false then compasses point to player2's position.
					player.setCompassTarget(location2); // compasses (only for the player that right clicked with the compass) update to player2's position.
					player.sendMessage(ChatColor.DARK_GREEN + "Compass is now pointing to " + victim2.getName());
				}
		    }
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(player.isOp()){
			player.sendMessage(ChatColor.YELLOW + "Please make sure you have server OP, otherwise you won't be able to use the /targets and /stoptargets commands to play 2 Speedrunner Manhunt");
		}
	}
	@EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
		if(manhuntIsOn) {
			Entity pickuping = event.getEntity();
			ItemStack item = event.getItem().getItemStack();
			if(pickuping.getType() == EntityType.PLAYER) { // If entity type is player
				HumanEntity player = (HumanEntity) pickuping; // Player
				if((player.getInventory().contains(Material.COMPASS) || player == victim1 || player == victim2) && item.getType() == Material.COMPASS) { // If player has compass and is trying to pickup compass.
					event.setCancelled(true); // Cancels the pickup event
					
				}
			}
		}
	}
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		if(manhuntIsOn) {
			Player player = event.getPlayer();
			Item item = event.getItemDrop();
			if(!(player == victim1 || player == victim2) && item.getItemStack().getType() == Material.COMPASS) { // If player is not a victim and is trying to throw a compass
				event.setCancelled(true); // Cancels event.
			}
		}
	}
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if(manhuntIsOn) {
		ItemStack item = event.getInventory().getResult();
			if(item.getType() == Material.COMPASS) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(manhuntIsOn) {
			Player p = (Player) event.getWhoClicked(); // The player who clicked on the item
			ItemStack item = event.getCurrentItem(); // Item that the players clicked on
			InventoryType it = event.getInventory().getType(); // The inventory type of the block open.
			if(item.getType() == Material.COMPASS && (p == victim1 || p == victim2)) {
				event.setCancelled(true); // Cancel this event if a target/speedrunner tries to click on a compass in a chest or their inventory.
			}
			else if(!(p == victim1 || p == victim2) && !(it.equals(InventoryType.PLAYER))) {
				event.setCancelled(true); // Prevents the hunter from getting a compass from a chest or any other storage item (meaning they can only click and move the compass around in their own inventory.)
			}
		}
	}
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if(manhuntIsOn) {
			Player p = event.getPlayer();
			if(!(p == victim1 || p == victim2)) {
				p.getInventory().addItem(new ItemStack(Material.COMPASS, 1)); // If the person who has respawned isn't a speedrunner then they will respawn with a compass in their inventory.
			}
		}
	}
	

}