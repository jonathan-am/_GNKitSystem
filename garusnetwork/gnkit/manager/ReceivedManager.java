/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.manager;

import garusnetwork.gnkit.db.KitsDB;
import garusnetwork.gnkit.db.MySQL;
import garusnetwork.gnkit.objects.Cooldown;
import garusnetwork.gnkit.objects.Kit;
import garusnetwork.gnkit.objects.Received;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jhow
 */
public class ReceivedManager {

    private Received received;
    private String name;

    public ReceivedManager(Received received) {
        this.received = received;
    }

    public ReceivedManager(String name) {
        this.name = name;
    }

    public void set() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //kit_receiveds, name, kit, tempo
            st = connection.prepareStatement("INSERT INTO kits_receiveds VALUES((?), (?) )");
            st.setString(1, received.getName());
            String s = "Nenhum";
            if (received.getUsedKits().size() > 0) {
                s = "";
                for (Cooldown x : received.getUsedKits().values()) {
                    s = s + x.getKit().getName() + "@" + x.getCooldown() + ",";
                }
            }
            st.setString(2, s);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReceivedManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public void update(Received kit) {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //kit_receiveds, name, kit, tempo
            st = connection.prepareStatement("UPDATE kits_receiveds SET "
                    + " kit=(?) WHERE name = (?)");

            String s = "";
            for (Cooldown k : kit.getUsedKits().values()) {
                s = s + k.getKit().getName() + "@" + k.getCooldown() + ",";
                System.out.println(s);
            }
            st.setString(1, s);
            st.setString(2, kit.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReceivedManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

    public Received get() {
        Connection connection;
        PreparedStatement st = null;
        ResultSet rs = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("SELECT * FROM kits_receiveds WHERE name = (?)");
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                received = new Received(name);
                if (!rs.getString("kit").equals("Nenhum")) {
                    String[] s = rs.getString("kit").split(",");
                    for (String x : s) {
                        if (x != null && !x.equals("")) {
                            received.addKit(new Cooldown(KitsDB.kits.get(x.split("@")[0]), Integer.parseInt(x.split("@")[1])));
                            System.out.println(x);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceivedManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st, rs);
        }
        return received;
    }

    public void delete() {
        Connection connection;
        PreparedStatement st = null;
        connection = new MySQL().pegaConexao();
        try {
            //name, displayname, item_id, item_slot, categoria, lore, items
            st = connection.prepareStatement("DELETE FROM kits_receiveds WHERE name = (?)");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReceivedManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            MySQL.fechaConexao(connection, st);
        }
    }

}
