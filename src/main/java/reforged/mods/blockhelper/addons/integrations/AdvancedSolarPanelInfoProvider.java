package reforged.mods.blockhelper.addons.integrations;

import advsolar.TileEntitySolarPanel;
import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class AdvancedSolarPanelInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntitySolarPanel) {
            TileEntitySolarPanel panel = (TileEntitySolarPanel) tile;
            infoHolder.add(TextColor.AQUA.format("info.energy", panel.storage, panel.maxStorage));
            infoHolder.add(TextColor.WHITE.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(panel.getMaxEnergyOutput()))));
            infoHolder.add(TextColor.WHITE.format("info.generator.output", panel.gainFuel()));
            infoHolder.add(TextColor.WHITE.format("info.generator.max_output", panel.getMaxEnergyOutput()));
        }
    }

    @Override
    public boolean isEnabled() {
        return Loader.isModLoaded("AdvancedSolarPanel");
    }
}
