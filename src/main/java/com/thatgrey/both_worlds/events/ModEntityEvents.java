package com.thatgrey.both_worlds.events;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Both_Worlds.MODID)
public class ModEntityEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            zombie.goalSelector.addGoal(3, new AvoidEntityGoal<>(
                    zombie,
                    MarmotEntity.class,
                    9.0F,
                    1.6D,
                    1.6D
            ));
        }
    }
}
