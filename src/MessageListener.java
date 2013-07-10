import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class MessageListener extends ListenerAdapter
{	
	public void onPrivateMessage(PrivateMessageEvent e)
	{
		if(e.getMessage().startsWith("!help"))
		{
			e.getUser().sendMessage("Available Commands:");
			e.getUser().sendMessage("!site,!website - links cubeworld");
			e.getUser().sendMessage("!status - link the status site");
			e.getUser().sendMessage("!techdemo, !demo - links the world demo");
		}
		
		if(e.getMessage().startsWith("!status"))
		{
			e.getUser().sendMessage("The Statuses for the CubeWorld systems may be found at http://direct.cyberkitsune.net/canibuycubeworld/");
		}
		
		if(e.getMessage().startsWith("!website") || e.getMessage().startsWith("!site"))
		{
			e.getUser().sendMessage("CubeWorld may be found at https://picroma.com/cubeworld");
		}
		
		if(e.getMessage().startsWith("!techdemo") || e.getMessage().startsWith("!demo"))
		{
			e.getUser().sendMessage("The Tech demo was linked to in this post https://picroma.com/blog/post/6");
		}
	}
	
	public void onPrivateMessage(NoticeEvent e)
	{
		if(e.getMessage().startsWith("!help"))
		{
			e.getUser().sendMessage("Available Commands:");
			e.getUser().sendMessage("!site,!website - links cubeworld");
			e.getUser().sendMessage("!status - link the status site");
			e.getUser().sendMessage("!techdemo, !demo - links the world demo");
		}
		
		if(e.getMessage().startsWith("!status"))
		{
			e.getUser().sendMessage("The Statuses for the CubeWorld systems may be found at http://direct.cyberkitsune.net/canibuycubeworld/");
		}
		
		if(e.getMessage().startsWith("!website") || e.getMessage().startsWith("!site"))
		{
			e.getUser().sendMessage("CubeWorld may be found at https://picroma.com/cubeworld");
		}
		
		if(e.getMessage().startsWith("!techdemo") || e.getMessage().startsWith("!demo"))
		{
			e.getUser().sendMessage("The Tech demo was linked to in this post https://picroma.com/blog/post/6");
		}
	}
	
	public void onMessage(MessageEvent e)
	{	
		if(e.getMessage().startsWith("!help"))
		{
			e.getUser().sendMessage("Available Commands:");
			e.getUser().sendMessage("!site, !website - links cubeworld");
			e.getUser().sendMessage("!status, .status - links the status site");
			e.getUser().sendMessage("!techdemo, !demo - links the world demo");
		}
		
		if(e.getMessage().startsWith("!status"))
		{
			e.getChannel().sendMessage("The Statuses for the CubeWorld systems may be found at http://direct.cyberkitsune.net/canibuycubeworld/");
		}
		
		if(e.getMessage().startsWith("!website") || e.getMessage().startsWith("!site"))
		{
			e.getChannel().sendMessage("CubeWorld may be found at https://picroma.com/cubeworld");
		}
		
		if(e.getMessage().startsWith("!techdemo") || e.getMessage().startsWith("!demo"))
		{
			e.getChannel().sendMessage("The Tech demo was linked to in this post https://picroma.com/blog/post/6");
		}
	}
}
