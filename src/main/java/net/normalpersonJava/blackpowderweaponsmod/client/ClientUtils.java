package net.normalpersonJava.blackpowderweaponsmod.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ClientUtils {


    public static Optional<HumanoidModel.ArmPose> getArmPose(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:firearm")))) {
            return Optional.of(HumanoidModel.ArmPose.BOW_AND_ARROW);
        }
        return Optional.empty();
    }

}

