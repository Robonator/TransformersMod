package fiskfille.tf.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class TFGuiFactory implements IModGuiFactory
{
    public void initialize(Minecraft minecraftInstance)
    {

    }

    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return GuiTFModConfig.class;
    }

    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        return null;
    }
}
