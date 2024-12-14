package reforged.mods.blockhelper.addons.utils;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.item.tool.ItemCropnalyzer;
import ic2.core.item.tool.ItemToolMeter;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.item.tool.ItemTreetapElectric;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.components.IChatComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;

public abstract class InfoProvider implements BlockHelperBlockProvider, IInfoProvider {

    public IFilter READER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof ItemToolMeter;
        }
    };
    public IFilter ANALYZER = new IFilter() {
        @Override
        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof ItemCropnalyzer;
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
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        addInfo(infoHolder, blockHelperBlockState.te);
    }

    public abstract void addInfo(final InfoHolder helper, final TileEntity blockEntity);

    /** TODO:
     * re-add this when BlockHelper provide EntityPlayer inside {@link BlockHelperBlockState}
     * return canHandle(BlockHelperAddons.PROXY.getPlayer())
     * */
    @Override
    public boolean isEnabled() {
        return true;
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

    public void text(InfoHolder helper, String text) {
        helper.add(text);
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
        return translate("info.energy.tier", Helper.getTierForDisplay(tier));
    }

    public String maxIn(int maxIn) {
        return translate("info.energy.input.max", maxIn);
    }

    public String usage(int usage) {
        return translate("info.energy.usage", usage);
    }
}
