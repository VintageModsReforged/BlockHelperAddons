package reforged.mods.blockhelper.addons.utils;

import ic2.core.item.tool.ItemCropnalyzer;
import ic2.core.item.tool.ItemToolMeter;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.item.tool.ItemTreetapElectric;
import mods.vintage.core.helpers.Utils;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.components.ChatComponentTranslation;
import mods.vintage.core.platform.lang.components.IChatComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import reforged.mods.blockhelper.addons.base.elements.CommonBarElement;
import reforged.mods.blockhelper.addons.base.elements.CommonFluidElement;
import reforged.mods.blockhelper.addons.base.elements.CommonTextElement;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaElementBuilder;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public abstract class InfoProvider implements IInfoProvider {

    public IFilter READER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof ItemToolMeter;
        }
    };
    public IFilter ANALYZER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && (stack.getItem() instanceof ItemCropnalyzer || Utils.instanceOf(stack.getItem(), "dev.vintage.cropnalyzer.item.ItemAdvancedAnalyzer"));
        }
    };
    public IFilter TREETAP = new IFilter() {
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
                if (filter.matches(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String status(boolean status) {
        return status ? FormattedTranslator.GREEN.format("" + true) : FormattedTranslator.RED.format("" + false);
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

    public void fluidBar(IWailaHelper helper, int current, int max, String text, String fluidId) {
        CommonFluidElement element = new CommonFluidElement(current, max, text, fluidId);
        add(helper, element);
    }

    public String translate(String translatable) {
        return FormattedTranslator.WHITE.format(translatable);
    }

    public String translate(String translatable, Object... args) {
        return FormattedTranslator.WHITE.format(translatable, args);
    }

    public String literal(String literal) {
        return FormattedTranslator.WHITE.literal(literal);
    }

    public String translate(FormattedTranslator formatter, String translatable) {
        return formatter.format(translatable);
    }

    public String translate(FormattedTranslator formatter, String translatable, Object... args) {
        return formatter.format(translatable, args);
    }

    public String literal(FormattedTranslator formatter, String translatable) {
        return formatter.literal(translatable);
    }

    public String percent(IChatComponent percentageComp) {
        return percentageComp.appendText(FormattedTranslator.WHITE.literal("%")).getFormattedText();
    }

    public String tier(int tier) {
        return new ChatComponentTranslation("info.energy.tier", Helper.getTierForDisplay(tier)).getFormattedText();
    }

    public String maxIn(int maxIn) {
        return new ChatComponentTranslation("info.energy.input.max", maxIn).getFormattedText();
    }

    public String usage(int usage) {
        return new ChatComponentTranslation("info.energy.usage", usage).getFormattedText();
    }

    public void add(IWailaHelper helper, IWailaElementBuilder element) {
        helper.add(element);
    }
}
