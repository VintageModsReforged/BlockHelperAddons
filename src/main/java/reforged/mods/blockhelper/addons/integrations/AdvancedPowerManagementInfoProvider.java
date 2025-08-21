package reforged.mods.blockhelper.addons.integrations;

import com.kaijin.AdvPowerMan.TEAdjustableTransformer;
import com.kaijin.AdvPowerMan.TEBatteryStation;
import com.kaijin.AdvPowerMan.TEChargingBench;
import mods.vintage.core.helpers.ElectricHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.Formatter;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class AdvancedPowerManagementInfoProvider extends InfoProvider {

    public static final AdvancedPowerManagementInfoProvider THIS = new AdvancedPowerManagementInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TEChargingBench) {
            TEChargingBench bench = (TEChargingBench) blockEntity;
            int stored = bench.getStored();
            int capacity = bench.getCapacity();
            bar(helper, stored, capacity, translate("info.energy", Formatter.formatNumber(stored, 2), Formatter.formatNumber(capacity, 2)), ColorUtils.RED);
            text(helper, tier(bench.baseTier));
            text(helper, maxIn(ElectricHelper.getMaxInputFromTier(bench.baseTier)));
            text(helper, translate("info.storage.output", ElectricHelper.getMaxInputFromTier(bench.baseTier)));
        }
        if (blockEntity instanceof TEBatteryStation) {
            TEBatteryStation station = (TEBatteryStation) blockEntity;
            text(helper, tier(station.baseTier));
            text(helper, maxIn(ElectricHelper.getMaxInputFromTier(station.baseTier)));
            text(helper, translate("info.storage.output", ElectricHelper.getMaxInputFromTier(station.baseTier)));
        }
        if (blockEntity instanceof TEAdjustableTransformer) {
            TEAdjustableTransformer adj = (TEAdjustableTransformer) blockEntity;
            text(helper, maxIn(ElectricHelper.getMaxInputFromTier(adj.getMaxSafeInput())));
            text(helper, translate("info.storage.output", adj.outputRate));
            text(helper, translate("info.eu_reader.packets.out", adj.packetSize));
        }
    }
}
