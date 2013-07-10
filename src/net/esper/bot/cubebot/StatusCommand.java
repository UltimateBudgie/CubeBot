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
                Status.pullData();
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
}
