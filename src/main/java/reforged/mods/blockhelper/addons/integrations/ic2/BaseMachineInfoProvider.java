package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class BaseMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElectricMachine) {
            TileEntityElectricMachine machine = (TileEntityElectricMachine) blockEntity;
            helper.add(translate("info.energy.usage", machine.energyConsume));
            float progress = machine.getProgress();
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", (int) (progress * 100)));
            }
        }
    }
}
