/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.db;

import garusnetwork.gnkit.Main;
import garusnetwork.gnkit.manager.CategoriaManager;
import garusnetwork.gnkit.objects.Categoria;
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
public class CategoriaDB {

    public static HashMap<String, Categoria> categorias;

    public void update(Categoria categoria) {
        if (categorias.containsKey(categoria.getName())) {
            categorias.remove(categoria.getName());
            categorias.put(categoria.getName(), categoria);
        } else {
            categorias.put(categoria.getName(), categoria);
        }
    }

    public void loadToDB() {
        Bukkit.getConsoleSender().sendMessage(Main.categoriaprefix + "§eDescarregando todas as categorias ao Bando de Dados...");
        categorias.values().forEach((c) -> {
            CategoriaManager cm = new CategoriaManager(c.getName());
            cm.update(c);
        });
        Bukkit.getConsoleSender().sendMessage(Main.categoriaprefix + "§aTodas as categorias foram descarregados com sucesso!");
    }

    public void loadFromDB() {
        Bukkit.getConsoleSender().sendMessage(Main.categoriaprefix + "§eCarregando todas os categorias ao cache...");
        categorias = new HashMap<>();
        Connection connection;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            stmt = connection.prepareStatement("SELECT * FROM kits_categorias");
            rs = stmt.executeQuery();
            while (rs.next()) {
                categorias.put(rs.getString("name"), new CategoriaManager(rs.getString("name")).get());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, stmt, rs);
        }
        Bukkit.getConsoleSender().sendMessage(Main.categoriaprefix + "§aTodas as categorias foram carregadas com sucesso!");
    }

}
