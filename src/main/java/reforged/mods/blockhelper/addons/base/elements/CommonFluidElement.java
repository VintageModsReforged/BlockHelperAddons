package reforged.mods.blockhelper.addons.base.elements;

import net.minecraft.nbt.NBTTagCompound;
import reforged.mods.blockhelper.addons.utils.WailaTags;

public class CommonFluidElement extends CommonBarElement {

    int FLUID_ID;

    public CommonFluidElement(int current, int max, String text, int fluidId) {
        super(current, max, text, 1);
        this.FLUID_ID = fluidId;
    }

    public int getFluidId() {
        return this.FLUID_ID;
    }

    @Override
    public NBTTagCompound save(NBTTagCompound tag) {
        super.save(tag);
        tag.setInteger("fluidId", this.FLUID_ID);
        return tag;
    }

    public static CommonFluidElement load(NBTTagCompound tag) {
        String text = tag.getString("text");
        int current = tag.getInteger("current");
        int max = tag.getInteger("max");
        int fluidId = tag.getInteger("fluidId");
        return new CommonFluidElement(current, max, text, fluidId);
    }

    @Override
    public String getTagId() {
        return WailaTags.ADDON_FLUID_TAG;
    }
}
