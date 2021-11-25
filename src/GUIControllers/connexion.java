package GUIControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Willson vulembere
 */
public class connexion {

    public static Connection con;
    public static PreparedStatement pst;
    public static ResultSet rs;

    public static Connection Con() {
        if (con == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:ipscan.db");
                System.out.println("Succed ");
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Erreur " + ex);
                con = null;
            }
        }
        return con;
    }

    public static String getID() {
        String date = LocalDateTime.now().toString().replace(":", "").replace(" ", "").replace("T", "").trim().replace("-", "").replace(".", "");
        return date;
    }

    public static String getBarcode() {
        int annee = LocalDate.now().getYear();
        int jour = LocalDate.now().getDayOfMonth();
        int mois = LocalDate.now().getMonthValue();
        int nano = LocalDateTime.now().getNano();
        String date = String.valueOf(annee).substring(2, 4) + "" + jour + "" + mois + "" + nano + "0000";
        return date.substring(0, 8);
    }

    public static boolean execute(String rqt) {
        try {
            pst = Con().prepareStatement(rqt);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    public static int getCountBy(String rqt) {
//        System.out.println(rqt);
        int x = 0;
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                x++;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static String setUrl(String name, String id) {
        String ext = ".png";
        return getValue("select valeur from url where name='" + name + "'") + id + ext;
    }

    public static int getValueInt(String rqt) {
// System.out.println(rqt);
        int x = 0;
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static String getValue(String rqt) {
// System.out.println(rqt);
        String x = null;
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static String getValue(String rqt, String... param) {
// System.out.println(rqt);
        String x = null;
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    i++;
                }
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static boolean execute(String rqt, String... param) {
//        System.out.println(rqt);
        String x = null;
        String rqt_ = "";
        try {
            pst = Con().prepareStatement(rqt);
            int i = 1;
            if (param != null) {
                for (String string : param) {
                    pst.setString(i, string);
                    rqt_ = rqt_ + "," + string;
                    i++;
                }
            }
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
//        System.out.println(rqt_);
        return false;
    }

    public static void ImageExecute(byte[] image, String table, String colone, String refrence) {
        pst = null;
        try {
            pst = Con().prepareStatement("update " + table + " set " + colone + "=? where code=?");
            pst.setBytes(1, image);
            pst.setString(2, refrence);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean exist(String table, String colone, String element) {
        boolean x = false;
        try {
            pst = Con().prepareStatement("select * from " + table + " where " + colone + "='" + element + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static String getLastId(String table) {
        String x = null;
        try {
            pst = Con().prepareStatement("select coalesce(max(code),0)+1 from " + table);
            rs = pst.executeQuery();
            if (rs.next()) {
                x = rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return x;
    }

    public static boolean isValidated(String codeop) {
        boolean Value = false;
        try {
            pst = Con().prepareStatement("select code from validation where codeOperation='" + codeop + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                Value = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return Value;
    }

    public static boolean isValidated(String codeop, String codeuser) {
        boolean Value = false;
        try {
            pst = Con().prepareStatement("select * from validation where codeOperation='" + codeop + "' and codeUser='" + codeuser + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                Value = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return Value;
    }

    public static ArrayList<String> getArray(String rqt) {
        ArrayList<String> liste = new ArrayList();
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!liste.contains(rs.getString(1))) {
                    liste.add(rs.getString(1));
                }

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return liste;
    }

    public static void loadCombo(ComboBox<String> combo, String rqt) {
        try {
            combo.getItems().clear();
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!combo.getItems().contains(rs.getString(1))) {
                    combo.getItems().add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static ResultSet getRs(String rqt) {
//        System.out.println(rqt);
        try {
            pst = Con().prepareStatement(rqt);
            rs = pst.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
 
}
