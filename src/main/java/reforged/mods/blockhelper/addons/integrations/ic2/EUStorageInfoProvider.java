package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.machine.tileentity.TileEntityElecMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class EUStorageInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElecMachine) {
            TileEntityElecMachine machine = (TileEntityElecMachine) blockEntity;
            helper.add(FormattedTranslator.AQUA.format("info.energy", machine.energy, machine.maxEnergy));
            helper.add(tier(Helper.getTierFromEU(machine.maxInput)));
            helper.add(maxIn(machine.maxInput));
        } else if (blockEntity instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock storage = (TileEntityElectricBlock) blockEntity;
            helper.add(FormattedTranslator.AQUA.format("info.energy", storage.getStored(), storage.getCapacity()));
            helper.add(tier(Helper.getTierFromEU(storage.getOutput())));
            helper.add(maxIn(storage.getOutput()));
            helper.add(FormattedTranslator.WHITE.format("info.storage.output", storage.getOutput()));
        } else if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) blockEntity;
            int storage = Math.max(0, generator.storage);
            helper.add(FormattedTranslator.AQUA.format("info.energy", storage, generator.maxStorage));
            int maxFuel = 0;
            if (generator instanceof TileEntityGenerator) {
                maxFuel = ((TileEntityGenerator) generator).itemFuelTime;
            } else if (generator instanceof TileEntityGeoGenerator) {
                maxFuel = ((TileEntityGeoGenerator) generator).maxLava;
//                Helper.addTankInfo(blockHelperBlockState, infoHolder, generator);
            }
            if (maxFuel > 0) {
                helper.add(FormattedTranslator.DARK_AQUA.format("info.fuel", generator.fuel, maxFuel));
            }
        }
    }
}
