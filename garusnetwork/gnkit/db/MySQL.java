/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.db;

import garusnetwork.gnkit.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lenovo
 */
public class MySQL {

    //Conexoes
    private Connection connection = null;//Cria uma connection
    private Statement st = null;//Cria um Statment
    //=======================SISTEMA E CONFIGURAÇOES========================

    //<editor-fold defaultstate="collapsed" desc="pegaConexao">
    public synchronized Connection pegaConexao() {//Extende a Conexao
        return createConnection();
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createConnection">
    private Connection createConnection() {//Cria a Conexao apartir dos dados do banco
        try {
            String connStr = "jdbc:mysql://" + Main.Host + ":" + Main.Port + "/" + Main.Banco + "?autoReconnect=true";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            Connection con = DriverManager.getConnection(connStr, Main.Usuario, Main.Senha);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//</editor-fold>

    //=======================METODOS APARTIR DO MYSQL=======================
    public void initBanco() {
        System.out.println("Inicializando Banco de dados !");
        try {
            connection = pegaConexao();
            st = connection.createStatement();
            //name, displayname, item_id, item_slot, categoria, lore, items, cooldown
            st.executeUpdate("CREATE TABLE IF NOT EXISTS kits(name VARCHAR(30), displayname VARCHAR(45) DEFAULT 'Nenhum', item VARCHAR(30) DEFAULT '0:0', item_slot INT DEFAULT 0, categoria VARCHAR(30) DEFAULT 'Nenhum', lore LONGTEXT, items LONGTEXT, cooldown INT DEFAULT 3, PRIMARY KEY(name))");
            //name, displayname, item_id, item_slot, lore, kits
            st.executeUpdate("CREATE TABLE IF NOT EXISTS kits_categorias(name VARCHAR(30), displayname VARCHAR(45) DEFAULT 'Nenhum', item VARCHAR(30) DEFAULT '0:0', item_slot INT DEFAULT 0, lore LONGTEXT, kits LONGTEXT, PRIMARY KEY(name))");
            //kit_receiveds, name, kit, tempo
            st.executeUpdate("CREATE TABLE IF NOT EXISTS kits_receiveds(name VARCHAR(30), kit LONGTEXT, PRIMARY KEY(name))");
            System.out.println("Banco inicializado com sucesso ");
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar o banco de dados");
        } finally {
            fechaConexao(connection, st);
        }
    }

    /**
     * Fecha apenas a conexao
     *
     * @param conexao Retorna a conexao consultada.
     */
    public static void fechaConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Fecha a conexao e o Statement
     *
     * @param conexao Retorna a conexao consultada
     * @param stmt Retorna o statement consultado
     */
    public static void fechaConexao(Connection conexao, Statement stmt) {
        fechaConexao(conexao);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Fecha a conexao, o statement e o resultset
     *
     * @param conexao Retorna a conexao consultada
     * @param stmt Retorna o statement consultado
     * @param rs Retorna o resultset consultado
     */
    public static void fechaConexao(Connection conexao, Statement stmt, ResultSet rs) {
        fechaConexao(conexao, stmt);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

}
