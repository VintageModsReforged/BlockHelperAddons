package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class BaseMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElectricMachine) {
            TileEntityElectricMachine machine = (TileEntityElectricMachine) blockEntity;
            int energyConsume = machine.energyConsume;
            text(helper, FormattedTranslator.WHITE.format("info.energy.usage", energyConsume));
            float progress = machine.progress;
            int maxProgress = machine.operationLength;
            if (progress > 0) {
                int scaled = (int) ((progress / maxProgress) * 100);
                bar(helper, (int) progress, maxProgress, FormattedTranslator.WHITE.format("info.progress", scaled), ColorUtils.PROGRESS);
            }
        }
    }
}
