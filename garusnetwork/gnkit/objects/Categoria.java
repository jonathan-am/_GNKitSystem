/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.objects;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jhow
 */
public class Categoria {

    public List<String> kits;
    public List<String> lore;
    public String name;
    public String displayname;
    public ItemStack item;
    public int item_slot;

    public Categoria() {
        kits = new ArrayList<>();
        lore = new ArrayList<>();
    }

    public List<String> getKits() {
        return this.kits;
    }

    public List<String> getLore() {
        return this.lore;
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

    public Categoria setItemSlot(int slot) {
        this.item_slot = slot;
        return this;
    }

}
