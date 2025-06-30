package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.items.GT_Wrench_Item;
import gregtechmod.api.metatileentity.BaseTileEntity;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mcp.mobius.waila.api.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.TooltipHelper;

public class GT_WrenchableInfoProvider extends TooltipHelper implements IDataProvider {

    public static final GT_WrenchableInfoProvider THIS = new GT_WrenchableInfoProvider();

    @Override
    public void modifyTail(ItemStack itemStack, ITaggedList<String, String> strings, IDataAccessor accessor, IPluginConfig iPluginConfig) {
        addInfo(strings, accessor.getTileEntity(), accessor.getPlayer());
    }

    public void addInfo(ITaggedList<String, String> strings, TileEntity blockEntity, EntityPlayer player) {
        int actualDrop;
        ItemStack heldStack = player.getHeldItem();
        if (blockEntity instanceof IWrenchable && ((IWrenchable) blockEntity).getWrenchDropRate() > 0) {
            IWrenchable wrenchable = (IWrenchable) blockEntity;
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    text(strings, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    float dropRate = wrenchable.getWrenchDropRate();
                    actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    text(strings, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else {
                    text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                }
            } else {
                text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
            }
        } else if (blockEntity instanceof BaseTileEntity) {
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    text(strings, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    text(strings, FormattedTranslator.RED.format("info.gt.wrenchable.warning"), true);
                } else {
                    text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                }
            } else {
                text(strings, FormattedTranslator.GOLD.format("info.wrenchable"), true);
            }
        }
    }

    @Override
    public ItemStack getStack(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return null;
    }

    @Override
    public void modifyHead(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

    @Override
    public void modifyBody(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

    @Override
    public void appendServerData(TileEntity tileEntity, NBTTagCompound nbtTagCompound, IServerDataAccessor iServerDataAccessor, IPluginConfig iPluginConfig) {}
}
