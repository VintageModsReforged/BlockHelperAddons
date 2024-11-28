package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class BaseMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TileEntityStandardMachine) {
            TileEntityStandardMachine machine = (TileEntityStandardMachine) blockEntity;
            int energyConsume = machine.energyConsume;
            helper.add(FormattedTranslator.WHITE.format("info.eu_reader.usage", energyConsume));
            float progress = machine.getProgress();
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", (int) (progress * 100)));
            }
        }
    }
}
