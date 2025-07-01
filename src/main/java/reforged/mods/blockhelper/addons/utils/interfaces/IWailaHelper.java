package reforged.mods.blockhelper.addons.utils.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IWailaHelper {
    void add(IWailaElementBuilder element);
    void transferData(NBTTagCompound serverData);
}
