package com.thatgrey.both_worlds.block.custom;

import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.entity.custom.DiemondEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class liemondOre extends Block{
    public liemondOre(Properties p_49795_) {
        super(p_49795_);
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();
        if (!level.isClientSide && event.getState().getBlock() == Blocks.DIAMOND_BLOCK) {
            ServerLevel serverLevel = (ServerLevel) level;
            DiemondEntity diemondEntity = new DiemondEntity(ModEntities.DIEMOND.get(), serverLevel);
            diemondEntity.setPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
            serverLevel.addFreshEntity(diemondEntity);
        }
    }
}
