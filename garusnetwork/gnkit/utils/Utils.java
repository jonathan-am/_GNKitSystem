/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jhow
 */
public class Utils {
    
    public static String itemToString(ItemStack item) {
        String s;
        // ID:DATA@QUANTIDADE#DISPLAYNAME@LORE@LORE@LORE@LORE|ENCHANT@VALUE;ENCHANT@VALUE
        s = item.getTypeId() + ":" + item.getData().getData() + "@" + item.getAmount();
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasDisplayName()) {
                s = s + "#" + item.getItemMeta().getDisplayName();
            } else {
                s = s + "#Nenhum";
            }
            if (item.getItemMeta().hasLore()) {
                for (String lore : item.getItemMeta().getLore()) {
                    s = s + "@" + lore;
                }
            } else {
                s = s + "@Nenhum";
            }
            if (item.getItemMeta().hasEnchants()) {
                s = s + "/";
                for (String x : getEnchantsToString(item)) {
                    s = s + x + ";";
                }
            } else {
                s = s + "/Nenhum";
            }
        } else {
            s = s + "#Nenhum@Nenhum/Nenhum";
        }
        return s;
    }
    
    public static ItemStack stringToItem(String s) {
        // ID:DATA@QUANTIDADE#DISPLAYNAME:LORE@LORE@LORE@LORE|ENCHANT@VALUE;ENCHANT@VALUE
        int id = Integer.parseInt(s.split("#")[0].split(":")[0]);
        byte data = Byte.parseByte(s.split("#")[0].split("@")[0].split(":")[1]);
        int quantidade = Integer.parseInt(s.split("#")[0].split("@")[1]);
        ItemStack item = new ItemStack(Material.getMaterial(id), quantidade, data);
        ItemMeta im = item.getItemMeta();
        String displayname = "Nenhum";
        if (!s.split("#")[1].split("@")[0].equals("Nenhum")) {//DISPLAYNAME
            displayname = s.split("#")[1].split("@")[0];
            im.setDisplayName(displayname);
        }
        if (!s.split("#")[1].split("/")[0].equals("Nenhum")) {//LORE
            String[] z = s.split("#")[1].split("/")[0].split("@");
            List<String> lore = new ArrayList<>();
            for (String x : z) {
                if (!x.equals(displayname) && !x.equals("Nenhum")) {
                    lore.add(x);
                }
            }
            im.setLore(lore);
        }
        item.setItemMeta(im);
        if (!s.split("#")[1].split("/")[1].equals("Nenhum")) {
            String c = s.split("/")[1];
            System.out.println(c);
            String[] z = c.split(";");
            System.out.println(Arrays.toString(z));
            List<String> enchants = new ArrayList<>();
            for (String x : z) {
                if (x != null && !x.equals("")) {
                    enchants.add(x);
                }
            }
            item.addUnsafeEnchantments(getStringToEnchants(enchants));
        }
        return item;
    }
    
    public static Map<Enchantment, Integer> getStringToEnchants(List<String> list) {
        Map<Enchantment, Integer> enc = new HashMap<>();
        try {
            if (!list.isEmpty()) {
                for (String l : list) {
                    if (!l.equals("")) {
                        String ls[] = l.split("@");
                        int i = Integer.parseInt(ls[1]);
                        switch (ls[0]) {
                            case "AD":
                                enc.put(Enchantment.ARROW_DAMAGE, i);
                                break;
                            case "AF":
                                enc.put(Enchantment.ARROW_FIRE, i);
                                break;
                            case "AI":
                                enc.put(Enchantment.ARROW_INFINITE, i);
                                break;
                            case "AK":
                                enc.put(Enchantment.ARROW_KNOCKBACK, i);
                                break;
                            case "AA":
                                enc.put(Enchantment.DAMAGE_ALL, i);
                                break;
                            case "DH":
                                enc.put(Enchantment.DAMAGE_ARTHROPODS, i);
                                break;
                            case "DU":
                                enc.put(Enchantment.DAMAGE_UNDEAD, i);
                                break;
                            case "AS":
                                enc.put(Enchantment.DIG_SPEED, i);
                                break;
                            case "DI":
                                enc.put(Enchantment.DURABILITY, i);
                                break;
                            case "FA":
                                enc.put(Enchantment.FIRE_ASPECT, i);
                                break;
                            case "KN":
                                enc.put(Enchantment.KNOCKBACK, i);
                                break;
                            case "LB":
                                enc.put(Enchantment.LOOT_BONUS_BLOCKS, i);
                                break;
                            case "LM":
                                enc.put(Enchantment.LOOT_BONUS_MOBS, i);
                                break;
                            case "LU":
                                enc.put(Enchantment.LUCK, i);
                                break;
                            case "LR":
                                enc.put(Enchantment.LURE, i);
                                break;
                            case "OX":
                                enc.put(Enchantment.OXYGEN, i);
                                break;
                            case "PE":
                                enc.put(Enchantment.PROTECTION_ENVIRONMENTAL, i);
                                break;
                            case "PX":
                                enc.put(Enchantment.PROTECTION_EXPLOSIONS, i);
                                break;
                            case "PF":
                                enc.put(Enchantment.PROTECTION_FALL, i);
                                break;
                            case "PI":
                                enc.put(Enchantment.PROTECTION_FIRE, i);
                                break;
                            case "PJ":
                                enc.put(Enchantment.PROTECTION_PROJECTILE, i);
                                break;
                            case "ST":
                                enc.put(Enchantment.SILK_TOUCH, i);
                                break;
                            case "TH":
                                enc.put(Enchantment.THORNS, i);
                                break;
                            case "WW":
                                enc.put(Enchantment.WATER_WORKER, i);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.print(e);
        }
        return enc;
    }
    
    public static List<String> getEnchantsToString(ItemStack i) {
        List<String> lista = new ArrayList<>();
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        if (!enchants.isEmpty()) {
            enchants.entrySet().forEach((enc) -> {
                if (enc.getKey().equals(Enchantment.ARROW_DAMAGE)) {
                    lista.add("AD@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.ARROW_FIRE)) {
                    lista.add("AF@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.ARROW_INFINITE)) {
                    lista.add("AI@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.ARROW_KNOCKBACK)) {
                    lista.add("AK@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.DAMAGE_ALL)) {
                    lista.add("AA@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.DAMAGE_ARTHROPODS)) {
                    lista.add("DH@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.DAMAGE_UNDEAD)) {
                    lista.add("DU@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.DIG_SPEED)) {
                    lista.add("AS@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.DURABILITY)) {
                    lista.add("DI@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.FIRE_ASPECT)) {
                    lista.add("FA@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.KNOCKBACK)) {
                    lista.add("KN@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.LOOT_BONUS_BLOCKS)) {
                    lista.add("LB@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.LOOT_BONUS_MOBS)) {
                    lista.add("LM@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.LUCK)) {
                    lista.add("LU@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.LURE)) {
                    lista.add("LR@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.OXYGEN)) {
                    lista.add("OX@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    lista.add("PE@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.PROTECTION_EXPLOSIONS)) {
                    lista.add("PX@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.PROTECTION_FALL)) {
                    lista.add("PF@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.PROTECTION_FIRE)) {
                    lista.add("PI@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.PROTECTION_PROJECTILE)) {
                    lista.add("PJ@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.SILK_TOUCH)) {
                    lista.add("ST@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.THORNS)) {
                    lista.add("TH@" + enc.getValue());
                } else if (enc.getKey().equals(Enchantment.WATER_WORKER)) {
                    lista.add("WW@" + enc.getValue());
                }
            });
            return lista;
        }
        return null;
    }
}
