/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.manager;

import garusnetwork.gnkit.db.MySQL;
import garusnetwork.gnkit.objects.Kit;
import garusnetwork.gnkit.utils.Utils;
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
public class KitManager {

    private Kit kit;
    private String name;

    public KitManager(Kit kit) {
        this.kit = kit;
    }

    public KitManager(String name) {
        this.name = name;
    }

    public void set() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("INSERT INTO kits VALUES((?), (?) , (?), (?), (?) , (?), (?), (?))");
            if (kit == null) {
                st.setString(1, name);
            } else {
                st.setString(1, kit.getName());
            }
            st.setString(2, "Nenhum");
            st.setString(3, "0:0");
            st.setInt(4, 0);
            st.setString(5, "Nenhum");
            st.setString(6, "Nenhum");
            st.setString(7, "Nenhum");
            st.setInt(8, 3);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public void update(Kit kit) {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("UPDATE kits SET "
                    + "displayname=(?),"
                    + " item=(?),"
                    + " item_slot=(?),"
                    + " categoria=(?),"
                    + " lore=(?),"
                    + " items=(?),"
                    + " cooldown=(?) WHERE name = (?)");
            if (kit.getDisplayName() != null) {//DISPLAYNAME
                st.setString(1, kit.getDisplayName());
            } else {
                st.setString(1, "Nenhum");
            }
            if (kit.getItem() != null) {//ITEM
                st.setString(2, kit.getItem().getTypeId() + ":" + kit.getItem().getData().getData());
            } else {
                st.setString(2, "0:0");
            }
            if (kit.getItemSlot() != 0) {//ITEM_SLOT
                st.setInt(3, kit.getItemSlot());
            } else {
                st.setInt(3, 0);
            }
            if (kit.getCategoria() != null) {//CATEGORIA
                st.setString(4, kit.getCategoria().getName());
            } else {
                st.setString(4, "Nenhum");
            }
            if (!kit.getLore().isEmpty()) {//LORE
                String z = "";
                for (String x : kit.getLore()) {
                    z = z + "@" + x;
                }
                st.setString(5, z);
            } else {
                st.setString(5, "Nenhum");
            }
            if (!kit.getItems().isEmpty()) {//LORE
                String z = "";
                for (ItemStack x : kit.getItems()) {
                    z = z + "," + Utils.itemToString(x);
                }
                st.setString(6, z);
            } else {
                st.setString(6, "Nenhum");
            }
            if (kit.getCooldown() != 0) {//ITEM_SLOT
                st.setInt(7, kit.getCooldown());
            } else {
                st.setInt(7, 3);
            }
            st.setString(8, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public Kit get() {
        Connection connection;
        PreparedStatement st = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("SELECT * FROM kits WHERE name = (?)");
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                kit = new Kit();
                kit.setName(rs.getString("name"));
                if (rs.getString("categoria") != null && !rs.getString("categoria").equals("Nenhum")) {
                    kit.setCategoria(new CategoriaManager(rs.getString("categoria")).get());
                }
                if (!rs.getString("displayname").equals("Nenhum")) {
                    kit.setDisplayName(rs.getString("displayname"));
                }
                if (!rs.getString("lore").equals("Nenhum")) {
                    String[] z = rs.getString("lore").split("@");
                    List<String> lore = new ArrayList<>();
                    for (String s : z) {
                        if (s != null && !s.equals("")) {
                            lore.add(s);
                        }
                    }
                    kit.lore = lore;
                }
                kit.setItem(new ItemStack(Material.getMaterial(Integer.parseInt(rs.getString("item").split(":")[0])), 1, (byte) Integer.parseInt(rs.getString("item").split(":")[1])));
                kit.setItemSlot(rs.getInt("item_slot"));
                if (!rs.getString("items").equals("Nenhum")) {
                    String[] z = rs.getString("items").split(",");
                    for (String x : z) {
                        if (x != null && !x.equals("")) {
                            kit.items.add(Utils.stringToItem(x));
                        }
                    }
                }
                kit.setCooldown(rs.getInt("cooldown"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st, rs);
        }
        return kit;
    }

    public void delete() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("DELETE FROM kits WHERE name = (?)");
            if (kit == null) {
                st.setString(1, name);
            } else {
                st.setString(1, kit.getName());
            }
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

}
