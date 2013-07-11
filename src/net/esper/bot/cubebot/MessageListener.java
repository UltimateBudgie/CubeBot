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
        String targetUser = null;
        String args = "";
        String commandString = e.getMessage().substring(1).split(" ")[0];

        String messagePrefix = "[" + ((quiet) ? "PRIVMSG" : ((MessageEvent) e).getChannel().getName()) + "]";
        String messageOutput = e.getUser().getNick() + ": " + e.getMessage();
        System.err.println(messagePrefix + " " + messageOutput);

        if (e.getMessage().startsWith("!")) {
            if (e.getMessage().split(" ").length > 1) {
                targetUser = e.getMessage().substring(1).split(" ")[1];

                if (e.getMessage().split(" ").length > 2) {
                    args += e.getMessage().split(" ")[2];

                    for (int i = 3; i < e.getMessage().split(" ").length; i++) {
                        args += " " + e.getMessage().split(" ")[i];
                    }
                }
            }

            for (Command chatCommand : net.esper.bot.cubebot.Bot.commandList) {
                if (chatCommand.matches(commandString)) {
                    if (chatCommand.getName().matches("wiki")) {
                        args = args.replace(" ", "%20");
                    }
                    sendMessage(e, chatCommand.getResponse(), args, quiet, targetUser);
                }
            }
        }
    }

    private void sendMessage(GenericMessageEvent e, String message, String args, boolean quiet, String targetUser) {
        if (quiet) {
            e.respond(message);
//            e.getUser().sendMessage(message);
        } else {
            ((MessageEvent) e).getChannel().sendMessage(((targetUser != null) ? (targetUser + ": ") : "") + String.format(message, args));
        }
    }
}
