package me.mrCookieSlime.CSCoreLibPlugin.protection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.protection.modules.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ProtectionManager {

	private Config local = new Config("plugins/CS-CoreLib/protection.yml");
	private Set<ProtectionModule> modules = new HashSet<ProtectionModule>();

	public void registerNewModule(String name, ProtectionModule module) {
		this.modules.add(module);
		this.loadModuleMSG(name);
		local.setDefaultValue("plugins." + name + ".no-block-access", "&4&l! &cИзвините, но плагин &4" + name + " &cне дал Вам доступа к этому блоку");
		local.setDefaultValue("plugins." + name + ".no-block-breaking", "&4&l! &cИзвините, но плагин &4" + name + " &cне разрешает Вам разрушать этот блок");
		local.save();
	}

	private void loadModuleMSG(String module) {
		System.out.println("[CS-CoreLib - Protection] Загружен модуль защиты \"" + module + "\"");
	}
	
	public ProtectionManager() {}

	public ProtectionManager(CSCoreLib cscorelib) {
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("WorldGuard") && cscorelib.getServer().getPluginManager().isPluginEnabled("WorldEdit")) {
			registerNewModule("WorldGuard", new WorldGuardProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("Factions")) {
			if (cscorelib.getServer().getPluginManager().getPlugin("Factions") instanceof com.massivecraft.factions.P)
				registerNewModule("Factions", new FactionsUUIDProtectionModule());
			else
				registerNewModule("Factions", new FactionsProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("Towny")) {
			registerNewModule("Towny", new TownyProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("GriefPrevention")) {
			registerNewModule("GriefPrevention", new GriefPreventionProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("ASkyBlock")) {
			registerNewModule("ASkyBlock", new ASkyBlockProtectionModule());
		}
		if(cscorelib.getServer().getPluginManager().isPluginEnabled("LWC")){
			registerNewModule("LWC", new LWCProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("PreciousStones")) {
			registerNewModule("PreciousStones", new PreciousStonesProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("Lockette")) {
			registerNewModule("Lockette", new LocketteProtectionModule());
		}
		if(cscorelib.getServer().getPluginManager().isPluginEnabled("ProtectionStones")) {
            this.loadModuleMSG("ProtectionStones");
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("uSkyblock")) {
			this.loadModuleMSG("uSkyblock");
		}
		if(cscorelib.getServer().getPluginManager().isPluginEnabled("PlotSquared")) {
			registerNewModule("PlotSquared", new PlotSquaredProtectionModule());
		}
		if (cscorelib.getServer().getPluginManager().isPluginEnabled("RedProtect")) {
			registerNewModule("RedProtect", new RedProtectProtectionModule());
		}
	}

	public boolean canBuild(UUID uuid, Block b) {
		return this.canBuild(uuid, b, false);
	}

	public boolean canAccessChest(UUID uuid, Block b) {
		return this.canAccessChest(uuid, b, false);
	}

	public boolean canBuild(UUID uuid, Block b, boolean message) {
		Player player = Bukkit.getPlayer(uuid);
		if (player == null) return false;

		for (ProtectionModule module: modules) {
			if (!module.canBuild(player, b)) {
				if (message) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', local.getString("plugins." + module.getName() + ".no-block-breaking")));
				}
				return false;
			}
		}

		return true;
	}

	public boolean canAccessChest(UUID uuid, Block b, boolean message) {
		Player player = Bukkit.getPlayer(uuid);
		if (player == null) return false;

		for (ProtectionModule module: modules) {
			if (!module.canAccessChest(player, b)) {
				if (message) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', local.getString("plugins." + module.getName() + ".no-block-access")));
				}
				return false;
			}
		}

		return true;
	}

}
