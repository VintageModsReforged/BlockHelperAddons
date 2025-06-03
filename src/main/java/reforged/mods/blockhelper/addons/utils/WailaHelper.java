package reforged.mods.blockhelper.addons.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Field;
import java.util.List;

public class WailaHelper implements IWailaHelper {

    public NBTTagList DATA = new NBTTagList();
    public static final String ADD_TAG = "add";

    @Override
    public void add(IWailaElementBuilder element) {
        NBTTagCompound addTag = new NBTTagCompound();
        NBTTagCompound elementTag = element.save(new NBTTagCompound());
        elementTag.setBoolean(ADD_TAG, true);
        addTag.setTag(element.getTagId(), elementTag);
        DATA.appendTag(addTag);
    }

    @Override
    public void transferData(NBTTagCompound serverData) {
        if (!isEmpty(DATA)) {
            serverData.setTag(WailaTags.TAG_DATA, this.DATA);
        }
    }

    public boolean isEmpty(NBTTagList tag) {
        try {
            Field list;
            try {
                list = NBTTagList.class.getDeclaredField("tagList");
            } catch (NoSuchFieldException e) {
                list = NBTTagList.class.getDeclaredField("field_74747_a");
            }
            list.setAccessible(true);
            List tagList = (List) list.get(tag);
            if (tagList != null) {
                return tagList.isEmpty();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
