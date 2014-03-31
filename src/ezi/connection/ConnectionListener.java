/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

/**
 *
 * @author Elwin
 */
public class ConnectionListener implements Runnable {

    private Connection connection;

    protected ConnectionListener(Connection con) {
        this.connection = con;
    }

    @Override
    public void run() {
    }
}
