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
import garusnetwork.gnkit.manager.CategoriaManager;
import garusnetwork.gnkit.objects.Categoria;
import garusnetwork.gnkit.objects.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Jhow
 */
public class CategoriaCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (p.hasPermission("gnkits.admin") || p.isOp()) {
                switch (strings.length) {
                    case 0:
                        sendHelpMSG(p);
                        break;
                    default:
                        if (strings[0].equalsIgnoreCase("addlore")) {//ADDLORE
                            if (p.isOp() || p.hasPermission("gnkits.admin")) {
                                String nome = strings[1];
                                if (CategoriaDB.categorias.containsKey(nome)) {
                                    StringBuilder me = new StringBuilder();
                                    int i = 0;
                                    while (i < strings.length) {
                                        me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                        ++i;
                                    }
                                    CategoriaDB.categorias.get(nome).lore.add(me.toString().split(nome + " ")[1]);
                                    p.sendMessage(CategoriaMessages.SUCESS_ADD_LORE.getMessage());
                                } else {
                                    p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            } else {
                                p.sendMessage(CategoriaMessages.SEM_PERM.getMessage());
                            }
                        } else if (strings[0].equalsIgnoreCase("setname")) {//SETNAME 
                            String nome = strings[1];
                            if (CategoriaDB.categorias.containsKey(nome)) {
                                StringBuilder me = new StringBuilder();
                                int i = 0;
                                while (i < strings.length) {
                                    me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                    ++i;
                                }
                                CategoriaDB.categorias.get(nome).setDisplayName(me.toString().split(nome + " ")[1]);
                                p.sendMessage(CategoriaMessages.SUCESS_SET_DISPLAYNAME.getMessage());
                            } else {
                                p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                            }
                        } else {
                            sendHelpMSG(p);
                        }
                        break;
                    case 2:
                        if (p.isOp() || p.hasPermission("gnkits.admin")) {
                            if (strings[0].equalsIgnoreCase("create")) {//CREATE
                                if (strings[1] != null) {
                                    String nome = strings[1];
                                    if (!CategoriaDB.categorias.containsKey(nome)) {
                                        Categoria categoria = new Categoria();
                                        categoria.setName(nome);
                                        new CategoriaManager(categoria).set();
                                        new CategoriaDB().update(categoria);
                                        p.sendMessage(CategoriaMessages.SUCESS_CREATE.getMessage());
                                    } else {
                                        p.sendMessage(CategoriaMessages.ALREADY_CONTAINS_ERROR.getMessage());
                                    }
                                }
                            } else if (strings[0].equalsIgnoreCase("delete")) {//DELETE
                                if (strings[1] != null) {
                                    String nome = strings[1];
                                    if (CategoriaDB.categorias.containsKey(nome)) {
                                        if (CategoriaDB.categorias.get(nome).getKits() != null) {
                                            KitsDB.kits.values().stream().filter((k) -> (CategoriaDB.categorias.get(nome).getKits().contains(k.getName()))).forEachOrdered((k) -> {
                                                k.setCategoria(null);
                                            });
                                        }
                                        new CategoriaManager(nome).delete();
                                        CategoriaDB.categorias.remove(nome);
                                        p.sendMessage(CategoriaMessages.SUCESS_DELETE.getMessage());
                                    } else {
                                        p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                    }
                                }
                            }
                        } else {
                            p.sendMessage(CategoriaMessages.SEM_PERM.getMessage());
                        }
                        break;
                    case 3:
                        if (p.isOp() || p.hasPermission("gnkits.admin")) {
                            if (strings[0].equalsIgnoreCase("setitem")) {//SETITEM
                                if (strings[1] != null && strings[2] != null) {
                                    String nome = strings[1];
                                    if (CategoriaDB.categorias.containsKey(nome)) {
                                        try {
                                            int i = Integer.parseInt(strings[2]);
                                            CategoriaDB.categorias.get(nome).setItemSlot(i);
                                            CategoriaDB.categorias.get(nome).setItem(p.getInventory().getItemInHand());
                                            p.getInventory().remove(p.getItemInHand());
                                            p.sendMessage(CategoriaMessages.SUCESS_SET_ITEM.getMessage());
                                            p.updateInventory();
                                        } catch (NumberFormatException e) {
                                            p.sendMessage(CategoriaMessages.NUMBER_FORMAT_ERROR.getMessage());
                                        }
                                    } else {
                                        p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                    }
                                }
                            } else if (strings[0].equalsIgnoreCase("removelore")) {//REMOVELORE
                                if (strings[1] != null && strings[2] != null) {
                                    String nome = strings[1];
                                    if (CategoriaDB.categorias.containsKey(nome)) {
                                        try {
                                            int n = Integer.parseInt(strings[2]);
                                            if (CategoriaDB.categorias.get(nome).lore.size() < n) {
                                                p.sendMessage(CategoriaMessages.ERROR_LORE_LINE_NOEXIST.getMessage());
                                                return true;
                                            }
                                            CategoriaDB.categorias.get(nome).lore.remove(n - 1);
                                        } catch (NumberFormatException e) {
                                            p.sendMessage(CategoriaMessages.NUMBER_FORMAT_ERROR.getMessage());
                                        }
                                    } else {
                                        p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                    }
                                }
                            } else if (strings[0].equalsIgnoreCase("addkit")) {//ADDITEM
                                if (strings[1] != null && strings[2] != null) {
                                    String nome = strings[1];
                                    String nome_do_kit = strings[2];
                                    if (CategoriaDB.categorias.containsKey(nome)) {
                                        if (KitsDB.kits.containsKey(nome_do_kit)) {
                                            if (CategoriaDB.categorias.get(nome).kits.contains(nome_do_kit)) {
                                                p.sendMessage(KitMessages.ALREADY_CONTAINS_ERROR.getMessage());
                                                return true;
                                            }
                                            Kit kit = KitsDB.kits.get(nome_do_kit);
                                            //#
                                            Categoria categoria = CategoriaDB.categorias.get(nome);
                                            categoria.kits.add(kit.getName());
                                            new CategoriaDB().update(categoria);
                                        } else {
                                            p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                        }
                                    } else {
                                        p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                    }
                                }
                            } else if (strings[0].equalsIgnoreCase("setname")) {//SETNAME 
                                String nome = strings[1];
                                if (CategoriaDB.categorias.containsKey(nome)) {
                                    StringBuilder me = new StringBuilder();
                                    int i = 0;
                                    while (i < strings.length) {
                                        me.append(String.valueOf(strings[i].replace("&", "§"))).append(" ");
                                        ++i;
                                    }
                                    CategoriaDB.categorias.get(nome).setDisplayName(me.toString().split(nome + " ")[1]);
                                    p.sendMessage(CategoriaMessages.SUCESS_SET_DISPLAYNAME.getMessage());
                                } else {
                                    p.sendMessage(CategoriaMessages.NO_CONTAINS_ERROR.getMessage());
                                }
                            }
                        } else {
                            p.sendMessage(CategoriaMessages.SEM_PERM.getMessage());
                        }
                        break;
                }
            }
        }
        return true;
    }

    private void sendHelpMSG(Player p) {
        p.sendMessage(" ");
        p.sendMessage("§cCategorias KIT:");
        p.sendMessage("§a /categoria create <nome>");
        p.sendMessage("§a /categoria delete <nome>");
        p.sendMessage("§a /categoria setname <nome> <displayname>");
        p.sendMessage("§a /categoria setitem <nome> <item_slot> §e(Fique com o Item Na mao)");
        p.sendMessage("§a /categoria addlore <nome> <lore>");
        p.sendMessage("§a /categoria removelore <nome> <numero_linha>");
        p.sendMessage("§a /categoria addkit <nome> <nome_kit>");
        p.sendMessage(" ");
    }

}
