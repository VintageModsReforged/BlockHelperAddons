package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class EUStorageInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        int energy;
        if (tile instanceof TileEntityElectricMachine) {
            TileEntityElectricMachine machine = (TileEntityElectricMachine) tile;
            energy = machine.energy;
            if (energy > machine.maxEnergy) {
                energy = machine.maxEnergy;
            }
            infoHolder.add(TextColor.AQUA.format("info.energy", energy, machine.maxEnergy));
            infoHolder.add(TextColor.WHITE.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(machine.maxInput))));
            infoHolder.add(TextColor.WHITE.format("info.eu_reader.max_in", machine.maxInput));
        } else if (tile instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock storage = (TileEntityElectricBlock) tile;
            energy = storage.energy;
            if (energy > storage.getCapacity()) {
                energy = storage.getCapacity();
            }
            infoHolder.add(TextColor.AQUA.format("info.energy", energy, storage.getCapacity()));
            infoHolder.add(TextColor.WHITE.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(storage.getOutput()))));
            infoHolder.add(TextColor.WHITE.format("info.eu_reader.max_in", storage.getOutput()));
            infoHolder.add(TextColor.WHITE.format("info.storage.output", storage.getOutput()));
        } else if (tile instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) tile;
            if (generator.storage >= 0) { // exclude negative values, blame windmill
                energy = generator.storage;
                if (energy > generator.maxStorage) {
                    energy = generator.maxStorage;
                }
                infoHolder.add(TextColor.AQUA.format("info.energy", energy, generator.maxStorage));
            }
            int maxFuel = 0;
            if (generator instanceof TileEntityGenerator) {
                maxFuel = ((TileEntityGenerator) generator).itemFuelTime;
            } else if (generator instanceof TileEntityGeoGenerator) {
                TileEntityGeoGenerator geo = (TileEntityGeoGenerator) generator;
//                Helper.addTankInfo(blockHelperBlockState, infoHolder, geo);
            }
            if (maxFuel > 0) {
                infoHolder.add(TextColor.DARK_AQUA.format("info.fuel", generator.fuel, maxFuel));
            }
        }
//        Helper.addTankInfo(blockHelperBlockState, infoHolder, blockHelperBlockState.te);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
