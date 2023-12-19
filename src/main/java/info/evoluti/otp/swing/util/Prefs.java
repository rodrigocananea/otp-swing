/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.evoluti.otp.swing.util;
import java.util.prefs.Preferences;

/**
 *
 * @author Rodrigo Cananea <rodrigoaguiar35@gmail.com>
 */
public class Prefs {

    public static String TEMA = "tema";
    public static String USER = "user";
    public static String EMAIL = "email";
    public static String SECRET_KEY = "secretkey";
    
    private static Preferences state;
    
    public static Preferences getState() {
        return state;   
    }

    public static void init(String rootPath) {
        state = Preferences.userRoot().node(rootPath);
    }
}