package reforged.mods.blockhelper.addons.integrations;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.advancedmachines.blocks.tiles.base.TileEntityAdvancedMachine;
import ic2.advancedmachines.blocks.tiles.machines.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class AdvancedMachinesInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TileEntityAdvancedMachine) {
            TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) blockEntity;
            helper.add(usage(advMachine.energyUsage));
            String speedName;
            if (advMachine instanceof TileEntityRotaryMacerator) {
                speedName = "probe.speed.rotation";
            } else if (advMachine instanceof TileEntitySingularityCompressor) {
                speedName = "probe.speed.pressure";
            } else if (advMachine instanceof TileEntityCentrifugeExtractor || advMachine instanceof TileEntityCompactingRecycler) {
                speedName = "probe.speed.speed";
            } else {
                speedName = "probe.speed.heat";
            }
            int speed = advMachine.speed;
            int maxSpeed = advMachine.maxSpeed;
            helper.add(FormattedTranslator.YELLOW.format(speedName, speed * 100 / maxSpeed));
            int progress = advMachine.gaugeProgressScaled(100);
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
            }
        }
    }
}
