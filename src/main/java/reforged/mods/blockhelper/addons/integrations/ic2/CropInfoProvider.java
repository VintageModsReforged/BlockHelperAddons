package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.BlockHelperItemStackFixer;
import de.thexxturboxx.blockhelper.api.BlockHelperNameFixer;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.CropCard;
import ic2.api.Items;
import ic2.api.TECrop;
import ic2.core.block.crop.IC2Crops;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BarElement;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CropInfoProvider extends InfoProvider implements BlockHelperItemStackFixer, BlockHelperNameFixer {

    public static Map<String, ItemStack[]> CROPS_DROPS = new HashMap<String, ItemStack[]>();

    static {
        // ic2
        CROPS_DROPS.put(IC2Crops.cropMelon.name(), new ItemStack[] { new ItemStack(Item.melon), new ItemStack(Block.melon) });
        CROPS_DROPS.put(IC2Crops.cropPotato.name(), new ItemStack[] { new ItemStack(Item.potato), new ItemStack(Item.poisonousPotato) });
    }

    private static final Random random = new Random();

    private static int currentDropIndex;
    private static ItemStack currentDropStack;
    private static String currentBlockName;
    public long lastUpdate = 0;
    public long interval = 1000;

    @Override
    public IFilter getFilter() {
        return ANALYZER;
    }

    @Override
    public ItemStack getItemStack(BlockHelperBlockState state) {
        TileEntity tile = state.te;
        if (tile instanceof TECrop) {
            TECrop cropTile = (TECrop) tile;
            CropCard crop = CropCard.getCrop(cropTile.id);
            if (crop != null) {
                if (!crop.isWeed(cropTile)) {
                    if (CROPS_DROPS.get(crop.name()) != null) {
                        String name = crop.name();
                        if (!name.equals(currentBlockName)) {
                            currentBlockName = name;
                            updateDropIcon(name);
                        } else {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastUpdate >= interval || currentDropStack == null) {
                                lastUpdate = currentTime;
                                updateDropIcon(name);
                            }
                        }
                        return currentDropStack;
                    } else return crop.getGain(cropTile);
                } else {
                    return Items.getItem("weedEx");
                }
            }
        }
        return null;
    }

    private static void updateDropIcon(String cropName) {
        ItemStack[] drops = CROPS_DROPS.get(cropName);
        if (drops != null && drops.length > 0) {
            int nextIndex = random.nextInt(drops.length);
            currentDropIndex = currentDropIndex == nextIndex ? random.nextInt(drops.length) : nextIndex;
            currentDropStack = drops[currentDropIndex];
        }
    }

    @Override
    public String getName(BlockHelperBlockState state) {
        TileEntity tile = state.te;
        if (tile instanceof TECrop) {
            TECrop cropTile = (TECrop) tile;
            CropCard crop = CropCard.getCrop(cropTile.id);
            if (crop != null) {
                return crop.name();
            }
        }
        return null;
    }

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TECrop) {
            TECrop cropTile = (TECrop) blockEntity;
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
                    helper.add(translate("probe.crop.by", FormattedTranslator.AQUA.format("probe.crop.by.unknown")));
                } else {
                    helper.add(translate("probe.crop.by", FormattedTranslator.AQUA.literal(crop.discoveredBy())));
                }
                if (scanLevel < 4) {
                    helper.add("");
                    helper.add(BarElement.bar(scanLevel, 4, FormattedTranslator.GREEN, Translator.format("probe.crop.info.scan", scanLevel, 4)));
                }
                if (scanLevel >= 4) {
                    helper.add("");
                    helper.add(FormattedTranslator.YELLOW.format("probe.crop.stats"));
                    helper.add(BarElement.bar(growth, 31, FormattedTranslator.DARK_GREEN, Translator.format("probe.crop.info.growth", growth, 31)));
                    helper.add(BarElement.bar(gain, 31, FormattedTranslator.GOLD, Translator.format("probe.crop.info.gain", gain, 31)));;
                    helper.add(BarElement.bar(resistance, 31, FormattedTranslator.DARK_AQUA, Translator.format("probe.crop.info.resistance", resistance, 31)));

                    int stress = (crop.tier() - 1) * 4 + growth + gain + resistance;
                    int maxStress = crop.weightInfluences(cropTile, humidity, nutrients, env) * 5;
                    helper.add(BarElement.bar(stress, maxStress, FormattedTranslator.DARK_PURPLE, Translator.format("probe.crop.info.needs", stress, maxStress)));
                }
            }

            helper.add("");
            helper.add(FormattedTranslator.YELLOW.format("probe.crop.storage"));
            helper.add(BarElement.bar(fertilizer, 200, FormattedTranslator.GOLD, Translator.format("probe.crop.info.fertilizer", fertilizer, 100)));
            helper.add(BarElement.bar(water, 200, FormattedTranslator.BLUE, Translator.format("probe.crop.info.water", water, 200)));
            helper.add(BarElement.bar(weedex, 100, FormattedTranslator.LIGHT_PURPLE, Translator.format("probe.crop.info.weedex", weedex, 150)));

            helper.add("");
            helper.add(FormattedTranslator.YELLOW.format("probe.crop.env"));
            helper.add(BarElement.bar(nutrients, 20, FormattedTranslator.GREEN, Translator.format("probe.crop.info.nutrients", nutrients, 20)));
            helper.add(BarElement.bar(humidity, 20, FormattedTranslator.DARK_AQUA, Translator.format("probe.crop.info.humidity", humidity, 20)));
            helper.add(BarElement.bar(env, 10, FormattedTranslator.AQUA, Translator.format("probe.crop.info.env", env, 10)));
            helper.add(BarElement.bar(light, 15, FormattedTranslator.YELLOW, Translator.format("probe.crop.info.light", light, 15)));
        }
    }
}
