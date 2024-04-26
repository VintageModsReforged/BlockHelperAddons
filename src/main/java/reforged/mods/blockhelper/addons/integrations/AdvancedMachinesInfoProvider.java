package reforged.mods.blockhelper.addons.integrations;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.advancedmachines.common.*;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class AdvancedMachinesInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityAdvancedMachine) {
            TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) tile;
            infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", advMachine.energy, advMachine.maxEnergy)));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(2))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", advMachine.maxInput)));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.usage", advMachine.energyConsume)));
            String speedName = "";
            if (advMachine instanceof TileEntityRotaryMacerator) {
                speedName = "info.rotary.speed";
            } else if (advMachine instanceof TileEntitySingularityCompressor) {
                speedName = "info.singularity.speed";
            } else if (advMachine instanceof TileEntityCentrifugeExtractor) {
                speedName = "info.centrifuge.speed";
            } else if (advMachine instanceof TileAdvancedInduction) {
                speedName = "info.induction.heat";
            }
            int speed = advMachine.speed;
            int maxSpeed = advMachine.maxSpeed;
            infoHolder.add(TextColor.YELLOW.format(I18n.format(speedName, speed * 100 / maxSpeed) + "%"));
            int progress = advMachine.gaugeProgressScaled(100);
            if (progress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format(I18n.format("info.progress", progress) + "%"));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return Loader.isModLoaded("AdvancedMachines");
    }
}
