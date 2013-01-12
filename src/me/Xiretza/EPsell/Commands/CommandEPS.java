package me.Xiretza.EPsell.Commands;

import org.bukkit.entity.Player;

import me.Xiretza.EPsell.EPsell;

public class CommandEPS {

	EPsell pl;
	Player p;
	String[] args;

	public CommandEPS(EPsell pl, Player p, String[] args) {

		this.pl = pl;
		this.args = args;
		this.p = p;
	}

	public boolean execute() {
		
		if (args.length == 0 || args.length > 1) {
			p.sendMessage(pl.nameP + "Mache /eps <Anzahl|all>");
			return true;
		}

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("all")) {

				if (p.getGameMode().getValue() == 1 && p.getLevel() == 0) {

					p.sendMessage(pl.nameP
							+ "Du hast keine unlimitierten XP im Kreativmodus.");
					p.sendMessage(pl.nameP
							+ "Bitte benutze /eps <Anzahl> um beliebig viele XP im Kretivmodus zu verkaufen.");
					return true;
				}

				if (p.getLevel() == 0) {

					p.sendMessage(pl.nameP + "Du hast keine XP zum verkaufen.");
					return true;
				}

				int lvl = p.getLevel();

				pl.addMoney(p.getName(), new Double(lvl));

				return true;

			} else {

				int lvl;

				try {
					lvl = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					p.sendMessage(pl.nameP + "Du musst eine Zahl angeben!");
					return false;
				}

				if (lvl == 0) {

					p.sendMessage(pl.nameP + "Du kannst nicht 0 XP verkaufen.");
					return true;
				}

				if (p.getLevel() < lvl && p.getGameMode().getValue() != 1) {

					p.sendMessage(pl.nameP + "Zu wenig XP!");
					return true;
				}

				pl.addMoney(p.getName(), new Double(lvl));

			}
		}
		return true;
	}
}
