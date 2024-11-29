package net.normalpersonJava.blackpowderweaponsmod.client.handler;

import ca.weblite.objc.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.client.ClientUtils;
import net.normalpersonJava.blackpowderweaponsmod.client.KeyBindings;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;
import net.normalpersonJava.blackpowderweaponsmod.item.base.RevolverItem;
import net.normalpersonJava.blackpowderweaponsmod.network.Network;
import net.normalpersonJava.blackpowderweaponsmod.network.packet.ReloadPacket;

import java.util.Optional;


@Mod.EventBusSubscriber(modid = BlackpowderWeaponsMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {
    public ClientEventHandler(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(this::onRenderPlayer);
        MinecraftForge.EVENT_BUS.addListener(this::onRenderHand);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    //keybinds
    @SubscribeEvent
    public void onClientTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = Minecraft.getInstance().player;
            while (KeyBindings.INSTANCE.reload.consumeClick() && player != null) {
                reloadHandler();
            }
        }
    }

    public void reloadHandler() {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        ItemStack heldItem = player.getMainHandItem();

        //revolver reload
        if (heldItem.getItem() instanceof RevolverItem revolverItem) {
            if (!revolverItem.fullyLoaded(heldItem) && revolverItem.hasAmmo(player) && !revolverItem.isReloading(heldItem)) {
                Network.sendToServer(new ReloadPacket());
                revolverItem.setCondition(heldItem, true);
                player.displayClientMessage(Component.literal("reloading"), true);
            } else if (!revolverItem.hasAmmo(player)) {
                player.displayClientMessage(Component.literal("No Ammo!"), true);
            } else if (revolverItem.fullyLoaded(heldItem)) {
                player.displayClientMessage(Component.literal("Already Fully Loaded!"), true);
            } else if (revolverItem.isReloading(heldItem)) {
                player.displayClientMessage(Component.literal("Already Reloading!"), true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(final RenderLivingEvent.Pre<Player, PlayerModel<Player>> event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        } else {
            event.getRenderer().getModel();
        }
        Player player = (Player) event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        Optional<HumanoidModel.ArmPose> mainHandPose = ClientUtils.getArmPose(player, InteractionHand.MAIN_HAND);
        Optional<HumanoidModel.ArmPose> offhandPose = ClientUtils.getArmPose(player, InteractionHand.OFF_HAND);

        PlayerModel<Player> model = event.getRenderer().getModel();

        if (player.getMainArm() == HumanoidArm.RIGHT) {
            if (mainHandItem.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:longgun")))) {
                model.rightArmPose = mainHandPose.orElseGet(() -> model.rightArmPose);
                model.leftArmPose = HumanoidModel.ArmPose.EMPTY;
            } else {
                model.rightArmPose = mainHandPose.orElseGet(() -> model.rightArmPose);
                model.leftArmPose = offhandPose.orElseGet(() -> model.leftArmPose);
            }
        }
        else {
            if (mainHandItem.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:longgun")))) {
                model.rightArmPose = HumanoidModel.ArmPose.EMPTY;
                model.leftArmPose = mainHandPose.orElseGet(() -> model.leftArmPose);
            } else {
                model.rightArmPose = offhandPose.orElseGet(() -> model.rightArmPose);
                model.leftArmPose = mainHandPose.orElseGet(() -> model.leftArmPose);
            }
        }



    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemStack mainHandItem = minecraft.player.getMainHandItem();
        ItemStack offHandItem = minecraft.player.getOffhandItem();

        if (mainHandItem.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:longgun"))))
        {
            if (event.getHand() == InteractionHand.OFF_HAND)
            {
                // Prevent the off-hand item from being rendered
                event.setCanceled(true);
            }
        }
    }
}
