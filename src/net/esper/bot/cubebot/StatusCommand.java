package net.esper.bot.cubebot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class StatusCommand extends Command {

    private boolean picromaUp = false;
    private boolean shopUp = false;
    private boolean registrationUp = false;
    private Time unixTime = null;

    public StatusCommand(String name, String response) {
        this.name = name;
        this.response = "";
        this.aliases = new LinkedList<String>();
    }

    public boolean isPicromaUp() {
        return picromaUp;
    }

    public boolean isShopUp() {
        return shopUp;
    }

    public boolean isRegistrationUp() {
        return registrationUp;
    }

    public void setPicromaUp(boolean picromaUp) {
        this.picromaUp = picromaUp;
    }

    public void setShopUp(boolean shopUp) {
        this.shopUp = shopUp;
    }

    public void setRegistrationUp(boolean registrationUp) {
        this.registrationUp = registrationUp;
    }

    @Override
    public String getResponse() {
        if (this.unixTime == null || this.timeExpired()) {
            try {
                pullData();
            } catch (Exception ex) {
            }
        }
        String statusResponse = String.format("Data provided by CyberKitsune - "
                + "Picroma is %s, shop is %s, registration is %s - "
                + "http://direct.cyberkitsune.net/canibuycubeworld/",
                (picromaUp ? "up" : "down"), 
                (shopUp ? "up" : "down"), 
                (registrationUp ? "up" : "down"));

        return statusResponse;
    }

    private boolean timeExpired() {
        if ((System.currentTimeMillis() / 1000L) > this.unixTime.getTime() + 70) {
            return true;
        }
        return false;
    }

    private void pullData() throws Exception {
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
        
        this.unixTime.setTime(Long.parseLong(dataOut.split(",")[0].split(":")[1])); // Set to cyberkitsune timestamp
    }
}
