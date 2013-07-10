
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.pircbotx.PircBotX;

public class Bot {

    static String[] channelList = {/*"cubeworld", */"cubeworld-status", "beserk"};
    static Integer channelSize = channelList.length;

    public static void main(String[] args) {
        Properties props;

        try {
            if (!new File("config.properties").exists()) {
                props = new Properties();
                props.setProperty("name", "CubeStatus");
                props.setProperty("network", "irc.esper.net");
                props.setProperty("channels.size", channelSize.toString());
                for (int i = 0; i < channelSize; i++) {
                    props.setProperty("channels.channel." + ((Integer)(i + 1)).toString(), "#" + channelList[i]);
                }
                props.setProperty("login", "Cube");
                props.store(new FileOutputStream("config.properties"), null);
            } else {
                props = new Properties();
                props.load(new FileInputStream("config.properties"));
            }

            PircBotX bot = new PircBotX();

            bot.setLogin(props.getProperty("login"));
            bot.setName(props.getProperty("name"));
            bot.connect(props.getProperty("network"));
            for (int i = 0; i < channelSize; i++) {
                bot.joinChannel(props.getProperty(""));
            }
            bot.getListenerManager().addListener(new MessageListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
