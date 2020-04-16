/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.listeners;

import garusnetwork.gnkit.Main;
import garusnetwork.gnkit.db.CategoriaDB;
import garusnetwork.gnkit.db.KitsDB;
import garusnetwork.gnkit.db.ReceivedDB;
import garusnetwork.gnkit.enums.KitMessages;
import garusnetwork.gnkit.events.CategoriaClickEvent;
import garusnetwork.gnkit.events.KitCommandEvent;
import garusnetwork.gnkit.events.KitReceiveEvent;
import garusnetwork.gnkit.manager.ReceivedManager;
import garusnetwork.gnkit.objects.Categoria;
import garusnetwork.gnkit.objects.Cooldown;
import garusnetwork.gnkit.objects.Received;
import garusnetwork.gnkit.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jhow
 */
public class Listeners implements org.bukkit.event.Listener {

    @EventHandler
    public void onKitCommand(KitCommandEvent e) {
        Inventory inv = Bukkit.createInventory(null, 27, "Categorias KIT");
        CategoriaDB.categorias.values().forEach((categoria) -> {
            ItemMeta im = categoria.getItem().getItemMeta();
            im.setDisplayName(categoria.getDisplayName());
            im.setLore(categoria.getLore());
            categoria.getItem().setItemMeta(im);
            String i = Utils.itemToString(categoria.getItem());
            inv.setItem(categoria.getItemSlot(), Utils.stringToItem(i));
        });
        e.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void onClickCategoria(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != null) {
            if (e.getInventory().getTitle().equals("Categorias KIT")) {
                e.setCancelled(true);
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    CategoriaDB.categorias.values().stream().filter((categoria) -> (e.getCurrentItem().getItemMeta().getDisplayName().equals(categoria.getDisplayName()))).forEachOrdered((categoria) -> {
                        e.getWhoClicked().closeInventory();
                        Bukkit.getPluginManager().callEvent(new CategoriaClickEvent((Player) e.getWhoClicked(), categoria));
                    });
                }
            } else {
                CategoriaDB.categorias.values().stream().filter((categoria) -> (e.getInventory().getTitle().equals(categoria.getDisplayName()))).forEachOrdered((_item) -> {
                    e.setCancelled(true);
                    if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                        KitsDB.kits.values().stream().filter((kit) -> (e.getCurrentItem().getItemMeta().getDisplayName().equals(kit.getDisplayName()))).forEachOrdered((kit) -> {
                            e.getWhoClicked().closeInventory();
                            Bukkit.getPluginManager().callEvent(new KitReceiveEvent(kit, (Player) e.getWhoClicked()));
                        });
                    }
                });
            }
        }
    }

    @EventHandler
    public void onOpenKit(CategoriaClickEvent e) {
        Inventory inv = Bukkit.createInventory(null, 54, e.getCategoria().getDisplayName());
        e.getCategoria().getKits().forEach((kname) -> {
            KitsDB.kits.values().stream().filter((kit) -> (kit.getName().equals(kname))).forEachOrdered((kit) -> {
                //
                ItemMeta im = kit.getItem().getItemMeta();
                im.setDisplayName(kit.getDisplayName());
                im.setLore(kit.getLore());
                kit.getItem().setItemMeta(im);

                String i = Utils.itemToString(kit.getItem());

                inv.setItem(kit.getItemSlot(), Utils.stringToItem(i));
            });
        });
        e.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void onReceiveKit(KitReceiveEvent e) {
        if (ReceivedDB.receiveds.containsKey(e.getPlayer().getName())) {
            if (ReceivedDB.receiveds.get(e.getPlayer().getName()).getUsedKits().size() > 0) {
                for (Cooldown cooldown : ReceivedDB.receiveds.get(e.getPlayer().getName()).getUsedKits().values()) {
                    if (cooldown.getKit() == e.getKit()) {
                        e.getPlayer().sendMessage(KitMessages.ERROR_ALREADY_RECEIVED.getMessage());
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (!e.isCancelled()) {
            if (e.getPlayer().hasPermission("gnkits.kit." + e.getKit().getName()) || e.getPlayer().isOp()) {
                //if (e.getPlayer().getInventory().firstEmpty() != -e.getKit().getItems().size()) {
                    List<String> items = new ArrayList<>();
                    e.getKit().getItems().forEach((i) -> {
                        items.add(Utils.itemToString(i));
                    });
                    items.forEach((is) -> {
                        e.getPlayer().getInventory().addItem(Utils.stringToItem(is));
                    });
                    e.getPlayer().sendMessage(KitMessages.SUCESS_RECEIVE_KIT.getMessage());
                    if (!e.getPlayer().isOp() || !e.getPlayer().hasPermission("gnkits.admin")) {
                        if (!ReceivedDB.receiveds.containsKey(e.getPlayer().getName())) {
                            Received received = new Received(e.getPlayer().getName());
                            received.getUsedKits().put(e.getKit(), new Cooldown(e.getKit(), e.getKit().getCooldown()));
                            ReceivedDB.receiveds.put(e.getPlayer().getName(), received);
                            new ReceivedManager(received).set();
                        } else {
                            ReceivedDB.receiveds.get(e.getPlayer().getName()).getUsedKits().put(e.getKit(), new Cooldown(e.getKit(), e.getKit().getCooldown()));
                        }
                    }
               // } else {
               //     e.getPlayer().sendMessage(KitMessages.ERROR_INVENTORY_FULL.getMessage());
              //  }
            } else {
                e.getPlayer().sendMessage(KitMessages.SEM_PERM.getMessage());
            }
        }
    }
}
