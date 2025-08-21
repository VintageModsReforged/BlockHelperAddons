package reforged.mods.blockhelper.addons.integrations;

import cpw.mods.compactsolars.CompactSolarType;
import cpw.mods.compactsolars.TileEntityCompactSolar;
import mods.vintage.core.helpers.ElectricHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class CompactSolarPanelsInfoProvider extends InfoProvider {

    public static final CompactSolarPanelsInfoProvider THIS = new CompactSolarPanelsInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCompactSolar) {
            TileEntityCompactSolar solar = (TileEntityCompactSolar) blockEntity;
            CompactSolarType type = solar.getType();
            text(helper, tier(ElectricHelper.getTierFromEU(type.getOutput())));
            text(helper, translate("info.generator.output", type.getOutput()));
            text(helper, translate("info.generator.max_output", type.getOutput()));
        }
    }
}
