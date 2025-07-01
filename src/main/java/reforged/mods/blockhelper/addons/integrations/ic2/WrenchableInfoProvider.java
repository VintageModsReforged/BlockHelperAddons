package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import ic2.api.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mcp.mobius.waila.api.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.TooltipHelper;

public class WrenchableInfoProvider extends TooltipHelper implements IDataProvider {

    public static final WrenchableInfoProvider THIS = new WrenchableInfoProvider();

    @Override
    public void modifyTail(ItemStack itemStack, ITaggedList<String, String> strings, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        addInfo(strings, iDataAccessor.getTileEntity(), iDataAccessor.getPlayer());
    }

    public void addInfo(ITaggedList<String, String> strings, TileEntity blockEntity, EntityPlayer player) {
        if (!Loader.isModLoaded("GregTech_Addon")) {
            if (blockEntity instanceof IWrenchable) {
                ItemStack heldStack = player.getHeldItem();
                IWrenchable wrenchable = (IWrenchable) blockEntity;
                float dropRate = wrenchable.getWrenchDropRate();
                if (dropRate > 0) {
                    if (heldStack != null) {
                        if (heldStack.getItem() instanceof ItemToolWrench) {
                            int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                            text(strings, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);

                        } else {
                            text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                        }
                    } else {
                        text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                    }
                }
            }
        }
    }

    /// -------------------- UNUSED
    @Override
    public ItemStack getStack(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return null;
    }

    @Override
    public void appendServerData(TileEntity tileEntity, NBTTagCompound nbtTagCompound, IServerDataAccessor iServerDataAccessor, IPluginConfig iPluginConfig) {}

    @Override
    public void modifyHead(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

    @Override
    public void modifyBody(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}
}
