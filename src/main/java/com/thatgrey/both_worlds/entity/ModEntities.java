package com.thatgrey.both_worlds.entity;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.DiemondEntity;
import com.thatgrey.both_worlds.entity.custom.HyraxEntity;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Both_Worlds.MODID);

    public static final RegistryObject<EntityType<HyraxEntity>> HYRAX =
            ENTITIES.register("hyrax",
                    () -> EntityType.Builder.of(HyraxEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 0.7f)
                            .build(new ResourceLocation(Both_Worlds.MODID, "hyrax").toString()));

    public static final RegistryObject<EntityType<DiemondEntity>> DIEMOND =
            ENTITIES.register("diemond",
                    () -> EntityType.Builder.of(DiemondEntity::new, MobCategory.CREATURE)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(Both_Worlds.MODID, "diemond").toString()));

    public static final RegistryObject<EntityType<MarmotEntity>> MARMOT =
            ENTITIES.register("marmot",
                    () -> EntityType.Builder.of(MarmotEntity::new, MobCategory.CREATURE)
                            .sized(0.4f, 0.4f)
                            .build(new ResourceLocation(Both_Worlds.MODID, "marmot").toString()));

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
