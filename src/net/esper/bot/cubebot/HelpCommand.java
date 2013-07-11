package net.esper.bot.cubebot;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class HelpCommand extends Command {

    public HelpCommand(String name, String response) {
        super(name, response);
    }
    
    @Override
    public String getResponse() {
        String commandList = "";
        for (Command cmd : net.esper.bot.cubebot.Bot.commandList) {
            commandList += " !" + cmd.getName();
            for (String alias : cmd.getAliases()) {
                commandList += " !" + alias;
            }
        }
        return super.getResponse() + commandList;
    }
}
