package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.BlockHelperInfoProvider;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.CropCard;
import ic2.api.TECrop;
import ic2.core.block.crop.IC2Crops;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BarElement;
import reforged.mods.blockhelper.addons.i18n.I18n;
import reforged.mods.blockhelper.addons.TextColor;

public class CropInfoProvider extends BlockHelperInfoProvider {

    @Override
    public ItemStack getItemStack(BlockHelperBlockState state) {
        TileEntity tile = state.te;
        if (tile instanceof TECrop) {
            TECrop cropTile = (TECrop) tile;
            CropCard crop = CropCard.getCrop(cropTile.id);
            if (crop != null) {
                if (!crop.isWeed(cropTile)) {
                    return crop.getGain(cropTile);
                } else {
                    return IC2Crops.weed.getGain(cropTile);
                }
            }
        }
        return super.getItemStack(state);
    }

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TECrop) {
            TECrop cropTile = (TECrop) tile;
            int scanLevel = cropTile.scanLevel;

            // stats info
            int growth = cropTile.statGrowth;
            int gain = cropTile.statGain;
            int resistance = cropTile.statResistance;
            // storage info
            int fertilizer = cropTile.nutrientStorage;
            int water = cropTile.waterStorage;
            int weedex = cropTile.exStorage;
            // environment info
            int nutrients = cropTile.getNutrients();
            int humidity = cropTile.getHumidity();
            int env = cropTile.getAirQuality();
            int light = cropTile.getLightLevel();

            CropCard crop = CropCard.getCrop(cropTile.id);
            if (crop != null) {
                if (scanLevel < 1 && !crop.isWeed(cropTile)) {
                    infoHolder.add(String.format("By: %s", "Unknown"));
                } else {
                    infoHolder.add(String.format("By: %s", crop.discoveredBy()));
                }
                if (scanLevel < 4) {
                    infoHolder.add("");
                    infoHolder.add(BarElement.bar(scanLevel, 4, TextColor.GREEN, I18n.format("crop.scan", scanLevel, 4)));
                }
                if (scanLevel >= 4) {
                    infoHolder.add("");
                    infoHolder.add(TextColor.YELLOW.format("#### Stats ####"));
                    infoHolder.add(BarElement.bar(growth, 31, TextColor.DARK_GREEN, I18n.format("crop.growth", growth, 31)));
                    infoHolder.add(BarElement.bar(gain, 31, TextColor.GOLD, I18n.format("crop.gain", gain, 31)));;
                    infoHolder.add(BarElement.bar(resistance, 31, TextColor.DARK_AQUA, I18n.format("crop.resistance", resistance, 31)));

                    int stress = (crop.tier() - 1) * 4 + growth + gain + resistance;
                    int maxStress = crop.weightInfluences(cropTile, humidity, nutrients, env) * 5;
                    infoHolder.add(BarElement.bar(stress, maxStress, TextColor.DARK_PURPLE, I18n.format("crop.stress", stress, maxStress)));
                }
            }

            infoHolder.add("");
            infoHolder.add(TextColor.YELLOW.format("#### Storage ####"));
            infoHolder.add(BarElement.bar(fertilizer, 200, TextColor.GOLD, I18n.format("crop.fertilizer", fertilizer, 100)));
            infoHolder.add(BarElement.bar(water, 200, TextColor.BLUE, I18n.format("crop.water", water, 200)));
            infoHolder.add(BarElement.bar(weedex, 100, TextColor.LIGHT_PURPLE, I18n.format("crop.weedex", weedex, 150)));

            infoHolder.add("");
            infoHolder.add(TextColor.YELLOW.format("#### Environment ####"));
            infoHolder.add(BarElement.bar(nutrients, 20, TextColor.GREEN, I18n.format("crop.nutrients", nutrients, 20)));
            infoHolder.add(BarElement.bar(humidity, 20, TextColor.DARK_AQUA, I18n.format("crop.humidity", humidity, 20)));
            infoHolder.add(BarElement.bar(env, 10, TextColor.AQUA, I18n.format("crop.env", env, 10)));
            infoHolder.add(BarElement.bar(light, 15, TextColor.YELLOW, I18n.format("crop.light", light, 15)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
