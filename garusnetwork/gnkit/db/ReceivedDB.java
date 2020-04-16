/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.db;

import garusnetwork.gnkit.Main;
import static garusnetwork.gnkit.db.CategoriaDB.categorias;
import garusnetwork.gnkit.manager.CategoriaManager;
import garusnetwork.gnkit.manager.ReceivedManager;
import garusnetwork.gnkit.objects.Received;
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
public class ReceivedDB {

    public static HashMap<String, Received> receiveds = new HashMap<>();

    public void loadToDB() {
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§eDescarregando todas as receiveds ao Bando de Dados...");
        receiveds.values().forEach((c) -> {
            ReceivedManager rm = new ReceivedManager(c.getName());
            rm.update(c);
        });
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§aTodas as receiveds foram descarregados com sucesso!");
    }

    public void loadFromDB() {
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§eCarregando todas os receiveds ao cache...");
        Connection connection;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            stmt = connection.prepareStatement("SELECT * FROM kits_receiveds");
            rs = stmt.executeQuery();
            while (rs.next()) {
                receiveds.put(rs.getString("name"), new ReceivedManager(rs.getString("name")).get());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, stmt, rs);
        }
        Bukkit.getConsoleSender().sendMessage(Main.kitprefix + "§aTodas as receiveds foram carregadas com sucesso!");
    }

}
