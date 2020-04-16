/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.db;

import garusnetwork.gnkit.Main;
import garusnetwork.gnkit.manager.KitManager;
import garusnetwork.gnkit.objects.Kit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

/**
 *
 * @author Jhow
 */
public class KitsDB {

    public static HashMap<String, Kit> kits;

    public void update(Kit kit) {
        if (kits.containsKey(kit.getName())) {
            kits.remove(kit.getName());
            kits.put(kit.getName(), kit);
        } else {
            kits.put(kit.getName(), kit);
        }
    }

    public void loadToDB() {
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§eDescarregando todos os kits ao Bando de Dados...");
        kits.values().forEach((c) -> {
            KitManager cm = new KitManager(c.getName());
            cm.update(c);
        });
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§aTodos os kits foram descarregados com sucesso!");
    }

    public void loadFromDB() {
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§eCarregando todos os kits ao cache...");
        kits = new HashMap<>();
        Connection connection;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            stmt = connection.prepareStatement("SELECT name FROM kits");
            rs = stmt.executeQuery();
            while (rs.next()) {
                kits.put(rs.getString("name"), new KitManager(rs.getString("name")).get());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, stmt, rs);
        }
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§aTodos os kits foram carregados com sucesso!");
    }

}
