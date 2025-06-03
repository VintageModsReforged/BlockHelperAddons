package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class BaseMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityStandardMachine) {
            TileEntityStandardMachine machine = (TileEntityStandardMachine) blockEntity;
            int energyConsume = machine.energyConsume;
            text(helper, FormattedTranslator.WHITE.format("info.energy.usage", energyConsume));
            float progress = machine.getProgress();
            if (progress > 0) {
                int scaledOp = (int) Math.min(6.0E7F, (float) machine.progress / machine.operationsPerTick);
                int scaledMaxOp = (int) Math.min(6.0E7F, (float) machine.operationLength / machine.operationsPerTick);
                bar(helper, scaledOp, scaledMaxOp, FormattedTranslator.WHITE.format("info.progress.full", scaledOp, scaledMaxOp).concat("t"), ColorUtils.PROGRESS);
            }
        }
    }
}
