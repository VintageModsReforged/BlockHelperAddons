package reforged.mods.blockhelper.addons.utils;

import net.minecraft.nbt.NBTTagCompound;

public interface IWailaElementBuilder {

    NBTTagCompound save(NBTTagCompound tag);
    String getTagId();
}
