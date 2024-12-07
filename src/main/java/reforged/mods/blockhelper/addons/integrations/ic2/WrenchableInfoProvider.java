package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class WrenchableInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        boolean showInfo = false;
        float dropRate = 0;
        if (blockEntity instanceof IWrenchable) {
            IWrenchable wrenchable = (IWrenchable) blockEntity;
            dropRate = wrenchable.getWrenchDropRate();
            if (dropRate > 0) {
                showInfo = true;
            }
        }
        if (showInfo) {
            ItemStack heldStack = player.getHeldItem();
            if (heldStack != null) {
                if (heldStack.getItem() instanceof ItemToolWrench) {
                    int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    helper.add(translate(FormattedTranslator.GOLD, "info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")));
                } else {
                    helper.add(translate(FormattedTranslator.GOLD, "info.wrenchable"));
                }
            } else {
                helper.add(translate(FormattedTranslator.GOLD, "info.wrenchable"));
            }
        }
    }

    @Override
    public IFilter getFilter() {
        return ALWAYS;
    }
}
