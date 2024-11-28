package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.wiring.TileEntityCable;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class CableInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TileEntityCable) {
            TileEntityCable cable = (TileEntityCable) blockEntity;
            helper.add(translate("info.eu_reader.cable_limit", cable.getConductorBreakdownEnergy() - 1));
            helper.add(translate("info.eu_reader.cable_loss", cable.getConductionLoss()));
        }
    }
}
