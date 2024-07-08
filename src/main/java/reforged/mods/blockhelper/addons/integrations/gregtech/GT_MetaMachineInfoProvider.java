package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.metatileentity.BaseTileEntity;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class GT_MetaMachineInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof BaseTileEntity) {
            BaseTileEntity baseTile = (BaseTileEntity) tile;
            if (baseTile instanceof GT_TileEntityMetaID_Machine) {
                GT_TileEntityMetaID_Machine machine = (GT_TileEntityMetaID_Machine) tile;
                if (machine.getEnergyCapacity() > 0) {
                    int storage = machine.getEnergyStored();
                    if (storage > machine.getEnergyCapacity()) {
                        storage = machine.getEnergyCapacity();
                    }
                    infoHolder.add(TextColor.AQUA.format("probe.info.energy", storage, machine.getEnergyCapacity()));
                }
                if (machine.maxEUInput() > 0) {
                    infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(machine.maxEUInput()))));
                    infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", machine.maxEUInput()));
                }
                int maxOut = machine.maxEUOutput();
                if (maxOut > 0) {
                    infoHolder.add(TextColor.WHITE.format("probe.info.generator.max_output", maxOut));
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
