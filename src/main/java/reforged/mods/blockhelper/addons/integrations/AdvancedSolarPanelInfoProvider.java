package reforged.mods.blockhelper.addons.integrations;

import advsolar.TileEntitySolarPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.*;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class AdvancedSolarPanelInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntitySolarPanel) {
            TileEntitySolarPanel panel = (TileEntitySolarPanel) blockEntity;
            int stored = panel.storage;
            int capacity = panel.maxStorage;
            bar(helper, stored, capacity, translate("info.energy", Formatter.formatNumber(stored, 2), Formatter.formatNumber(capacity, 2)), ColorUtils.RED);
            text(helper, tier(Helper.getTierFromEU(panel.getMaxEnergyOutput())));
            text(helper, translate("info.generator.output", panel.generating));
            text(helper, translate("info.generator.max_output", panel.getMaxEnergyOutput()));
        }
    }
}
