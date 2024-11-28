package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class EUStorageInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        int energy;
        if (blockEntity instanceof TileEntityElectricMachine) {
            TileEntityElectricMachine machine = (TileEntityElectricMachine) blockEntity;
            energy = machine.energy;
            if (energy > machine.maxEnergy) {
                energy = machine.maxEnergy;
            }
            helper.add(FormattedTranslator.AQUA.format("info.energy", energy, machine.maxEnergy));
            helper.add(tier(Helper.getTierFromEU(machine.maxInput)));
            helper.add(maxIn(machine.maxInput));
        } else if (blockEntity instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock storage = (TileEntityElectricBlock) blockEntity;
            energy = storage.energy;
            if (energy > storage.getCapacity()) {
                energy = storage.getCapacity();
            }
            helper.add(FormattedTranslator.AQUA.format("info.energy", energy, storage.getCapacity()));
            helper.add(tier(Helper.getTierFromEU(storage.getOutput())));
            helper.add(maxIn(storage.getOutput()));
            helper.add(FormattedTranslator.WHITE.format("info.storage.output", storage.getOutput()));
        } else if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) blockEntity;
            if (generator.storage >= 0) { // exclude negative values, blame windmill
                energy = Math.min(generator.storage, generator.maxStorage);
                helper.add(FormattedTranslator.AQUA.format("info.energy", energy, generator.maxStorage));
            }
            int maxFuel = 0;
            if (generator instanceof TileEntityGenerator) {
                maxFuel = ((TileEntityGenerator) generator).itemFuelTime;
            } else if (generator instanceof TileEntityGeoGenerator) {
                TileEntityGeoGenerator geo = (TileEntityGeoGenerator) generator;
//                Helper.addTankInfo(blockHelperBlockState, infoHolder, geo);
            }
            if (maxFuel > 0) {
                helper.add(FormattedTranslator.DARK_AQUA.format("info.fuel", generator.fuel, maxFuel));
            }
        }
    }
}
