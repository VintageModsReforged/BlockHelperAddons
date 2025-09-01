package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import mods.vintage.core.helpers.ElectricHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class GT_MetaMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof GT_TileEntityMetaID_Machine) {
            GT_TileEntityMetaID_Machine machine = (GT_TileEntityMetaID_Machine) blockEntity;
            if (machine.getCapacity() > 0) {
                int storage = Math.min(machine.getStored(), machine.getCapacity());
                bar(helper, storage, machine.getCapacity(), translate("info.energy", storage, machine.getCapacity()), ColorUtils.RED);
            }
            if (machine.maxEUInput() > 0) {
                text(helper, tier(ElectricHelper.getTierFromEU(machine.maxEUInput())));
                text(helper, maxIn(machine.maxEUInput()));
            }
            int maxOut = machine.maxEUOutput();
            if (maxOut > 0) {
                text(helper, translate("info.generator.max_output", maxOut));
            }
        }
    }
}
