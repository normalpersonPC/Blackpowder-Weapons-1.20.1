package net.normalpersonjava.blackpowderweaponsmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import org.lwjgl.glfw.GLFW;

public final class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings() {

    }

    public static final String CATEGORY = "key.category." + BlackpowderWeaponsMod.MODID;

    public final KeyMapping reload = new KeyMapping(
            "key." + BlackpowderWeaponsMod.MODID + ".reload",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
    );

}
