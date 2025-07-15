package reforged.mods.blockhelper.addons.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaElementBuilder;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.lang.reflect.Field;
import java.util.List;

public class WailaHelper implements IWailaHelper {

    public NBTTagList DATA = new NBTTagList();

    @Override
    public void add(IWailaElementBuilder element) {
        NBTTagCompound addTag = new NBTTagCompound();
        NBTTagCompound elementTag = element.save(new NBTTagCompound());
        addTag.setTag(element.getTagId(), elementTag);
        DATA.appendTag(addTag);
    }

    @Override
    public void transferData(NBTTagCompound serverData) {
        if (DATA.tagCount() > 0) {
            serverData.setTag(WailaTags.TAG_DATA, this.DATA);
        }
    }
}
