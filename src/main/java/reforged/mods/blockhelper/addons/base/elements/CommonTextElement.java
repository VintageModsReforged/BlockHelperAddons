package reforged.mods.blockhelper.addons.base.elements;

import net.minecraft.nbt.NBTTagCompound;
import reforged.mods.blockhelper.addons.utils.IWailaElementBuilder;
import reforged.mods.blockhelper.addons.utils.WailaTags;

public class CommonTextElement implements IWailaElementBuilder {

    String TEXT;
    boolean CENTERED;

    public CommonTextElement(String text, boolean centered) {
        this.TEXT = text;
        this.CENTERED = centered;
    }

    public String getText() {
        return TEXT;
    }

    public boolean isCentered() {
        return this.CENTERED;
    }

    @Override
    public NBTTagCompound save(NBTTagCompound tag) {
        tag.setString("text", TEXT);
        tag.setBoolean("centered", this.CENTERED);
        return tag;
    }

    public static CommonTextElement load(NBTTagCompound tag) {
        String text = tag.getString("text");
        boolean centered = tag.getBoolean("centered");
        return new CommonTextElement(text, centered);
    }

    @Override
    public String getTagId() {
        return WailaTags.ADDON_TEXT_TAG;
    }
}
