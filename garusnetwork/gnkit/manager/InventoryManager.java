/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Jhow
 */
public class InventoryManager {

    private Player player = null;
    private String title = null;
    private final Inventory inventory;
    private int size = 0;

    public InventoryManager(Player p, String title, int size) {
        this.player = p;
        this.title = title;
        this.size = size;
        inventory = Bukkit.createInventory(null, size, title);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryManager open() {
        player.openInventory(inventory);
        return this;
    }
}
