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
public enum CategoriaMessages {
    
    SEM_PERM(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SEM_PERM")),
    ALREADY_CONTAINS_ERROR(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.ALREADY_CONTAINS_ERROR")),
    SUCESS_CREATE(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SUCESS_CREATE")),
    SUCESS_DELETE(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SUCESS_DELETE")),
    SUCESS_SET_ITEM(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SUCESS_SET_ITEM")),
    SUCESS_SET_DISPLAYNAME(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SUCESS_SET_DISPLAYNAME")),
    SUCESS_ADD_LORE(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.SUCESS_ADD_LORE")),
    NUMBER_FORMAT_ERROR(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.NUMBER_FORMAT_ERROR")),
    NO_CONTAINS_ERROR(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.NO_CONTAINS_ERROR")),
    ERROR_LORE_LINE_NOEXIST(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.ERROR_LORE_LINE_NOEXIST")),
    COMMAND_ERROR(Main.categoriaprefix + Main.instance.getConfig().getString("Mensagems.Categoria.COMMAND_ERROR"));

    /*
    ##
    ##
     */
    private final String s;
    
    private CategoriaMessages(String z) {
        this.s = z;
    }
    
    public String getMessage() {
        return this.s.replace("&", "§");
    }
}
