package reforged.mods.blockhelper.addons.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.machine.tileentity.TileEntityElecMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.i18n.I18n;
import reforged.mods.blockhelper.addons.TextColor;

public class EUStorageInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityElecMachine) {
            TileEntityElecMachine machine = (TileEntityElecMachine) tile;
            infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", machine.energy, machine.maxEnergy)));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromMaxInput(machine.maxInput)))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", machine.maxInput)));
        } else if (tile instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock storage = (TileEntityElectricBlock) tile;
            infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", storage.getStored(), storage.getCapacity())));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromMaxInput(storage.getOutput())))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", storage.getOutput())));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.storage.output", storage.getOutput())));
        } else if (tile instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) tile;
            if (generator.storage >= 0) { // exclude negative values, blame windmill
                infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", generator.storage, generator.maxStorage)));
            }
            int maxFuel = 0;
            if (generator instanceof TileEntityGenerator) {
                maxFuel = ((TileEntityGenerator) generator).itemFuelTime;
            } else if (generator instanceof TileEntityGeoGenerator) {
                maxFuel = ((TileEntityGeoGenerator) generator).maxLava;
            }
            if (maxFuel > 0) {
                infoHolder.add(TextColor.DARK_AQUA.format(I18n.format("info.fuel", generator.fuel, maxFuel)));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
