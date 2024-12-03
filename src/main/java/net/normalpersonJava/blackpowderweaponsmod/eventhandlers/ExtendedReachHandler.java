package net.normalpersonJava.blackpowderweaponsmod.eventhandlers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BlackpowderWeaponsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExtendedReachHandler {

    private static final UUID REACH_MODIFIER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"); // Unique identifier

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        ItemStack heldItem = player.getMainHandItem();

        // Add or remove the reach modifier dynamically
        if (heldItem.getItem() instanceof GunItem gun) {
            addReachModifier(player, gun.meleeReach());
        } else {
            removeReachModifier(player);
        }
    }

    private static void addReachModifier(Player player, double reach) {
        var attributeInstance = player.getAttribute(ForgeMod.ENTITY_REACH.get());

        if (attributeInstance != null && attributeInstance.getModifier(REACH_MODIFIER_UUID) == null) {
            // Add the reach modifier
            AttributeModifier reachModifier = new AttributeModifier(REACH_MODIFIER_UUID, "Extended reach", reach - 2, AttributeModifier.Operation.ADDITION);
            attributeInstance.addPermanentModifier(reachModifier);
        }
    }

    private static void removeReachModifier(Player player) {
        var attributeInstance = player.getAttribute(ForgeMod.ENTITY_REACH.get());

        if (attributeInstance != null && attributeInstance.getModifier(REACH_MODIFIER_UUID) != null) {
            // Remove the reach modifier
            attributeInstance.removeModifier(REACH_MODIFIER_UUID);
        }
    }
}

