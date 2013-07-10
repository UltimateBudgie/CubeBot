package net.esper.bot.cubebot;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class Command {
    /**
     * Command name - registered command word with which a standard response is given.
     */
    private String name;
    
    /**
     * Standard, static response for a command word and set of aliases.
     */
    private String response;
    
    /**
     * List of aliases for a command, e.g. !techdemo may also be !demo for short
     */
    private List<String> aliases;

    public Command(String name, String response) {
        this.name = name;
        this.response = response;
        this.aliases = new LinkedList<String>();
    }

    public String getResponse() {
        return response;
    }

    /**
     * This function is used to see if a particular command word matches this
     * command's name or any of its aliases. Design does not compensate for
     * multiple commands with the same alias, it is up to the end user to
     * check for data validation and redundancy.
     * @param command Command word to check for match
     * @return True if command matches command name or any alias, else false
     */
    public boolean matches(String command) {
        if (command.matches(name)) {
            return true;
        }
        for (String alias : aliases) {
            if (command.matches(alias)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Provide a way to add aliases to this command. Use matches to see if the
     * command already has the name or has an alias.
     * @param alias Alias to add to this command.
     */
    public void addAlias(String alias) {
        if (this.matches(alias))
            return;
        
        aliases.add(alias);
    }
    
    
}
