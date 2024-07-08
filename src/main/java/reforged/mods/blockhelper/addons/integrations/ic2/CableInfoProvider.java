package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.wiring.TileEntityCable;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.TextColor;

public class CableInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityCable) {
            TileEntityCable cable = (TileEntityCable) tile;
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.cable_limit", cable.getConductorBreakdownEnergy() - 1));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.cable_loss", cable.getConductionLoss()));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
