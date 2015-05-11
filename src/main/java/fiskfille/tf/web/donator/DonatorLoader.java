package fiskfille.tf.web.donator;

import com.google.gson.Gson;
import fiskfille.tf.web.WebHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class DonatorLoader extends Thread {
    private Side side;

    public void run() {
        try {
            Donator[] donators = new Gson().fromJson(WebHelper.readPastebin("yPpJaz7p"), Donator[].class);

            for (Donator donator : donators) {
                Donators.donators.put(UUID.fromString(donator.uuid), new Money(donator.money));
            }

            Donators.doAchievements(side);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDonators() {
        side = FMLCommonHandler.instance().getEffectiveSide();
        this.start();
    }
}
