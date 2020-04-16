/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.manager;

import garusnetwork.gnkit.db.MySQL;
import garusnetwork.gnkit.objects.Categoria;
import garusnetwork.gnkit.objects.Kit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jhow
 */
public class CategoriaManager {

    private Categoria categoria;
    private String name;

    public CategoriaManager(Categoria kit) {
        this.categoria = kit;
    }

    public CategoriaManager(String name) {
        this.name = name;
    }

    public void set() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("INSERT INTO kits_categorias VALUES((?),(?),(?),(?),(?),(?))");
            if (categoria == null) {
                st.setString(1, name);
            } else {
                st.setString(1, categoria.getName());
            }
            st.setString(2, "Nenhum");
            st.setString(3, "0:0");
            st.setInt(4, 0);
            st.setString(5, "Nenhum");
            st.setString(6, "Nenhum");
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public void update(Categoria categoria) {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, lore, kits
            st = connection.prepareStatement("UPDATE kits_categorias SET "
                    + "displayname=(?),"
                    + " item=(?),"
                    + " item_slot=(?),"
                    + " lore=(?),"
                    + " kits=(?) WHERE name = (?)");
            if (categoria.getDisplayName() != null) {//DISPLAYNAME
                st.setString(1, categoria.getDisplayName());
            } else {
                st.setString(1, "Nenhum");
            }
            if (categoria.getItem() != null) {//ITEM
                st.setString(2, categoria.getItem().getTypeId() + ":" + categoria.getItem().getData().getData());
            } else {
                st.setString(2, "0:0");
            }
            if (categoria.getItemSlot() != 0) {//ITEM_SLOT
                st.setInt(3, categoria.getItemSlot());
            } else {
                st.setInt(3, 0);
            }
            if (!categoria.getLore().isEmpty()) {//LORE
                String z = "";
                for (String x : categoria.getLore()) {
                    z = z + "@" + x;
                }
                st.setString(4, z);
            } else {
                st.setString(4, "Nenhum");
            }
            if (!categoria.getKits().isEmpty()) {//LORE
                String z = "";
                for (String kit : categoria.getKits()) {
                    z = z + "," + kit;
                }
                st.setString(5, z);
            } else {
                st.setString(5, "Nenhum");
            }
            st.setString(6, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public Categoria get() {
        Connection connection;
        PreparedStatement st = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("SELECT * FROM kits_categorias WHERE name = (?)");
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                categoria = new Categoria();
                categoria.setName(rs.getString("name"));
                if (!rs.getString("displayname").equals("Nenhum")) {
                    categoria.setDisplayName(rs.getString("displayname"));
                }
                if (!rs.getString("lore").equals("Nenhum")) {
                    String[] z = rs.getString("lore").split("@");
                    List<String> lore = new ArrayList<>();
                    for (String s : z) {
                        if (s != null && !s.equals("")) {
                            lore.add(s);
                        }
                    }
                    categoria.lore = lore;
                }
                categoria.setItem(new ItemStack(Material.getMaterial(Integer.parseInt(rs.getString("item").split(":")[0])), 1, (byte) Integer.parseInt(rs.getString("item").split(":")[1])));
                categoria.setItemSlot(rs.getInt("item_slot"));
                if (!rs.getString("kits").equals("Nenhum")) {
                    String[] z = rs.getString("kits").split(",");
                    for (String x : z) {
                        if (x != null && !x.equals("")) {
                            categoria.kits.add(x);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st, rs);
        }
        return categoria;
    }

    public void delete() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("DELETE FROM kits_categorias WHERE name = (?)");
            if (categoria == null) {
                st.setString(1, name);
            } else {
                st.setString(1, categoria.getName());
            }
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

}
