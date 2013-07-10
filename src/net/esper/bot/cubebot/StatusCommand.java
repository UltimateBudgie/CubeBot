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

    

    public StatusCommand(String name, String response) {
        this.name = name;
        this.response = "";
        this.aliases = new LinkedList<String>();
    }

    @Override
    public String getResponse() {
        if (Status.getUnixTime() == null || Status.expired()) {
            try {
                pullData();
            } catch (Exception ex) {
            }
        }
        String statusResponse = String.format("Data provided by CyberKitsune - "
                + "Picroma is %s, shop is %s, registration is %s - "
                + "http://direct.cyberkitsune.net/canibuycubeworld/",
                (Status.isPicromaUp() ? "up" : "down"), 
                (Status.isShopUp() ? "up" : "down"), 
                (Status.isRegistrationUp() ? "up" : "down"));

        return statusResponse;
    }

    private void pullData() throws Exception {
        String address = "http://direct.cyberkitsune.net/canibuycubeworld/status.json";
        URL newURL = new URL(address);
        URLConnection newConnection = newURL.openConnection();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(newConnection.getInputStream()));
        String dataOut = reader.readLine();
        reader.close();
        
        Status.setPicromaUp(Boolean.parseBoolean(dataOut.split(",")[1].split(":")[1]));
        Status.setRegistrationUp(Boolean.parseBoolean(dataOut.split(",")[2].split(":")[1]));
        Status.setShopUp(Boolean.parseBoolean(dataOut.split(",")[3].split(":")[1]));
        
        System.err.println("Time: " + dataOut.split(",")[0].split(":")[1]);
        System.err.println("isPicromaUp: " + Status.isPicromaUp());
        System.err.println("isRegistrationUp: " + Status.isRegistrationUp());
        System.err.println("isShopUp: " + Status.isShopUp());
        
        Status.setUnixTime(Long.parseLong(dataOut.split(",")[0].split(":")[1])); // Set to cyberkitsune timestamp
    }
}
