package reforged.mods.blockhelper.addons.utils;

import ic2.core.item.tool.ItemCropnalyzer;
import ic2.core.item.tool.ItemToolMeter;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.item.tool.ItemTreetapElectric;
import mods.vintage.core.helpers.ElectricHelper;
import mods.vintage.core.platform.lang.Translator;
import mods.vintage.core.platform.lang.component.MutableComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import reforged.mods.blockhelper.addons.base.elements.CommonBarElement;
import reforged.mods.blockhelper.addons.base.elements.CommonFluidElement;
import reforged.mods.blockhelper.addons.base.elements.CommonTextElement;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaElementBuilder;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.util.List;
import java.util.Locale;

public abstract class InfoProvider implements IInfoProvider {

    public static IFilter READER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof ItemToolMeter;
        }
    };
    public static IFilter ANALYZER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof ItemCropnalyzer;
        }
    };
    public static IFilter TREETAP = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && (stack.getItem() instanceof ItemTreetap || stack.getItem() instanceof ItemTreetapElectric);
        }
    };
    public IFilter ALWAYS = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return true;
        }
    };

    public IFilter getFilter() {
        return READER;
    }

    @Override
    public boolean canHandle(EntityPlayer player) {
        return player != null && (hasHotbarItem(player, getFilter()) || player.capabilities.isCreativeMode);
    }

    private boolean hasHotbarItem(EntityPlayer player, IFilter filter) {
        if (player != null) {
            InventoryPlayer inventoryPlayer = player.inventory;
            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventoryPlayer.getStackInSlot(i);
                if (stack != null) {
                    if (filter.matches(stack)) {
                        return true;
                    }
                }
            }
            ItemStack armorStack = inventoryPlayer.armorItemInSlot(3);
            if (ToolIntegrationHelper.isHelmet(armorStack)) {
                List<String> installed = ToolIntegrationHelper.getInstalledTools(armorStack);
                for (String tool : installed) {
                    ToolIntegrationHelper.Tools stack = ToolIntegrationHelper.Tools.valueOf(tool.toUpperCase(Locale.ROOT));
                    ItemStack toolStack = stack.toolInstance.get();
                    if (toolStack != null && filter.matches(toolStack)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String status(boolean status) {
        return status ? Translator.GREEN.format("" + true) : Translator.RED.format("" + false);
    }

    public void text(IWailaHelper helper, String text, boolean centered) {
        CommonTextElement element = new CommonTextElement(text, centered);
        add(helper, element);
    }

    public void text(IWailaHelper helper, String text) {
        CommonTextElement element = new CommonTextElement(text, false);
        add(helper, element);
    }

    public void textCentered(IWailaHelper helper, String text) {
        CommonTextElement element = new CommonTextElement(text, true);
        add(helper, element);
    }

    public void bar(IWailaHelper helper, int current, int max, String text, int color) {
        CommonBarElement barElement = new CommonBarElement(current, max, text, color);
        add(helper, barElement);
    }

    public void fluidBar(IWailaHelper helper, int current, int max, String text, int fluidId) {
        CommonFluidElement element = new CommonFluidElement(current, max, text, fluidId);
        add(helper, element);
    }

    public String translate(String translatable) {
        return Translator.WHITE.format(translatable);
    }

    public String translate(String translatable, Object... args) {
        return Translator.WHITE.format(translatable, args);
    }

    public String literal(String literal) {
        return Translator.WHITE.literal(literal);
    }

    public String translate(Translator formatter, String translatable) {
        return formatter.format(translatable);
    }

    public String translate(Translator formatter, String translatable, Object... args) {
        return formatter.format(translatable, args);
    }

    public String literal(Translator formatter, String translatable) {
        return formatter.literal(translatable);
    }

    public String percent(MutableComponent percentageComp) {
        return percentageComp.append(Translator.WHITE.literal("%")).getFormattedString();
    }

    public String tier(int tier) {
        return translate("info.energy.tier", ElectricHelper.getTierForDisplay(tier));
    }

    public String maxIn(int maxIn) {
        return translate("info.energy.input.max", maxIn);
    }

    public String usage(int usage) {
        return translate("info.energy.usage", usage);
    }

    public void add(IWailaHelper helper, IWailaElementBuilder element) {
        helper.add(element);
    }
}
