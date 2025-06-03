package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BlockHelperAddons;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class WrenchableInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (!Loader.isModLoaded("GregTech_Addon")) {
            if (blockEntity instanceof IWrenchable) {
                ItemStack heldStack = BlockHelperAddons.PROXY.getPlayer().getHeldItem();
                IWrenchable wrenchable = (IWrenchable) blockEntity;
                float dropRate = wrenchable.getWrenchDropRate();
                if (dropRate > 0) {
                    if (heldStack != null) {
                        if (heldStack.getItem() instanceof ItemToolWrench) {
                            int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                            text(helper, translate(FormattedTranslator.WHITE, "info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                        } else {
                            text(helper, translate(FormattedTranslator.GOLD, "info.wrenchable"), true);
                        }
                    } else {
                        text(helper, translate(FormattedTranslator.GOLD, "info.wrenchable"), true);
                    }
                }
            }
        }
    }
}
