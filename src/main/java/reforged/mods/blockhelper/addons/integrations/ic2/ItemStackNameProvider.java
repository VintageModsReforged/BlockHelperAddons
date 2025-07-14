package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.api.Items;
import ic2.core.Ic2Items;
import ic2.core.block.BlockIC2Door;
import ic2.core.block.TileEntityBarrel;
import mcp.mobius.waila.api.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ItemStackNameProvider implements IDataProvider {

    public static final ItemStackNameProvider THIS = new ItemStackNameProvider();

    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig iPluginConfig) {
        Block block = accessor.getBlock();
        if (block instanceof BlockIC2Door) return Items.getItem("reinforcedDoor");
        return null;
    }

    @Override
    public void modifyHead(ItemStack stack, ITaggedList<String, String> tooltip, IDataAccessor accessor, IPluginConfig iPluginConfig) {
        try {
            Block block = accessor.getBlock();
            TileEntity blockEntity = accessor.getTileEntity();
            if (blockEntity instanceof TileEntityBarrel) {
                boolean empty = ((TileEntityBarrel) blockEntity).isEmpty();
                String name = "block.blockBarrel." + (empty ? "empty" : "full");
                tooltip.set(0, FormattedTranslator.WHITE.format(name));
            }
            if (block.blockID == Ic2Items.constructionFoamWall.itemID) {
                tooltip.set(0, FormattedTranslator.WHITE.format("block.cfoam.wall"));
            }
        } catch (Throwable ignored) {}
    }

    @Override
    public void modifyBody(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {

    }

    @Override
    public void modifyTail(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {

    }

    @Override
    public void appendServerData(TileEntity tileEntity, NBTTagCompound nBTTagCompound, IServerDataAccessor iServerDataAccessor, IPluginConfig iPluginConfig) {

    }
}
