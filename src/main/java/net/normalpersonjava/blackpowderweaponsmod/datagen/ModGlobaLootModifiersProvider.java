package net.normalpersonjava.blackpowderweaponsmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonjava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonjava.blackpowderweaponsmod.datagen.loot.AddItemModifier;

public class ModGlobaLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobaLootModifiersProvider(PackOutput output) {
        super(output, BlackpowderWeaponsMod.MODID);
    }

    @Override
    protected void start() {
        add("blueprint_breechblock_loot_location", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/pillager_outpost")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build()
        }, ModItems.BLUEPRINT_BREECHBLOCK.get()));

        add("blueprint_caplock_loot_location", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_weaponsmith")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build(),
        }, ModItems.BLUEPRINT_CAPLOCK_MECHANISM.get()));

        add("blueprint_needlefire_bolt_loot_location", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/nether_bridge")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build(),
        }, ModItems.BLUEPRINT_NEEDLEFIRE_BOLT.get()));

        add("disc_my_way_loot_location", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_other")).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build(),
        }, ModItems.DISC_MY_WAY.get()));

        add("disc_my_way_loot_location2", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build(),
        }, ModItems.DISC_MY_WAY.get()));

        add("disc_cat_infdev_loot_location", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build(),
        }, ModItems.DISC_CAT_INFDEV.get()));
    }
}
