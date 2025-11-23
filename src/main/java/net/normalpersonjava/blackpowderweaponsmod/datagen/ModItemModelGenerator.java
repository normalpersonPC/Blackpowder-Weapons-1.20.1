package net.normalpersonjava.blackpowderweaponsmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonjava.blackpowderweaponsmod.item.ModItems;

public class ModItemModelGenerator extends ItemModelProvider {
    public ModItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BlackpowderWeaponsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //ammo
        ammoItem(ModItems.MUSKETBALL);
        ammoItem(ModItems.MUSKETBALL_SMALL);
        ammoItem(ModItems.RIFLE_BULLET);
        ammoItem(ModItems.PISTOL_BULLET);
        ammoItem(ModItems.SHOTGUN_PELLETS);
        ammoItem(ModItems.PERCUSSION_CAP);
        ammoItem(ModItems.PAPER_CARTRIDGE_RIFLE);
        ammoItem(ModItems.PAPER_CARTRIDGE_PISTOL);

        //parts
        partsItem(ModItems.BARREL_LONG);
        partsItem(ModItems.BARREL_MEDIUM);
        partsItem(ModItems.BARREL_SHORT);
        partsItem(ModItems.STOCK_LONG);
        partsItem(ModItems.STOCK_PISTOL);
        partsItem(ModItems.FLINTLOCK_MECHANISM);
        partsItem(ModItems.CAPLOCK_MECHANISM);
        partsItem(ModItems.NEEDLEFIRE_BOLT);
        partsItem(ModItems.BREECHBLOCK);
        partsItem(ModItems.REVOLVER_CYLINDER);

        //music disc
        partsItem(ModItems.DISC_CAT_INFDEV);
        partsItem(ModItems.DISC_MY_WAY);

        //tools
        partsItem(ModItems.TOOLKIT);
        partsItem(ModItems.TOOLKIT_CONVERSION);
        partsItem(ModItems.BULLET_MOLD);

        //blueprints
        blueprintItem(ModItems.BLUEPRINT_BREECHBLOCK);
        blueprintItem(ModItems.BLUEPRINT_CAPLOCK_MECHANISM);
        blueprintItem(ModItems.BLUEPRINT_NEEDLEFIRE_BOLT);

    }

    private ItemModelBuilder ammoItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BlackpowderWeaponsMod.MODID, "item/ammo/" + item.getId().getPath()));
    }

    private ItemModelBuilder partsItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BlackpowderWeaponsMod.MODID, "item/parts/" + item.getId().getPath()));
    }

    private ItemModelBuilder blueprintItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BlackpowderWeaponsMod.MODID, "item/parts/blueprint"));
    }
}
