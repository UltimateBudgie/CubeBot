package net.esper.bot.cubebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.pircbotx.PircBotX;

public class Bot {

    private static String[] channelList = {/*"cubeworld", */"cubeworld-status", "beserk"};
    private static Integer channelSize = channelList.length;
    public static List<Command> commandList;

    public static void main(String[] args) {
        Properties config;
        Properties commands;
        commandList = new LinkedList<Command>();

        try {
            if (!new File("config.properties").exists()) {
                config = new Properties();
                initConfig(config);
                config.store(new FileOutputStream("config.properties"), null);
            } else {
                config = new Properties();
                config.load(new FileInputStream("config.properties"));
            }

            if (!new File("command.properties").exists()) {
                commands = new Properties();
                initCommands(commands);
                commands.store(new FileOutputStream("commands.properties"), null);
            } else {
                commands = new Properties();
                commands.load(new FileInputStream("commands.properties"));
            }

            int commandIndex = 0;
            Command currentCommand;
            while (commands.getProperty("commands." + commandIndex + ".name") != null) {
                currentCommand = new Command(
                        commands.getProperty("commands." + commandIndex + ".name"),
                        commands.getProperty("commands." + commandIndex + ".text"));
                
                int aliasIndex = 0;
                while (commands.getProperty("commands." + commandIndex + ".alias." + aliasIndex) != null) {
                    currentCommand.addAlias(commands.getProperty("commands." + commandIndex + ".alias." + aliasIndex));
                }

                commandIndex++;
                
                commandList.add(currentCommand);
            }

            PircBotX bot = new PircBotX();

            bot.setLogin(config.getProperty("login"));
            bot.setName(config.getProperty("name"));
            bot.connect(config.getProperty("network"));
            for (int i = 0; i < channelSize; i++) {
                bot.joinChannel(config.getProperty("channels." + ((Integer) i).toString()));
            }
            bot.getListenerManager().addListener(new MessageListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initConfig(Properties p) {
        p.setProperty("name", "CubeStatus");
        p.setProperty("network", "irc.esper.net");
        p.setProperty("channels.size", channelSize.toString());
        for (int i = 0; i < channelSize; i++) {
            p.setProperty("channels." + ((Integer) i).toString(), "#" + channelList[i]);
        }
        p.setProperty("login", "Cube");
    }

    private static void initCommands(Properties p) {
        p.setProperty("commands.0.name", "help");
        p.setProperty("commands.0.text", "Available commands: %s");
        p.setProperty("commands.1.name", "status");
        p.setProperty("commands.1.text", "Data provided by CyberKitsune - Picroma is %1, shop is %2, registration is %3 - http://direct.cyberkitsune.net/canibuycubeworld/");
        p.setProperty("commands.2.name", "website");
        p.setProperty("commands.2.text", "Cube World can be found at https://www.picroma.com/cubeworld");
        p.setProperty("commands.2.alias.0", "site");
        p.setProperty("commands.3.name", "techdemo");
        p.setProperty("commands.3.text", "A link to the Cube World Mini Demo was posted on July 2 at https://www.picroma.com/blog/post/6 - this is only a starting screen with a rotating landscape to see if Cube World will run on your computer.");
        p.setProperty("commands.3.alias.0", "minidemo");
        p.setProperty("commands.3.alias.1", "demo");
    }
}
