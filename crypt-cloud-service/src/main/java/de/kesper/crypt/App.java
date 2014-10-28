package de.kesper.crypt;

import de.kesper.crypt.engine.CryptEngine;
import de.kesper.crypt.observer.FileObserver;
import de.kesper.crypt.observer.FileSystemEventHandler;
import de.kesper.crypt.observer.FileSystemEventType;
import de.kesper.crypt.observer.FileSystemModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Utilities;
import java.nio.file.*;
import java.util.logging.Logger;

/**
 * Created by Stephan on 25.10.2014.
 */
public class App {

    private static CryptEngine engine;

    private static Logger log = Logger.getLogger("SYNC");

    public static void main(String[] args) throws Exception {
        System.out.println("Crypt secure cloud sync. Version 1.0alpha");

        String password = getPassword();

        engine = new CryptEngine(password);

        FileSystemModel fsc = new FileSystemModel(Paths.get("test/cloud"));
        FileSystemModel fsl = new FileSystemModel(Paths.get("test/local"));

        fsl.getDirectoryModel().values().stream().forEach(b -> System.out.println("fsl directory: "+b));

        FileObserver fo = new FileObserver(fsc, fsl, new FileSystemEventHandler() {
            @Override
            public void handleFile(FileSystemModel model, Path file, FileSystemEventType type) {
                log.info(type.name()+" : fil = "+file.toString());
            }

            @Override
            public void handleDirectory(FileSystemModel model, Path directory, FileSystemEventType type) {
                log.info(type.name()+" : dir = "+directory.toString());
            }
        });


        fo.startObservation();

        Thread.sleep(10000000L);
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
