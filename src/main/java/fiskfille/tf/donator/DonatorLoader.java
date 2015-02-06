package fiskfille.tf.donator;

import com.google.gson.Gson;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.util.UUID;

public class DonatorLoader extends Thread
{
	private Side side;
	
	public void run()
	{
		try 
		{
			Donator[] donators = new Gson().fromJson(IOUtils.toString(new URL("http://pastebin.com/raw.php?i=yPpJaz7p")), Donator[].class);
            for (Donator donator : donators) Donators.donators.put(UUID.fromString(donator.uuid), new Money(donator.money));
			
			Donators.doAchievements(side);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadDonators()
	{
		side = FMLCommonHandler.instance().getEffectiveSide();
		this.start();
	}
}
