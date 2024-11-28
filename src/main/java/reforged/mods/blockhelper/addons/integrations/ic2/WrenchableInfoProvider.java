package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BlockHelperAddons;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class WrenchableInfoProvider extends InfoProvider {

    @Override
    public boolean isEnabled() {
        return !Loader.isModLoaded("GregTech_Addon"); // if GT is loaded, use GT InfoProvider instead
    }

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof IWrenchable) {
            ItemStack heldStack = BlockHelperAddons.PROXY.getPlayer().getHeldItem();
            IWrenchable wrenchable = (IWrenchable) blockEntity;
            float dropRate = wrenchable.getWrenchDropRate();
            if (dropRate > 0) {
                if (heldStack != null) {
                    if (heldStack.getItem() instanceof ItemToolWrench) {
                        int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                        helper.add(translate(FormattedTranslator.GOLD, "probe.info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")));
                    } else {
                        helper.add(translate(FormattedTranslator.GOLD, "info.wrenchable"));
                    }
                } else {
                    helper.add(translate(FormattedTranslator.GOLD, "info.wrenchable"));
                }
            }
        }
    }
}
