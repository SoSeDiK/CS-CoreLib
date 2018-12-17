package me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem extends ItemStack {
	
	public MenuItem(Material type, String name, int amount, int durability, String action) {
		super(type, amount);
		ItemMeta im = getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GREEN + "> Нажмите, чтобы " + action);
		im.setLore(lore);
		setItemMeta(im);
		setDurability((short) durability);
	}
	
	public MenuItem(Material type, String name, int durability, String action) {
		super(type);
		ItemMeta im = getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GREEN + "> Нажмите, чтобы " + action);
		im.setLore(lore);
		setItemMeta(im);
		setDurability((short) durability);
	}
	// MY NEW
	public MenuItem(Material type, String name, int durability, String action, String[] lores) {
		super(type);
		ItemMeta im = getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GREEN + "> Нажмите, чтобы " + action);
		for (int i = 0; i < lores.length; i++)
			lore.add(ChatColor.translateAlternateColorCodes('&', lores[i]));
		im.setLore(lore);
		setItemMeta(im);
		setDurability((short) durability);
	}

}
