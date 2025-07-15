package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import ic2.core.block.BlockCrop;
import ic2.core.block.TileEntityCrop;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.impl.PluginConfig;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.base.WailaTooltipRenderer;
import reforged.mods.blockhelper.addons.base.tooltiprenderers.BaseProgressBarRenderer;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_WrenchableInfoProvider;
import reforged.mods.blockhelper.addons.integrations.ic2.CropInfoProvider;
import reforged.mods.blockhelper.addons.integrations.ic2.WrenchableInfoProvider;

public class WailaPluginHandler implements IWailaPlugin {

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public void register(IRegistrar registration, Side side) {
        // disable built-in modules for IC2
        registration.registerTooltipRenderer("jade.progress", new BaseProgressBarRenderer());

        registration.registerStackProvider(CropInfoProvider.CropIconProvider.THIS, BlockCrop.class);
        registration.registerNBTProvider(CropInfoProvider.CropIconProvider.THIS, TileEntityCrop.class);
        registration.registerBodyProvider(WailaTooltipRenderer.THIS, Block.class);
        registration.registerNBTProvider(WailaTooltipRenderer.THIS, TileEntity.class);
        registration.registerBodyProvider(WrenchableInfoProvider.THIS, Block.class);
        if (Loader.isModLoaded("GregTech_Addon")) {
            registration.registerBodyProvider(GT_WrenchableInfoProvider.THIS, Block.class);
        }
    }
}
