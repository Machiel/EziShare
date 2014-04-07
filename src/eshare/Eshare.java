/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eshare;

import ezi.connection.EziClient;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Elwin
 */
public class Eshare {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //JOptionPane.showMessageDialog(frame, "Choose a folder to share.");
        JFileChooser shareFolder = new JFileChooser();
        shareFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Integer opt = shareFolder.showDialog(shareFolder, "choose");
        System.out.println(shareFolder.getSelectedFile().getPath());
        String shareDirectory = shareFolder.getSelectedFile().getPath();
        
        //JOptionPane.showMessageDialog(frame, "Choose your directory to download into.");
        JFileChooser downloadFolder = new JFileChooser();
        downloadFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Integer opt2 = downloadFolder.showDialog(downloadFolder, "choose");
        System.out.println(downloadFolder.getSelectedFile().getPath());
        String downloadDirectory = downloadFolder.getSelectedFile().getPath();
        
        frame.dispose();
        
        EziClient client = new EziClient(new File(shareDirectory), new File(downloadDirectory));
        client.start();
    }
}
