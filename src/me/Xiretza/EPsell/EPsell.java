package me.Xiretza.EPsell;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Xiretza.EPsell.Commands.CommandEPS;
import net.milkbowl.vault.economy.Economy;

public class EPsell extends JavaPlugin {
	
	Economy economy;
	String nameC = "[EPsell] ";
	public String nameP = ChatColor.RED + "[EPsell] " + ChatColor.GREEN;
	
	@Override
	public void onEnable() {
		System.out.println(nameC + "Plugin by and Xiretza");
		System.out.println(nameC + "Plugin activated");
		prepareConfig();
		prepareEconomy();
	}

	@Override
	public void onDisable() {
		System.out.println(nameC + "Plugin deactivated");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		Player p = (Player)sender;
		
		//Wenn Kommando "eps"
		if (cmd.getName().equalsIgnoreCase("eps")){
			CommandEPS cmdcps = new CommandEPS(this, p, args);
			return cmdcps.execute();
		}
		
		return false;
	}
	
	private void prepareConfig() {
		
		this.getConfig().options().copyDefaults(true);
		this.getConfig().addDefault("EPsell.usePerm", false);
		this.getConfig().addDefault("EPsell.permission", "EPsell.execute");
		this.getConfig().addDefault("EPsell.parity", 5.00);
		this.saveConfig();
	}
	
	private boolean prepareEconomy() {
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		
		return (economy != null);
	}
	
	public void addMoney(String player, double amountXP) {
		
		if(economy.isEnabled()) {
			
			double amount = amountXP * this.getConfig().getInt("EPsell.parity");
			economy.depositPlayer(player, amount);
			
			Player p = this.getServer().getPlayer(player);
			
			p.setLevel(p.getLevel() - (int) amountXP);
			
			p.sendMessage(nameP + "Sucessful " + amountXP + " XP for " + amount + " " + economy.currencyNamePlural() + " selled.");
			p.sendMessage(nameP + "Your current balance: " + economy.getBalance(player) + " " + economy.currencyNamePlural());
		}
	}
}

