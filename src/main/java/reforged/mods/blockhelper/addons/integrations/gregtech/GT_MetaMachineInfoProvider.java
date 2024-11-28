package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.metatileentity.BaseTileEntity;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_MetaMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof BaseTileEntity) {
            BaseTileEntity baseTile = (BaseTileEntity) blockEntity;
            if (baseTile instanceof GT_TileEntityMetaID_Machine) {
                GT_TileEntityMetaID_Machine machine = (GT_TileEntityMetaID_Machine) blockEntity;
                if (machine.getEnergyCapacity() > 0) {
                    int storage = Math.min(machine.getStored(), machine.getEnergyCapacity());
                    helper.add(FormattedTranslator.AQUA.format("info.energy", storage, machine.getEnergyCapacity()));
                }
                if (machine.maxEUInput() > 0) {
                    helper.add(tier(Helper.getTierFromEU(machine.maxEUInput())));
                    helper.add(maxIn(machine.maxEUInput()));
                }
                int maxOut = machine.maxEUOutput();
                if (maxOut > 0) {
                    helper.add(translate("info.generator.max_output", maxOut));
                }
            }
        }
    }
}
