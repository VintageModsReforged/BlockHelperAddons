package reforged.mods.blockhelper.addons.base.elements;

import net.minecraft.nbt.NBTTagCompound;
import reforged.mods.blockhelper.addons.utils.IWailaElementBuilder;
import reforged.mods.blockhelper.addons.utils.WailaTags;

public class CommonBarElement implements IWailaElementBuilder {

    String TEXT;
    int CURRENT;
    int MAX;
    int COLOR;

    public CommonBarElement(int current, int max, String text, int color) {
        this.CURRENT = current;
        this.MAX = max;
        this.TEXT = text;
        this.COLOR = color;
    }

    public String getText() {
        return this.TEXT;
    }

    public int getCurrent() {
        return this.CURRENT;
    }

    public int getMax() {
        return this.MAX;
    }

    public int getColor() {
        return this.COLOR;
    }

    @Override
    public NBTTagCompound save(NBTTagCompound tag) {
        tag.setString("text", this.TEXT);
        tag.setInteger("current", this.CURRENT);
        tag.setInteger("max", this.MAX);
        tag.setInteger("color", this.COLOR);
        return tag;
    }

    public static CommonBarElement load(NBTTagCompound tag) {
        String text = tag.getString("text");
        int current = tag.getInteger("current");
        int max = tag.getInteger("max");
        int color = tag.getInteger("color");
        return new CommonBarElement(current, max, text, color);
    }

    @Override
    public String getTagId() {
        return WailaTags.ADDON_BAR_TAG;
    }
}
