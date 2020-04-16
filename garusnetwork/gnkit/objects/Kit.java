/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.objects;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jhow
 */
public class Kit {

    public List<ItemStack> items;
    public List<String> lore;
    public String name;
    public String displayname;
    public ItemStack item;
    public int item_slot = 0;
    public Categoria categoria;
    public int cooldown = 0;

    public Kit() {
        this.items = new ArrayList<>();
        this.lore = new ArrayList<>();
    }

    public List<String> getLore() {
        return this.lore;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return this.displayname;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getItemSlot() {
        return this.item_slot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
    }

    public void setItem(ItemStack id) {
        this.item = id;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(int time) {
        this.cooldown = time;
    }

    public Kit setItemSlot(int slot) {
        this.item_slot = slot;
        return this;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
