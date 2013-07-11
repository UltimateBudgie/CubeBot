package net.esper.bot.cubebot;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class StatusCommand extends Command {

    public StatusCommand(String name, String response) {
        super(name, response);
    }
    
    @Override
    public String getResponse() {
        if (Status.getUnixTime() == null || Status.expired()) {
            try {
                Status.pullData();
            } catch (Exception ex) {
            }
        }
        String statusResponse = String.format("Picroma is %s, shop is %s, registration is %s"
                + " - Data provided by CyberKitsune"
                + " - http://direct.cyberkitsune.net/canibuycubeworld/",
                (Status.isPicromaUp() ? "up" : "down"), 
                (Status.isShopUp() ? "up" : "down"), 
                (Status.isRegistrationUp() ? "up" : "down"));

        return statusResponse;
    }
}
