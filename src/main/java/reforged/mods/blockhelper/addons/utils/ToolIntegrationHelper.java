package reforged.mods.blockhelper.addons.utils;

import ic2.api.item.Items;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import mods.vintage.core.helpers.StackHelper;
import mods.vintage.core.utils.LazyEntry;
import mods.vintage.core.utils.function.Supplier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static reforged.mods.blockhelper.addons.utils.InfoProvider.*;

public class ToolIntegrationHelper {

    public static final List<IInfoProvider.IFilter> TOOL_FILTERS = Arrays.asList(READER, ANALYZER, TREETAP);

    public static boolean isHelmet(ItemStack stack) {
        return stack != null && (stack.getItem() instanceof ItemArmorNanoSuit || stack.getItem() instanceof ItemArmorQuantumSuit);
    }

    public static List<String> getInstalledTools(ItemStack helmet) {
        List<String> tools = new ArrayList<String>();
        if (helmet != null) {
            NBTTagCompound tag = StackHelper.getOrCreateTag(helmet);
            for (Tools tool : Tools.values()) {
                if (tag.getBoolean(tool.name())) {
                    tools.add(tool.name().toLowerCase(Locale.ROOT));
                }
            }
        }
        return tools;
    }

    public static void addTool(ItemStack helmet, Tools tool) {
        NBTTagCompound tag = StackHelper.getOrCreateTag(helmet);
        if (!tag.getBoolean(tool.name())) {
            tag.setBoolean(tool.name(), true);
        }
    }

    public static void removeTool(ItemStack helmet, Tools tool) {
        if (helmet == null) {
            return;
        }
        NBTTagCompound tag = StackHelper.getOrCreateTag(helmet);
        if (!tag.getBoolean(tool.name())) return;
        tag.setBoolean(tool.name(), false);
    }

    public static boolean hasReader(ItemStack stack) {
        NBTTagCompound tag = StackHelper.getOrCreateTag(stack);
        return tag.getBoolean(Tools.READER.name());
    }

    public static boolean hasAnalyzer(ItemStack stack) {
        NBTTagCompound tag = StackHelper.getOrCreateTag(stack);
        return tag.getBoolean(Tools.CROPNALYZER.name());
    }

    public static boolean hasTreeTap(ItemStack stack) {
        NBTTagCompound tag = StackHelper.getOrCreateTag(stack);
        return tag.getBoolean(Tools.TREETAP.name());
    }

    public enum Tools {
        READER(new Supplier<ItemStack>() {
            @Override
            public ItemStack get() {
                return Items.getItem("ecMeter");
            }
        }, "ic2.itemToolMEter"),
        CROPNALYZER(new Supplier<ItemStack>() {
            @Override
            public ItemStack get() {
                return Items.getItem("cropnalyzer");
            }
        }, "ic2.itemCropnalyzer"),
        TREETAP(new Supplier<ItemStack>() {
            @Override
            public ItemStack get() {
                return Items.getItem("treetap");
            }
        }, "ic2.itemTreetap"),
        EMPTY(new Supplier<ItemStack>() {
            @Override
            public ItemStack get() {
                return null;
            }
        }, "");

        public final LazyEntry<ItemStack> toolInstance;
        public final String description;

        Tools(Supplier<ItemStack> tool, String translationKey) {
            this.toolInstance = new LazyEntry<ItemStack>(tool);
            this.description = translationKey;
        }
    }
}
