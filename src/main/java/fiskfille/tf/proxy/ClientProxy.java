package fiskfille.tf.proxy;

import java.lang.reflect.Field;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fiskfille.tf.TransformersMod;
import fiskfille.tf.entity.EntityMiniMissile;
import fiskfille.tf.entity.EntityMissile;
import fiskfille.tf.entity.EntityTankShell;
import fiskfille.tf.item.TFItems;
import fiskfille.tf.keybinds.TFKeyBinds;
import fiskfille.tf.model.transformer.ModelCloudtrap;
import fiskfille.tf.model.transformer.ModelPurge;
import fiskfille.tf.model.transformer.ModelSkystrike;
import fiskfille.tf.model.transformer.ModelSubwoofer;
import fiskfille.tf.model.transformer.ModelVurp;
import fiskfille.tf.model.transformer.TFModelRegistry;
import fiskfille.tf.render.entity.RenderCustomPlayer;
import fiskfille.tf.render.entity.RenderMiniMissile;
import fiskfille.tf.render.entity.RenderMissile;
import fiskfille.tf.render.entity.RenderTankShell;
import fiskfille.tf.render.item.RenderItemDisplayVehicle;
import fiskfille.tf.render.item.RenderItemFlamethrower;
import fiskfille.tf.render.item.RenderItemPurgesKatana;
import fiskfille.tf.render.item.RenderItemSkystrikesCrossbow;
import fiskfille.tf.render.item.RenderItemVurpsSniper;
import fiskfille.tf.render.tileentity.RenderCrystal;
import fiskfille.tf.render.tileentity.RenderDisplayPillar;
import fiskfille.tf.tick.ClientTickHandler;
import fiskfille.tf.tileentity.TileEntityCrystal;
import fiskfille.tf.tileentity.TileEntityDisplayPillar;

public class ClientProxy extends CommonProxy
{
	public static Field camRollField;
	
	@Override
	public void registerRenderInformation()
	{
		RenderCustomPlayer renderCustomPlayer = new RenderCustomPlayer();
		renderCustomPlayer.setRenderManager(RenderManager.instance);
		RenderManager.instance.entityRenderMap.put(EntityPlayer.class, renderCustomPlayer);
		
		int i = 0;
		for (Field curField : EntityRenderer.class.getDeclaredFields())
		{
			if (curField.getType() == float.class)
			{
				if (++i == 15)
				{
					camRollField = curField;
					curField.setAccessible(true);
				}
			}
		}
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTankShell.class, new RenderTankShell());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile());
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMissile.class, new RenderMiniMissile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayPillar.class, new RenderDisplayPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
		
		MinecraftForgeClient.registerItemRenderer(TFItems.purgesKatana, new RenderItemPurgesKatana());
		MinecraftForgeClient.registerItemRenderer(TFItems.skystrikesCrossbow, new RenderItemSkystrikesCrossbow());
		
		MinecraftForgeClient.registerItemRenderer(TFItems.displayVehicle, new RenderItemDisplayVehicle());
		MinecraftForgeClient.registerItemRenderer(TFItems.flamethrower, new RenderItemFlamethrower());
		MinecraftForgeClient.registerItemRenderer(TFItems.vurpsSniper, new RenderItemVurpsSniper());
		
		TFModelRegistry.registerModel(TransformersMod.transformerCloudtrap.getName(), new ModelCloudtrap());
		TFModelRegistry.registerModel(TransformersMod.transformerPurge.getName(), new ModelPurge());
		TFModelRegistry.registerModel(TransformersMod.transformerSkystrike.getName(), new ModelSkystrike());
		TFModelRegistry.registerModel(TransformersMod.transformerSubwoofer.getName(), new ModelSubwoofer());
		TFModelRegistry.registerModel(TransformersMod.transformerVurp.getName(), new ModelVurp());
	}
	
	@Override
	public void registerTickHandlers()
	{
		tickHandler = new ClientTickHandler();
	}
	
	@Override
	public void registerKeyBinds()
	{
		TFKeyBinds.register();
	}
}