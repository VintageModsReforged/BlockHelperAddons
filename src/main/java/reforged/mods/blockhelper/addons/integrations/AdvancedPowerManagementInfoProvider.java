package reforged.mods.blockhelper.addons.integrations;

import com.kaijin.AdvPowerMan.TEAdjustableTransformer;
import com.kaijin.AdvPowerMan.TEBatteryStation;
import com.kaijin.AdvPowerMan.TEChargingBench;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class AdvancedPowerManagementInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TEChargingBench) {
            TEChargingBench bench = (TEChargingBench) blockEntity;
            helper.add(FormattedTranslator.AQUA.format("info.energy", bench.getStored(), bench.getCapacity()));
            helper.add(tier(bench.baseTier));
            helper.add(maxIn(Helper.getMaxInputFromTier(bench.baseTier)));
            helper.add(translate("info.storage.output", Helper.getMaxInputFromTier(bench.baseTier)));
        }
        if (blockEntity instanceof TEBatteryStation) {
            TEBatteryStation station = (TEBatteryStation) blockEntity;
            helper.add(tier(station.baseTier));
            helper.add(maxIn(Helper.getMaxInputFromTier(station.baseTier)));
            helper.add(translate("info.storage.output", Helper.getMaxInputFromTier(station.baseTier)));
        }
        if (blockEntity instanceof TEAdjustableTransformer) {
            TEAdjustableTransformer adj = (TEAdjustableTransformer) blockEntity;
            helper.add(maxIn(Helper.getMaxInputFromTier(adj.getMaxSafeInput())));
            helper.add(translate("info.storage.output", adj.outputRate));
            helper.add(translate("info.eu_reader.packets.out", adj.packetSize));
        }
    }
}
