package reforged.mods.blockhelper.addons.integrations;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.advancedmachines.common.*;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class AdvancedMachinesInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityAdvancedMachine) {
            TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) tile;
            infoHolder.add(TextColor.AQUA.format("probe.info.energy", advMachine.energy, advMachine.maxEnergy));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.tier", Helper.getTierForDisplay(2)));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", advMachine.maxInput));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.usage", advMachine.energyConsume));
            String speedName = "";
            if (advMachine instanceof TileEntityRotaryMacerator) {
                speedName = "probe.info.rotary.speed";
            } else if (advMachine instanceof TileEntitySingularityCompressor) {
                speedName = "probe.info.singularity.speed";
            } else if (advMachine instanceof TileEntityCentrifugeExtractor) {
                speedName = "probe.info.centrifuge.speed";
            } else if (advMachine instanceof TileAdvancedInduction) {
                speedName = "probe.info.induction.heat";
            }
            int speed = advMachine.speed;
            int maxSpeed = advMachine.maxSpeed;
            infoHolder.add(TextColor.YELLOW.format(speedName, speed * 100 / maxSpeed) + "%");
            int progress = advMachine.gaugeProgressScaled(100);
            if (progress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format("probe.info.progress", progress) + "%");
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return Loader.isModLoaded("AdvancedMachines");
    }
}
