package fiskfille.tf.helper;

import fiskfille.tf.common.item.TFItems;
import fiskfille.tf.common.network.MessageLaserShoot;
import fiskfille.tf.common.network.MessageVehicleShoot;
import fiskfille.tf.common.network.base.TFNetworkManager;
import fiskfille.tf.common.playerdata.TFDataManager;
import fiskfille.tf.common.transformer.TransformerVurp;
import fiskfille.tf.common.transformer.base.Transformer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class TFShootManager {
    public static int shootCooldown = 0;
    public static int shotsLeft = 4;

    public static boolean reloading;

    public static int laserCharge;
    public static boolean laserFilling;

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (event.entity.worldObj.isRemote) {
                if (player == Minecraft.getMinecraft().thePlayer) {
                    Transformer transformer = TFHelper.getTransformer(player);

                    if (laserFilling) {
                        int max = 50;

                        if (laserCharge < max) {
                            laserCharge += 1;
                        } else if (laserCharge >= max) {
                            laserFilling = false;
                            laserCharge = max;
                        }
                    }

                    if (transformer != null) {
                        if (shootCooldown > 0) {
                            shootCooldown--;
                        }

                        Item ammo = transformer.getShootItem();

                        if (ammo != null) {
                            int ammoCount = getShotsLeft(player, transformer, ammo);

                            if (TFDataManager.isInVehicleMode(player)) {
                                if (reloading && shootCooldown <= 0) {
                                    shotsLeft = ammoCount;
                                    reloading = false;
                                }
                            } else {
                                int shots = ammoCount;

                                if (shotsLeft > shots) {
                                    shotsLeft = shots;
                                }
                            }
                        }
                    }
                }

                Transformer transformer = TFHelper.getTransformer(player);

                if (Mouse.isButtonDown(1)) {
                    if (transformer != null && TFDataManager.isInVehicleMode(player)) {
                        if (transformer.canShoot(player) && transformer.hasRapidFire() && player.ticksExisted % 2 == 0) {
                            stealthForceShoot(transformer, player);
                        }
                    }
                }
            }
        }
    }

    private int getShotsLeft(EntityPlayer player, Transformer transformer, Item shootItem) {
        int maxAmmo = transformer.getShots();
        int ammoCount;

        if (player.capabilities.isCreativeMode) {
            ammoCount = maxAmmo;
        } else {
            ammoCount = getAmountOf(shootItem, player);
        }

        if (ammoCount > maxAmmo) {
            ammoCount = maxAmmo;
        }

        if (shotsLeft > ammoCount) {
            shotsLeft = ammoCount;
        }

        return ammoCount;
    }

    private int getAmountOf(Item item, EntityPlayer player) {
        int amount = 0;
        InventoryPlayer inventory = player.inventory;

        for (ItemStack stack : inventory.mainInventory) {
            if (stack != null) {
                if (stack.getItem() == item) {
                    amount += stack.stackSize;
                }
            }
        }

        return amount;
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        Transformer transformer = TFHelper.getTransformer(player);
        Action action = event.action;

        if (action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (transformer != null && TFDataManager.isInVehicleMode(player)) {
                if (transformer.canShoot(player) && !transformer.hasRapidFire()) {
                    stealthForceShoot(transformer, player);
                    event.setCanceled(true);
                }
            }
        }
    }

    private void stealthForceShoot(Transformer transformer, EntityPlayer player) {
        if (player == Minecraft.getMinecraft().thePlayer) {
            if (transformer instanceof TransformerVurp) {
                if (transformer.canShoot(player)) {
                    if (!laserFilling && laserCharge > 0) {
                        laserCharge -= 5;
                        player.playSound("random.fizz", 1, 2F);
                        TFNetworkManager.networkWrapper.sendToServer(new MessageLaserShoot(player, false));
                    } else {
                        if (!laserFilling && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode)) {
                            TFNetworkManager.networkWrapper.sendToServer(new MessageLaserShoot(player, true));
                            laserFilling = true;
                        }
                    }
                }
            } else {
                if (shotsLeft > 0) {
                    if (shootCooldown <= 0) {
                        if (transformer.canShoot(player)) {
                            Item shootItem = transformer.getShootItem();

                            boolean isCreative = player.capabilities.isCreativeMode;
                            boolean hasAmmo = isCreative || player.inventory.hasItem(shootItem);

                            if (hasAmmo) {
                                TFNetworkManager.networkWrapper.sendToServer(new MessageVehicleShoot(player));

                                if (!isCreative) {
                                    player.inventory.consumeInventoryItem(shootItem);
                                }
                            }
                        }

                        if (shotsLeft > transformer.getShots()) {
                            shotsLeft = transformer.getShots();
                        }

                        shotsLeft--;

                        if (shotsLeft <= 0) {
                            shootCooldown = 20;
                            reloading = true;
                        }
                    }
                } else {
                    if (!reloading) {
                        shootCooldown = 20;
                        reloading = true;
                    }
                }
            }
        }
    }
}
