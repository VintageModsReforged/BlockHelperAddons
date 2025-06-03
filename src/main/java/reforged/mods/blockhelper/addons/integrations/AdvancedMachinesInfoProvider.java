package reforged.mods.blockhelper.addons.integrations;

import ic2.advancedmachines.blocks.tiles.base.TileEntityAdvancedMachine;
import ic2.advancedmachines.blocks.tiles.machines.TileEntityCentrifugeExtractor;
import ic2.advancedmachines.blocks.tiles.machines.TileEntityCompactingRecycler;
import ic2.advancedmachines.blocks.tiles.machines.TileEntityRotaryMacerator;
import ic2.advancedmachines.blocks.tiles.machines.TileEntitySingularityCompressor;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class AdvancedMachinesInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityAdvancedMachine) {
            TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) blockEntity;
            text(helper, usage(advMachine.energyUsage));
            String speedName;
            if (advMachine instanceof TileEntityRotaryMacerator) {
                speedName = "info.speed.rotation";
            } else if (advMachine instanceof TileEntitySingularityCompressor) {
                speedName = "info.speed.pressure";
            } else if (advMachine instanceof TileEntityCentrifugeExtractor || advMachine instanceof TileEntityCompactingRecycler) {
                speedName = "info.speed.speed";
            } else {
                speedName = "info.speed.heat";
            }
            int speed = advMachine.speed;
            int maxSpeed = advMachine.maxSpeed;
            bar(helper, speed, maxSpeed, translate(speedName, speed * 100 / maxSpeed), -295680);
            int progress = advMachine.progress;
            if (progress > 0) {
                int operationsPerTick = advMachine.speed / 30;
                int scaledOp = (int) Math.min(6.0E7F, (float) advMachine.progress / operationsPerTick);
                int scaledMaxOp = (int) Math.min(6.0E7F, (float) advMachine.maxProgress / operationsPerTick);
                bar(helper, scaledOp, scaledMaxOp, FormattedTranslator.WHITE.format("info.progress.full", scaledOp, scaledMaxOp).concat("t"), ColorUtils.PROGRESS);
            }
        }
    }
}
