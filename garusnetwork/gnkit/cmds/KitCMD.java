/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.cmds;

import garusnetwork.gnkit.db.CategoriaDB;
import garusnetwork.gnkit.db.KitsDB;
import garusnetwork.gnkit.enums.CategoriaMessages;
import garusnetwork.gnkit.enums.KitMessages;
import garusnetwork.gnkit.events.KitCommandEvent;
import garusnetwork.gnkit.manager.KitManager;
import garusnetwork.gnkit.objects.Categoria;
import garusnetwork.gnkit.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jhow
 */
public class KitCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            switch (strings.length) {
                case 0:
                    if (p.hasPermission("gnkits.admin") || p.isOp()) {
                        sendHelpMSG(p);
                    }
                    Bukkit.getPluginManager().callEvent(new KitCommandEvent(p));
                    break;
                default:
                    if (strings[0].equalsIgnoreCase("addlore")) {//ADDLORE
                        if (p.isOp() || p.hasPermission("gnkits.admin")) {
                            String nome = strings[1];
                            if (KitsDB.kits.containsKey(nome)) {
                                StringBuilder me = new StringBuilder();
                                int i = 0;
                                while (i < strings.length) {
                                    me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                    ++i;
                                }
                                KitsDB.kits.get(nome).lore.add(me.toString().split(nome + " ")[1]);
                                p.sendMessage(KitMessages.SUCESS_ADD_LORE.getMessage());
                            } else {
                                p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("setname")) {//SETNAME
                        if (p.isOp() || p.hasPermission("gnkits.admin")) {
                            String nome = strings[1];
                            if (KitsDB.kits.containsKey(nome)) {
                                StringBuilder me = new StringBuilder();
                                int i = 0;
                                while (i < strings.length) {
                                    me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                    ++i;
                                }
                                KitsDB.kits.get(nome).setDisplayName(me.toString().split(nome + " ")[1]);
                                p.sendMessage(KitMessages.SUCESS_SET_DISPLAYNAME.getMessage());
                            } else {
                                p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                            }
                        } else {
                            p.sendMessage(KitMessages.SEM_PERM.getMessage());
                        }
                    } else if (p.hasPermission("gnkits.admin") || p.isOp()) {
                        sendHelpMSG(p);
                    } else {
                        Bukkit.getPluginManager().callEvent(new KitCommandEvent(p));
                    }
                    break;
                case 2:
                    if (p.isOp() || p.hasPermission("gnkits.admin")) {
                        if (strings[0].equalsIgnoreCase("create")) {//CREATE
                            if (strings[1] != null) {
                                String nome = strings[1];
                                if (!KitsDB.kits.containsKey(nome)) {
                                    Kit kit = new Kit();
                                    kit.setName(nome);
                                    new KitManager(kit).set();
                                    KitsDB.kits.put(nome, kit);
                                    p.sendMessage(KitMessages.SUCESS_CREATE.getMessage());
                                } else {
                                    p.sendMessage(KitMessages.ALREADY_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("delete")) {//DELETE
                            if (strings[1] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    if (KitsDB.kits.get(nome).getCategoria() != null) {
                                        Categoria categoria = KitsDB.kits.get(nome).getCategoria();
                                        for (int i = 0; i < CategoriaDB.categorias.get(categoria.getName()).kits.size(); i++) {
                                            if (CategoriaDB.categorias.get(categoria.getName()).kits.get(i).equalsIgnoreCase(nome)) {
                                                CategoriaDB.categorias.get(categoria.getName()).kits.remove(i);
                                            }
                                        }
                                    }
                                    new KitManager(nome).delete();
                                    KitsDB.kits.remove(nome);
                                    p.sendMessage(KitMessages.SUCESS_DELETE.getMessage());
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("additem")) {//ADDITEM
                            if (strings[1] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    KitsDB.kits.get(nome).items.add(p.getItemInHand());
                                    p.sendMessage(KitMessages.SUCESS_ADD_ITEM.getMessage());
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("setinv")) {//SETINV
                            if (strings[1] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    for (int i = 0; i < p.getInventory().getContents().length; i++) {
                                        ItemStack is = p.getInventory().getItem(i);
                                        if (is != null && is.getType() != Material.AIR) {
                                            KitsDB.kits.get(nome).items.add(is);
                                            p.getInventory().remove(p.getInventory().getItem(i));
                                        }
                                    }
                                    p.updateInventory();
                                    p.sendMessage(KitMessages.SUCESS_SET_INVENTORY.getMessage());
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        }
                    } else {
                        p.sendMessage(KitMessages.SEM_PERM.getMessage());
                    }
                    break;
                case 3:
                    if (p.isOp() || p.hasPermission("gnkits.admin")) {
                        if (strings[0].equalsIgnoreCase("setitem")) {//SETITEM
                            if (strings[1] != null && strings[2] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    try {
                                        int i = Integer.parseInt(strings[2]);
                                        KitsDB.kits.get(nome).setItemSlot(i);
                                        KitsDB.kits.get(nome).setItem(p.getInventory().getItemInHand());
                                        p.getInventory().remove(p.getItemInHand());
                                        p.sendMessage(KitMessages.SUCESS_SET_ITEM.getMessage());
                                        p.updateInventory();
                                    } catch (NumberFormatException e) {
                                        p.sendMessage(KitMessages.NUMBER_FORMAT_ERROR.getMessage());
                                    }
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("setcategoria")) {//SETCATEGORIA
                            if (strings[1] != null && strings[2] != null) {
                                String categoria = strings[2];
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    if (CategoriaDB.categorias.containsKey(categoria)) {
                                        KitsDB.kits.get(nome).categoria = CategoriaDB.categorias.get(categoria);
                                        CategoriaDB.categorias.get(categoria).kits.add(KitsDB.kits.get(nome).getName());
                                        p.sendMessage(KitMessages.SUCESS_SET_CATEGORIA.getMessage());
                                    } else {
                                        p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                        return true;
                                    }
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("removelore")) {//REMOVELORE
                            if (strings[1] != null && strings[2] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    try {
                                        int n = Integer.parseInt(strings[2]);
                                        if (KitsDB.kits.get(nome).lore.size() < n) {
                                            p.sendMessage(KitMessages.ERROR_LORE_LINE_NOEXIST.getMessage());
                                            return true;
                                        }
                                        KitsDB.kits.get(nome).lore.remove(n - 1);
                                        p.sendMessage(KitMessages.SUCESS_REMOVE_LORE.getMessage());
                                    } catch (NumberFormatException e) {
                                        p.sendMessage(KitMessages.NUMBER_FORMAT_ERROR.getMessage());
                                    }
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("setcooldown")) {//SETCOOLDOWN
                            if (strings[1] != null && strings[2] != null) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    try {
                                        int n = Integer.parseInt(strings[2]);
                                        KitsDB.kits.get(nome).setCooldown(n);
                                        p.sendMessage(KitMessages.SUCESS_SET_COOLDOWN.getMessage());
                                    } catch (NumberFormatException e) {
                                        p.sendMessage(KitMessages.NUMBER_FORMAT_ERROR.getMessage());
                                    }
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("setname")) {//SETNAME
                            if (p.isOp() || p.hasPermission("gnkits.admin")) {
                                String nome = strings[1];
                                if (KitsDB.kits.containsKey(nome)) {
                                    StringBuilder me = new StringBuilder();
                                    int i = 0;
                                    while (i < strings.length) {
                                        me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                        ++i;
                                    }
                                    KitsDB.kits.get(nome).setDisplayName(me.toString().split(nome + " ")[1]);
                                    p.sendMessage(KitMessages.SUCESS_SET_DISPLAYNAME.getMessage());
                                } else {
                                    p.sendMessage(KitMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            } else {
                                p.sendMessage(KitMessages.SEM_PERM.getMessage());
                            }
                        }
                    } else {
                        p.sendMessage(KitMessages.SEM_PERM.getMessage());
                    }
                    break;
            }
        }
        return true;
    }

    private void sendHelpMSG(Player p) {
        p.sendMessage(" ");
        p.sendMessage("§cKits:");
        p.sendMessage("§a /kit create <nome>");
        p.sendMessage("§a /kit delete <nome>");
        p.sendMessage("§a /kit setname <nome> <displayname>");
        p.sendMessage("§a /kit setitem <nome> <item_slot> §e(Fique com o Item Na mao)");
        p.sendMessage("§a /kit setcategoria <nome> <categoria>");
        p.sendMessage("§a /kit addlore <nome> <lore>");
        p.sendMessage("§a /kit removelore <nome> <numero_linha>");
        p.sendMessage("§a /kit setcooldown <nome> <cooldown>");
        p.sendMessage("§a /kit additem <nome>");
        p.sendMessage("§a /kit setinv <nome>");
        p.sendMessage(" ");
    }

}
