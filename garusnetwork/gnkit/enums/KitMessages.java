/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.enums;

import garusnetwork.gnkit.Main;

/**
 *
 * @author Jhow
 */
public enum KitMessages {

    SEM_PERM(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SEM_PERM")),
    ALREADY_CONTAINS_ERROR(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.ALREADY_CONTAINS_ERROR")),
    SUCESS_CREATE(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_CREATE")),
    SUCESS_DELETE(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_DELETE")),
    SUCESS_SET_ITEM(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_SET_ITEM")),
    SUCESS_SET_DISPLAYNAME(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_SET_DISPLAYNAME")),
    SUCESS_SET_CATEGORIA(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_SET_CATEGORIA")),
    SUCESS_ADD_ITEM(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_ADD_ITEM")),
    SUCESS_SET_INVENTORY(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_SET_INVENTORY")),
    SUCESS_RECEIVE_KIT(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_RECEIVE_KIT")),
    SUCESS_SET_COOLDOWN(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_SET_COOLDOWN")),
    SUCESS_REMOVE_LORE(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_REMOVE_LORE")),
    SUCESS_ADD_LORE(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.SUCESS_ADD_LORE")),
    NUMBER_FORMAT_ERROR(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.NUMBER_FORMAT_ERROR")),
    NO_CONTAINS_ERROR(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.NO_CONTAINS_ERROR")),
    ERROR_ALREADY_RECEIVED(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.ERROR_ALREADY_RECEIVED")),
    ERROR_LORE_LINE_NOEXIST(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.ERROR_LORE_LINE_NOEXIST")),
    ERROR_INVENTORY_FULL(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.ERROR_INVENTORY_FULL")),
    COMMAND_ERROR(Main.kitprefix + Main.instance.getConfig().getString("Mensagems.Kit.COMMAND_ERROR"));

    /*
    ##
    ##
     */
    private final String s;

    private KitMessages(String z) {
        this.s = z;
    }

    public String getMessage() {
        return this.s.replace("&", "§");
    }
}
