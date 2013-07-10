package net.esper.bot.cubebot;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class MessageListener extends ListenerAdapter {

    public void onPrivateMessage(PrivateMessageEvent e) {
        checkMessages(e, true);
    }

    public void onPrivateMessage(NoticeEvent e) {
        checkMessages(e, true);
    }

    public void onMessage(MessageEvent e) {
        checkMessages(e, false);
    }

    private void checkMessages(org.pircbotx.hooks.types.GenericMessageEvent e, boolean quiet) {
        String commandString = e.getMessage().substring(1).split(" ")[0];
        
        if (e.getMessage().startsWith("!")) {
            for (Command chatCommand : net.esper.bot.cubebot.Bot.commandList) {
                if (chatCommand.matches(commandString)) {
                    sendMessage(e, chatCommand.getResponse(), quiet);
                }
            }
        }

        /*if (e.getMessage().startsWith("!help")) {
         e.getUser().sendMessage("Available Commands:");
         e.getUser().sendMessage("!site,!website - links cubeworld");
         e.getUser().sendMessage("!status - link the status site");
         e.getUser().sendMessage("!techdemo, !demo - links the world demo");
         }

         if (e.getMessage().startsWith("!status")) {
         e.getUser().sendMessage("The Statuses for the CubeWorld systems may be found at http://direct.cyberkitsune.net/canibuycubeworld/");
         }

         if (e.getMessage().startsWith("!website") || e.getMessage().startsWith("!site")) {
         e.getUser().sendMessage("CubeWorld may be found at https://picroma.com/cubeworld");
         }

         if (e.getMessage().startsWith("!techdemo") || e.getMessage().startsWith("!demo")) {
         e.getUser().sendMessage("The Tech demo was linked to in this post https://picroma.com/blog/post/6");
         }*/
    }
    
    private void sendMessage(GenericMessageEvent e, String message, boolean quiet) {
        if (quiet)
            e.getUser().sendMessage(message);
        else
            ((MessageEvent)e).getChannel().sendMessage(message);
    }
}
