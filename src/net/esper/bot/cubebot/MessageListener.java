package net.esper.bot.cubebot;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent e) {
        checkMessages(e, true);
    }

    public void onPrivateMessage(NoticeEvent e) {
        checkMessages(e, true);
    }

    @Override
    public void onMessage(MessageEvent e) {
        checkMessages(e, false);
    }

    private void checkMessages(org.pircbotx.hooks.types.GenericMessageEvent e, boolean quiet) {
        boolean target = false;
        String args[];
        String commandString = e.getMessage().substring(1).split(" ")[0];

        String messagePrefix = "[" + ((quiet) ? "PRIVMSG" : ((MessageEvent) e).getChannel().getName()) + "]";
        String messageOutput = e.getUser().getNick() + ": " + e.getMessage();
        System.err.println(messagePrefix + " " + messageOutput);

        if (e.getMessage().startsWith(Bot.COMMANDTRIGGER)) {
            args = e.getMessage().split(" ");
            if (args.length > 1) {
                target = true;
            }

            for (Command chatCommand : net.esper.bot.cubebot.Bot.commandList) {
                if (chatCommand.matches(commandString)) {
                    chatCommand.respond(e, args, quiet, target);
                }
            }
        }
    }

    private void sendMessage(GenericMessageEvent e, String message, String[] args, boolean quiet, boolean target) {
        if (quiet) {
            e.respond(message);
//            e.getUser().sendMessage(message);
        } else {
//            ((MessageEvent) e).getChannel().sendMessage(((target != null) ? (target + ": ") : "") + String.format(message, String.args));
        }
    }
}
