package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.block.wiring.TileEntityElectricBlock;
import mods.vintage.core.helpers.Utils;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.*;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class EUStorageInfoProvider extends InfoProvider {

    public static final EUStorageInfoProvider THIS = new EUStorageInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        int energy;
        if (Utils.instanceOf(blockEntity, "reforged.ic2.addons.asp.tiles.TileEntityAdvancedSolarPanel")) return;
        if (blockEntity instanceof TileEntityElectricMachine && !(blockEntity instanceof TileEntityMatter)) {
            TileEntityElectricMachine machine = (TileEntityElectricMachine) blockEntity;
            energy = machine.energy;
            if (energy > machine.maxEnergy) {
                energy = machine.maxEnergy;
            }
            bar(helper, energy, machine.maxEnergy, FormattedTranslator.WHITE.format("info.energy", Formatter.formatNumber(energy, 2), Formatter.formatNumber(machine.maxEnergy, 2)), ColorUtils.RED);
            text(helper, tier(Helper.getTierFromEU(machine.maxInput)));
            text(helper, maxIn(machine.maxInput));
        } else if (blockEntity instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock storage = (TileEntityElectricBlock) blockEntity;
            energy = storage.energy;
            if (energy > storage.getCapacity()) {
                energy = storage.getCapacity();
            }
            bar(helper, energy, storage.getCapacity(), FormattedTranslator.WHITE.format("info.energy", Formatter.formatNumber(energy, 2), Formatter.formatNumber(storage.getCapacity(), 2)), ColorUtils.RED);
            text(helper, tier(Helper.getTierFromEU(storage.getOutput())));
            text(helper, maxIn(storage.getOutput()));
            text(helper, FormattedTranslator.WHITE.format("info.storage.output", storage.getOutput()));
        } else if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) blockEntity;
            if (generator.storage >= 0) { // exclude negative values, blame windmill
                energy = Math.min(generator.storage, generator.maxStorage);
                bar(helper, energy, generator.maxStorage, FormattedTranslator.WHITE.format("info.energy", Formatter.formatNumber(energy, 2), Formatter.formatNumber(generator.maxStorage, 2)), ColorUtils.RED);
            }
            int maxFuel = 0;
            if (generator instanceof TileEntityGenerator) {
                maxFuel = ((TileEntityGenerator) generator).itemFuelTime;
            } else if (generator instanceof TileEntityGeoGenerator) {
                TileEntityGeoGenerator geo = (TileEntityGeoGenerator) generator;
                maxFuel = geo.maxLava;
            }
            if (generator.fuel > 0) {
                bar(helper, generator.fuel, maxFuel, FormattedTranslator.WHITE.format("info.fuel", generator.fuel, maxFuel), ColorUtils.DARK_GRAY);
            }
        }
    }
}
