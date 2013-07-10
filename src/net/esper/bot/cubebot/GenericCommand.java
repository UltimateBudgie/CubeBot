package net.esper.bot.cubebot;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class GenericCommand extends Command {

    public GenericCommand(String name, String response) {
        this.name = name;
        this.response = response;
        this.aliases = new LinkedList<String>();
    }
}
