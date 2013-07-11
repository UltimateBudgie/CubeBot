package net.esper.bot.cubebot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class Status {

    private static boolean picromaUp = false;
    private static boolean shopUp = false;
    private static boolean registrationUp = false;
    private static Time unixTime = null;
    private static boolean statusUpdates = false;

    Timer statusTimer = new Timer();

    public Status() throws Exception {
        pullData();
        statusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Status.pullData();
                    Status.sendIRCNotifications();
                } catch (Exception ex) {
                }
            }
        }, new Date(getUnixTime().getTime()*1000L), 60 * 1000L);
    }

    public static boolean isPicromaUp() {
        return picromaUp;
    }

    public static boolean isShopUp() {
        return shopUp;
    }

    public static boolean isRegistrationUp() {
        return registrationUp;
    }

    public static boolean getStatusUpdates() {
        return statusUpdates;
    }

    public static Time getUnixTime() {
        return unixTime;
    }

    public static void setPicromaUp(boolean picromaUp) {
        Status.picromaUp = picromaUp;
    }

    public static void setShopUp(boolean shopUp) {
        Status.shopUp = shopUp;
    }

    public static void setStatusUpdates(boolean statusUpdates) {
        Status.statusUpdates = statusUpdates;
    }

    public static void setRegistrationUp(boolean registrationUp) {
        Status.registrationUp = registrationUp;
    }

    public static boolean expired() {
        if ((System.currentTimeMillis() / 1000L) > getUnixTime().getTime() + 70) {
            return true;
        }
        return false;
    }

    protected static void setUnixTime(Long timeStamp) {
        Status.unixTime.setTime(timeStamp);
    }
    
    private static void sendIRCNotifications() {
        String statusMessage = String.format("Data provided by CyberKitsune - "
                + "Picroma is %s, shop is %s, registration is %s - "
                + "http://direct.cyberkitsune.net/canibuycubeworld/",
                (Status.isPicromaUp() ? "up" : "down"), 
                (Status.isShopUp() ? "up" : "down"), 
                (Status.isRegistrationUp() ? "up" : "down"));
        
        
    }
    
    protected static void pullData() throws Exception {
        String address = "http://direct.cyberkitsune.net/canibuycubeworld/status.json";
        URL newURL = new URL(address);
        URLConnection newConnection = newURL.openConnection();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(newConnection.getInputStream()));
        String dataOut = reader.readLine();
        reader.close();
        
        setPicromaUp(Boolean.parseBoolean(dataOut.split(",")[1].split(":")[1]));
        setRegistrationUp(Boolean.parseBoolean(dataOut.split(",")[2].split(":")[1]));
        setShopUp(Boolean.parseBoolean(dataOut.split(",")[3].split(":")[1]));
        
        System.err.println("Time: " + dataOut.split(",")[0].split(":")[1]);
        System.err.println("isPicromaUp: " + isPicromaUp());
        System.err.println("isRegistrationUp: " + isRegistrationUp());
        System.err.println("isShopUp: " + isShopUp());
        
        setUnixTime(Long.parseLong(dataOut.split(",")[0].split(":")[1])); // Set to cyberkitsune timestamp
    }
}