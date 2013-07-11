package net.esper.bot.cubebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.pircbotx.PircBotX;

public class Bot {

    private static String[] channelList = {"cubeworld", "cubeworld-status", "beserk"};
    private static String[] statusChannelList = {"cubeworld-status"};
    private static Integer channelSize = channelList.length;
    public static List<Command> commandList;

    public static void main(String[] args) {
        Properties config;
        Properties commands;
        commandList = new LinkedList<Command>();
        
        try {
            if (!new File("config.properties").exists()) {
                System.err.println("Generating config file for CubeBot...");
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
                System.err.println("Creating commands file for CubeBot...");
                commands = new Properties();
                initCommands(commands);
                commands.store(new FileOutputStream("commands.properties"), null);
                System.err.println("Commands file created successfully.");
            } else {
                System.err.println("Loading stored commands for CubeBot...");
                commands = new Properties();
                commands.load(new FileInputStream("commands.properties"));
                System.err.println("Commands loaded.");
            }

            System.err.println("Dynamically loading commands into memory...");
            loadCommands(commands);
            System.err.println("Commands stored to memory.");

            PircBotX bot = new PircBotX();

            System.err.println("CubeBot started.");

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
        p.setProperty("name", "CubeStatus");
        p.setProperty("network", "irc.esper.net");
        p.setProperty("channels.size", channelSize.toString());
        for (int i = 0; i < channelSize; i++) {
            p.setProperty("channels." + i, "#" + channelList[i]);
        }
        p.setProperty("status.updates", "true");
        for (int i = 0; i < statusChannelList.length; i++) {
            p.setProperty("status.channel." + i, "#" + statusChannelList[i]);
        }
        p.setProperty("login", "Cube");
    }

    private static void initCommands(Properties p) {
        p.setProperty("commands.0.name", "help");
        p.setProperty("commands.0.text", "Available commands:");
        p.setProperty("commands.0.type", "help");
        p.setProperty("commands.1.name", "status");
        p.setProperty("commands.1.type", "status");
        p.setProperty("commands.2.name", "website");
        p.setProperty("commands.2.text", "Cube World can be found at https://www.picroma.com/cubeworld");
        p.setProperty("commands.2.alias.0", "site");
        p.setProperty("commands.3.name", "techdemo");
        p.setProperty("commands.3.text", "A link to the Cube World Mini Demo was posted on July 2 at https://www.picroma.com/blog/post/6 - this is only a starting screen with a rotating landscape to see if Cube World will run on your computer.");
        p.setProperty("commands.3.alias.0", "minidemo");
        p.setProperty("commands.3.alias.1", "demo");
        p.setProperty("commands.4.name", "wiki");
        p.setProperty("commands.4.text", "http://wiki.cubeworldforum.org/index.php?search=%s");
        p.setProperty("commands.4.alias.0", "search");
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
