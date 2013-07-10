import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.pircbotx.PircBotX;

public class Bot 
{
	public static void main(String[] args)
	{	
		Properties props;
		
		try
		{	
			if(!new File("config.properties").exists())
			{
				props = new Properties();
				props.setProperty("name", "CubeStatus");
				props.setProperty("network", "irc.esper.net");
				props.setProperty("channel", "#cubeworld");
				props.setProperty("login", "Cube");
				props.store(new FileOutputStream("config.properties"), null);
			} else
			{
				props = new Properties();
				props.load(new FileInputStream("config.properties"));
			}
			
			PircBotX bot = new PircBotX();
			
			bot.setLogin(props.getProperty("login"));
			bot.setName(props.getProperty("name"));
			bot.connect(props.getProperty("network"));
			bot.joinChannel(props.getProperty("channel"));
			bot.getListenerManager().addListener(new MessageListener());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
