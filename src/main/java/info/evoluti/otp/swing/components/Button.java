/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.evoluti.otp.swing.components;

import java.awt.Cursor;
import javax.swing.JButton;

/**
 *
 * @author Rodrigo Cananea <rodrigoaguiar35@gmail.com>
 */
public class Button extends JButton {
    
    public Button() {
        super();
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public Button(String text) {
        super(text);
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
