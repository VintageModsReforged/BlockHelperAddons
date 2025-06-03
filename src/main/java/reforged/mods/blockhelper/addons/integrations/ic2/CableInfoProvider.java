package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.wiring.TileEntityCable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class CableInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCable) {
            TileEntityCable cable = (TileEntityCable) blockEntity;
            text(helper, translate("info.eu_reader.cable_limit", cable.getConductorBreakdownEnergy() - 1));
            text(helper, translate("info.eu_reader.cable_loss", cable.getConductionLoss()));
        }
    }
}
