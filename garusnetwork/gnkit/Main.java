/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit;

import garusnetwork.gnkit.cmds.CategoriaCMD;
import garusnetwork.gnkit.cmds.KitCMD;
import garusnetwork.gnkit.db.CategoriaDB;
import garusnetwork.gnkit.db.KitsDB;
import garusnetwork.gnkit.db.MySQL;
import garusnetwork.gnkit.db.ReceivedDB;
import garusnetwork.gnkit.listeners.Listeners;
import garusnetwork.gnkit.tasks.ReceiveTaks;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jhow
 */
public class Main extends JavaPlugin {

    //==== MYSQL
    public static String Senha = null;//Senha MySQL
    public static String Usuario = null;//Usuario MySQL
    public static String Banco = null;//Base de Dados MySQL
    public static String Host = null;//Ip MySQL
    public static String Port = null;//Ip MySQL
    public static MySQL mysql = null;//Return MYSQL
    //==================
    public static Main instance = null;
    public static String kitprefix = "";
    public static String categoriaprefix = "";

    @Override
    public void onEnable() {
        super.onEnable(); //To change body of generated methods, choose Tools | Templates.
        instance = this;
        saveDefaultConfig();
        Senha = getConfig().getString("MySQL.Senha");
        Usuario = getConfig().getString("MySQL.Usuario");
        Banco = getConfig().getString("MySQL.Banco");
        Host = getConfig().getString("MySQL.Ip");
        Port = getConfig().getString("MySQL.Porta");
        kitprefix = Main.instance.getConfig().getString("Mensagems.Kit.Prefix").replace("&", "§");
        categoriaprefix = Main.instance.getConfig().getString("Mensagems.Categoria.Prefix").replace("&", "§");
        new MySQL().initBanco();
        new CategoriaDB().loadFromDB();
        new KitsDB().loadFromDB();
        new ReceivedDB().loadFromDB();
        getCommand("kit").setExecutor(new KitCMD());
        getCommand("categoria").setExecutor(new CategoriaCMD());
        new ReceiveTaks().run();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
        HandlerList.unregisterAll();
        new KitsDB().loadToDB();
        new CategoriaDB().loadToDB();
        new ReceivedDB().loadToDB();
    }

}
