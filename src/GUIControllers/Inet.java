/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Inet {

    static String AdresseIp;
    static String HosName;

    public static String getName(String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            String host = addr.getHostName();
            if (!host.equals(ip)) {
                return host;
            } else {
                return null;
            }

        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static String getHosName() {
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            HosName = addr.getHostName();
            return HosName;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setHosName(String HosName) {
        Inet.HosName = HosName;
    }

    public static String getAdresseIp() {
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            AdresseIp = addr.getHostAddress();
            return AdresseIp;
        } catch (UnknownHostException ex) {
//            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setAdresseIp(String AdresseIp) {
        Inet.AdresseIp = AdresseIp;
    }

    public static boolean isPing(String Ip,int timeOut) {

        try {
            InetAddress inet = InetAddress.getByName(Ip);

            return inet.isReachable(timeOut);
        } catch (UnknownHostException ex) {
//            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
