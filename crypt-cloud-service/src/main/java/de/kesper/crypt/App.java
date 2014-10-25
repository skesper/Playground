package de.kesper.crypt;

import de.kesper.crypt.engine.CryptEngine;
import de.kesper.crypt.observer.FileObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Utilities;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Stephan on 25.10.2014.
 */
public class App {

    private static CryptEngine engine;

    public static void main(String[] args) throws Exception {
        System.out.println("Crypt secure cloud sync. Version 1.0alpha");

        String password = getPassword();

        engine = new CryptEngine(password);

        FileObserver fo = new FileObserver(Paths.get("./test/cloud"), Paths.get("./test/local"));

        fo.run();
    }


    private static String getPassword() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your password:");
        JPasswordField pass = new JPasswordField(60);
        pass.setBorder(new BasicBorders.MarginBorder());
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Password",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            return new String(password);
        }
        return null;
    }
}
