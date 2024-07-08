package reforged.mods.blockhelper.addons.integrations;

import com.kaijin.AdvPowerMan.TEAdjustableTransformer;
import com.kaijin.AdvPowerMan.TEBatteryStation;
import com.kaijin.AdvPowerMan.TEChargingBench;
import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class AdvancedPowerManagementInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TEChargingBench) {
            TEChargingBench bench = (TEChargingBench) tile;
            infoHolder.add(TextColor.AQUA.format("probe.info.energy", bench.getStored(), bench.getCapacity()));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.tier", Helper.getTierForDisplay(bench.baseTier)));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", Helper.getMaxInputFromTier(bench.baseTier)));
            infoHolder.add(TextColor.WHITE.format("probe.info.storage.output", Helper.getMaxInputFromTier(bench.baseTier)));
        }
        if (tile instanceof TEBatteryStation) {
            TEBatteryStation station = (TEBatteryStation) tile;
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.tier", Helper.getTierForDisplay(station.baseTier)));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", Helper.getMaxInputFromTier(station.baseTier)));
            infoHolder.add(TextColor.WHITE.format("probe.info.storage.output", Helper.getMaxInputFromTier(station.baseTier)));
        }
        if (tile instanceof TEAdjustableTransformer) {
            TEAdjustableTransformer adj = (TEAdjustableTransformer) tile;
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", adj.getMaxSafeInput()));
            infoHolder.add(TextColor.WHITE.format("probe.info.storage.output", adj.outputRate));
            infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.packets.out", adj.packetSize));
        }
    }

    @Override
    public boolean isEnabled() {
        return Loader.isModLoaded("AdvancedPowerManagement");
    }
}
