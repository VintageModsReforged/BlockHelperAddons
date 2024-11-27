package reforged.mods.blockhelper.addons.integrations;

import advsolar.TileEntitySolarPanel;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class AdvancedSolarPanelInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntitySolarPanel) {
            TileEntitySolarPanel panel = (TileEntitySolarPanel) blockEntity;
            helper.add(FormattedTranslator.AQUA.format("info.energy", panel.storage, panel.maxStorage));
            helper.add(tier(Helper.getTierFromEU(panel.getMaxEnergyOutput())));
            helper.add(translate("info.generator.output", panel.generating));
            helper.add(translate("info.generator.max_output", panel.getMaxEnergyOutput()));
        }
    }
}
