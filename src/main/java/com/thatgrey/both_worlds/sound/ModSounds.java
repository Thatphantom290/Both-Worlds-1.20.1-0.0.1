package com.thatgrey.both_worlds.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.thatgrey.both_worlds.Both_Worlds;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Both_Worlds.MODID);

    public static final RegistryObject<SoundEvent> MARMOT_IDLE = registerSound("marmot_idle");
    public static final RegistryObject<SoundEvent> MARMOT_HURT1 = registerSound("marmot_hurt1");
    public static final RegistryObject<SoundEvent> MARMOT_HAPPY = registerSound("marmot_happy");
    public static final RegistryObject<SoundEvent> MARMOT_DEATH = registerSound("marmot_death");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation id = new ResourceLocation(Both_Worlds.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}