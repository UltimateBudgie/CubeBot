package net.esper.bot.cubebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.pircbotx.PircBotX;

public class Bot {

    private static String[] channelList = {"sharpcube", "sharpcube-dev"};
    private static String[] statusChannelList = {};
    private static Integer channelSize = channelList.length;
    public static List<Command> commandList;

    public static void main(String[] args) {
        Properties config;
        Properties commands;
        commandList = new LinkedList<Command>();
        
        try {
            if (!new File("config.properties").exists()) {
                System.err.println("Generating config file for SharpBot...");
                config = new Properties();
                initConfig(config);
                config.store(new FileOutputStream("config.properties"), null);
                System.err.println("Config file generated successfully.");
            } else {
                System.err.println("Loading config file...");
                config = new Properties();
                config.load(new FileInputStream("config.properties"));
                System.err.println("Config loaded.");
            }
            
            Status.setStatusUpdates(Boolean.parseBoolean(config.getProperty("status.updates")));

            if (!new File("command.properties").exists()) {
                System.err.println("Creating commands file for " + config.getProperty("name") + "...");
                commands = new Properties();
                initCommands(commands);
                commands.store(new FileOutputStream("commands.properties"), null);
                System.err.println("Commands file created successfully.");
            } else {
                System.err.println("Loading stored commands for " + config.getProperty("name"));
                commands = new Properties();
                commands.load(new FileInputStream("commands.properties"));
                System.err.println("Commands loaded.");
            }

            System.err.println("Dynamically loading commands into memory...");
            loadCommands(commands);
            System.err.println("Commands stored to memory.");

            PircBotX bot = new PircBotX();

            System.err.println(config.getProperty("name") + " started.");

            bot.setLogin(config.getProperty("login"));
            bot.setName(config.getProperty("name"));
            bot.connect(config.getProperty("network"));
            for (int i = 0; i < channelSize; i++) {
                System.err.println("Joining " + config.getProperty("channels." + ((Integer) i).toString()));
                bot.joinChannel(config.getProperty("channels." + ((Integer) i).toString()));
            }
            bot.getListenerManager().addListener(new MessageListener());
        } catch (Exception e) {
        }
    }

    private static void initConfig(Properties p) {
        p.setProperty("login", "Sharp");
        p.setProperty("name", "SharpBot");
        p.setProperty("network", "irc.esper.net");
        p.setProperty("channels.size", channelSize.toString());
        for (int i = 0; i < channelSize; i++) {
            p.setProperty("channels." + i, "#" + channelList[i]);
        }
        p.setProperty("status.updates", "true");
        for (int i = 0; i < statusChannelList.length; i++) {
            p.setProperty("status.channel." + i, "#" + statusChannelList[i]);
        }
    }

    private static void initCommands(Properties p) {
        p.setProperty("commands.0.name", "help");
        p.setProperty("commands.0.text", "Available commands:");
        p.setProperty("commands.0.type", "help");
        p.setProperty("commands.1.name", "website");
        p.setProperty("commands.1.text", "SharpCube can be found at http://sharpcube.org/forums/");
        p.setProperty("commands.1.alias.0", "site");
        p.setProperty("commands.2.name", "devbuilds");
        p.setProperty("commands.2.text", "Builds are due to be available.");
        p.setProperty("commands.2.alias.0", "builds");
        p.setProperty("commands.3.name", "wiki");
        p.setProperty("commands.3.text", "http://sharpcube.org/forums/index.php?app=core&module=search&search_in=forums");
        p.setProperty("commands.3.alias.0", "search");
        p.setProperty("commands.4.name", "dev");
        p.setProperty("commands.4.text", "The SharpCube development channel is #sharpcube-dev");
    }

    private static void loadCommands(Properties p) {
        int commandIndex = 0;
        Command currentCommand = null;
        while (p.getProperty("commands." + commandIndex + ".name") != null) {
            String type = p.getProperty("commands." + commandIndex + ".type");
            String currentCommandString = "commands." + commandIndex;
            if (type != null) {
                if (type.matches("status")) {
                    currentCommand = new StatusCommand(
                            p.getProperty(currentCommandString + ".name"),
                            p.getProperty(currentCommandString + ".text"));
                }
                if (type.matches("help")) {
                    currentCommand = new HelpCommand(
                            p.getProperty(currentCommandString + ".name"),
                            p.getProperty(currentCommandString + ".text"));
                }
            } else {
                currentCommand = new GenericCommand(
                        p.getProperty(currentCommandString + ".name"),
                        p.getProperty(currentCommandString + ".text"));
            }

            int aliasIndex = 0;
            while (p.getProperty("commands." + commandIndex + ".alias." + aliasIndex) != null) {
                currentCommand.addAlias(p.getProperty("commands." + commandIndex + ".alias." + aliasIndex));
                aliasIndex++;
            }

            commandList.add(currentCommand);

            commandIndex++;
        }
    }
}
