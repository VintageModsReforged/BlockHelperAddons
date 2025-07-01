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
        PluginConfig.instance().setConfig("modules", "ic2.storage", false);
        PluginConfig.instance().setConfig("modules", "ic2.outputeu", false);
        registration.registerStackProvider(CropInfoProvider.CropIconProvider.THIS, BlockCrop.class);
        registration.registerNBTProvider(CropInfoProvider.CropIconProvider.THIS, TileEntityCrop.class);
        registration.registerBodyProvider(WailaTooltipRenderer.THIS, Block.class);
        registration.registerNBTProvider(WailaTooltipRenderer.THIS, TileEntity.class);
        registration.registerTailProvider(WrenchableInfoProvider.THIS, Block.class);
        if (Loader.isModLoaded("GregTech_Addon")) {
            registration.registerTailProvider(GT_WrenchableInfoProvider.THIS, Block.class);
        }

        registration.registerTooltipRenderer("jade.progress", new BaseProgressBarRenderer());
    }
}
