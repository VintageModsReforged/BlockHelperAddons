package reforged.mods.blockhelper.addons.base;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.base.elements.CommonBarElement;
import reforged.mods.blockhelper.addons.base.elements.CommonFluidElement;
import reforged.mods.blockhelper.addons.base.elements.CommonTextElement;
import reforged.mods.blockhelper.addons.utils.TooltipHelper;
import reforged.mods.blockhelper.addons.utils.WailaHelper;
import reforged.mods.blockhelper.addons.utils.WailaTags;

public class WailaTooltipRenderer extends TooltipHelper implements IDataProvider {

    public static final WailaTooltipRenderer THIS = new WailaTooltipRenderer();

    @Override
    public void modifyBody(ItemStack stack, ITaggedList<String, String> strings, IDataAccessor accessor, IPluginConfig config) {
        addTooltips(strings, accessor);
    }

    public void addTooltips(ITaggedList<String, String> strings, IDataAccessor accessor) {
        NBTTagCompound serverData = accessor.getNBTData();
        if (serverData != null && serverData.hasKey(WailaTags.TAG_DATA)) {
            NBTTagList tagList = serverData.getTagList(WailaTags.TAG_DATA);
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound serverTag = (NBTTagCompound) tagList.tagAt(i);
                // text
                if (serverTag.hasKey(WailaTags.ADDON_TEXT_TAG)) {
                    NBTTagCompound elementTag = serverTag.getCompoundTag(WailaTags.ADDON_TEXT_TAG);
                    CommonTextElement textElement = CommonTextElement.load(elementTag);
                    String text = textElement.getText();
                    boolean centered = textElement.isCentered();
                    text(strings, text, centered);
                }
                // bar
                if (serverTag.hasKey(WailaTags.ADDON_BAR_TAG)) {
                    NBTTagCompound elementTag = serverTag.getCompoundTag(WailaTags.ADDON_BAR_TAG);
                    CommonBarElement barElement = CommonBarElement.load(elementTag);
                    int color = barElement.getColor();
                    int current = barElement.getCurrent();
                    int max = barElement.getMax();
                    String text = barElement.getText();
                    bar(strings, current, max, text, color);
                }
                // TODO: Check if we need it
                // fluid
                if (serverTag.hasKey(WailaTags.ADDON_FLUID_TAG)) {
                    NBTTagCompound elementTag = serverTag.getCompoundTag(WailaTags.ADDON_FLUID_TAG);
                    CommonFluidElement fluidElement = CommonFluidElement.load(elementTag);
                    int current = fluidElement.getCurrent();
                    int max = fluidElement.getMax();
                    String text = fluidElement.getText();
                    int fluidId = fluidElement.getFluidId();
                    fluid(strings, current, max, text, fluidId);
                }
            }
        }
    }

    @Override
    public void appendServerData(TileEntity tile, NBTTagCompound tag, IServerDataAccessor accessor, IPluginConfig config) {
        WailaHelper helper = new WailaHelper();
        WailaCommonHandler.addInfo(helper, tile, accessor.getPlayer());
        helper.transferData(tag);
    }

    /// -------------------- UNUSED
    @Override
    public ItemStack getStack(IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {
        return null;
    }

    @Override
    public void modifyTail(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

    @Override
    public void modifyHead(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}
}
