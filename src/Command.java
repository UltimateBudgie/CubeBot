
import java.util.List;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class Command {
    /**
     * Command name - registered command word with which a standard response is given.
     */
    String name;
    
    /**
     * Standard, static response for a command word and set of aliases.
     */
    String response;
    
    /**
     * List of aliases for a command, e.g. !techdemo may also be !demo for short
     */
    List<String> aliases;

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
}
