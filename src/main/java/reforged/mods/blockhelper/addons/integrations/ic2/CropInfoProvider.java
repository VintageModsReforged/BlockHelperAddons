package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.api.CropCard;
import ic2.api.Items;
import ic2.api.TECrop;
import ic2.core.block.TileEntityCrop;
import ic2.core.block.crop.IC2Crops;
import mcp.mobius.waila.api.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class CropInfoProvider extends InfoProvider {

    public static Map<String, ItemStack[]> CROPS_DROPS = new HashMap<String, ItemStack[]>();
    public static Map<CropCard, Integer> CROPS_MAX_SIZES = new HashMap<CropCard, Integer>();

    static {
        // ic2
        CROPS_DROPS.put(IC2Crops.cropMelon.name(), new ItemStack[] { new ItemStack(Item.melon), new ItemStack(Block.melon) });
        CROPS_DROPS.put(IC2Crops.cropPotato.name(), new ItemStack[] { new ItemStack(Item.potato), new ItemStack(Item.poisonousPotato) });
        // TODO: CHECK THESE
        CROPS_MAX_SIZES.put(IC2Crops.cropAurelia, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropCocoa, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropCoffee, 5);
        CROPS_MAX_SIZES.put(IC2Crops.cropYellowFlower, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropRedFlower, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropBlackFlower, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropPurpleFlower, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropBlueFlower, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropFerru, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropHops, 7);
        CROPS_MAX_SIZES.put(IC2Crops.cropMelon, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropNetherWart, 3);
        CROPS_MAX_SIZES.put(IC2Crops.cropPotato, 3);
        CROPS_MAX_SIZES.put(IC2Crops.cropCarrots, 3);
        CROPS_MAX_SIZES.put(IC2Crops.cropPumpkin, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropRedwheat, 7);
        CROPS_MAX_SIZES.put(IC2Crops.cropReed, 3);
        CROPS_MAX_SIZES.put(IC2Crops.cropStickReed, 4);
        CROPS_MAX_SIZES.put(IC2Crops.cropTerraWart, 3);
        CROPS_MAX_SIZES.put(IC2Crops.cropVenomilia, 6);
        CROPS_MAX_SIZES.put(IC2Crops.cropWheat, 7);
    }

    @Override
    public IFilter getFilter() {
        return ANALYZER;
    }

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCrop) {
            TileEntityCrop cropTile = (TileEntityCrop) blockEntity;
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

            if (cropTile.id != -1) {
                CropCard crop = CropCard.getCrop(cropTile.id);
                if (crop != null) {
                    int size = cropTile.size;
                    int maxSize = CROPS_MAX_SIZES.get(cropTile.crop());
                    if (scanLevel < 1 && !crop.isWeed(cropTile)) {
                        text(helper, translate("info.crop.by", FormattedTranslator.AQUA.format("info.crop.by.unknown")));
                    } else {
                        text(helper, translate("info.crop.by", FormattedTranslator.AQUA.literal(crop.discoveredBy())));
                    }
                    if (scanLevel < 4) {
                        bar(helper, scanLevel, 4, FormattedTranslator.WHITE.format("info.crop.info.scan", scanLevel, 4), ColorUtils.GREEN);
                    }
                    if (scanLevel >= 4) {
                        text(helper, FormattedTranslator.YELLOW.format("info.crop.growth"), true);
                        if (size == maxSize) {
                            bar(helper, maxSize, maxSize, translate("info.crop.info.stage_done", maxSize), ColorUtils.GREEN);
                        } else {
                            int growthPoints = cropTile.growthPoints;
                            int maxGrowthPoints = crop.growthDuration(cropTile);
                            bar(helper, size, maxSize, translate("info.crop.info.stage", size, maxSize), ColorUtils.GREEN);
                            bar(helper, growthPoints, maxGrowthPoints, translate("info.crop.info.points", growthPoints, maxGrowthPoints), ColorUtils.GREEN);
                        }

                        if (!crop.canGrow(cropTile)) {
                            text(helper, FormattedTranslator.RED.format("info.crop.grow.not"), true);
                        } else {
                            text(helper, FormattedTranslator.GREEN.format("info.crop.grow.rate", cropTile.calcGrowthRate()), true);
                        }
                        text(helper, FormattedTranslator.GOLD.format("info.crop.harvest", status(crop.canBeHarvested(cropTile))));
                        text(helper, FormattedTranslator.YELLOW.format("info.crop.stats"), true);
                        bar(helper, growth, 31, translate("info.crop.info.growth", growth, 31), ColorUtils.CYAN);
                        bar(helper, gain, 31, translate("info.crop.info.gain", gain, 31), -5829955);
                        bar(helper, resistance, 31, translate("info.crop.info.resistance", resistance, 31), ColorUtils.rgb(255, 170, 0));

                        int stress = (crop.tier() - 1) * 4 + growth + gain + resistance;
                        int maxStress = crop.weightInfluences(cropTile, humidity, nutrients, env) * 5;
                        bar(helper, stress, maxStress, translate("info.crop.info.needs", stress, maxStress), ColorUtils.CYAN);
                        DecimalFormat format = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ROOT));
                        text(helper, FormattedTranslator.GOLD.format("info.crop.drop.chance", format.format(crop.dropSeedChance(cropTile) * 100.0)), true);

                    }
                }
            }

            text(helper, FormattedTranslator.YELLOW.format("info.crop.storage"), true);
            bar(helper, fertilizer, 100, translate("info.crop.info.fertilizer", fertilizer, 100), ColorUtils.rgb(86, 54, 36));
            bar(helper, water, 200, translate("info.crop.info.water", water, 200), ColorUtils.rgb(93, 105, 255));
            bar(helper, weedex, 150, translate("info.crop.info.weedex", weedex, 150), ColorUtils.rgb(255, 85, 255));

            text(helper, FormattedTranslator.YELLOW.format("info.crop.env"), true);
            bar(helper, nutrients, 20, translate("info.crop.info.nutrients", nutrients, 20), ColorUtils.rgb(0, 255, 5));
            bar(helper, humidity, 20, translate("info.crop.info.humidity", humidity, 20), ColorUtils.rgb(93, 105, 255));
            bar(helper, env, 10, translate("info.crop.info.env", env, 10), ColorUtils.CYAN);
            bar(helper, light, 15, translate("info.crop.info.light", light, 15), ColorUtils.rgb(255, 255, 85));
        }
    }

    public static class CropIconProvider implements IDataProvider {

        public static final CropIconProvider THIS = new CropIconProvider();

        public static Map<String, ItemStack[]> CROPS_DROPS = new HashMap<String, ItemStack[]>();

        static {
            // ic2
            CROPS_DROPS.put(IC2Crops.cropMelon.name(), new ItemStack[] { new ItemStack(Item.melon), new ItemStack(Block.melon) });
            CROPS_DROPS.put(IC2Crops.cropPotato.name(), new ItemStack[] { new ItemStack(Item.potato), new ItemStack(Item.poisonousPotato) });
            // GregTech
            CROPS_DROPS.put("Bobsyeruncleranks", new ItemStack[]{ new ItemStack(Item.emerald) });
            CROPS_DROPS.put("Diareed", new ItemStack[]{ new ItemStack(Item.diamond) });
            CROPS_DROPS.put("Withereed", new ItemStack[]{ new ItemStack(Item.coal), Items.getItem("coalDust") });
            CROPS_DROPS.put("Blazereed", new ItemStack[]{ new ItemStack(Item.blazePowder), new ItemStack(Item.blazeRod) });
            CROPS_DROPS.put("Eggplant", new ItemStack[]{ new ItemStack(Item.egg), new ItemStack(Item.chickenRaw), new ItemStack(Item.feather) });
            CROPS_DROPS.put("Corpseplant", new ItemStack[]{ new ItemStack(Item.rottenFlesh), new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.bone) });
            CROPS_DROPS.put("Enderbloom", new ItemStack[]{ new ItemStack(Item.enderPearl), new ItemStack(Item.eyeOfEnder) });
            CROPS_DROPS.put("Meatrose", new ItemStack[]{ new ItemStack(Item.dyePowder, 1, 9), new ItemStack(Item.beefRaw), new ItemStack(Item.porkRaw), new ItemStack(Item.chickenRaw), new ItemStack(Item.fishRaw) });
            CROPS_DROPS.put("Spidernip", new ItemStack[]{ new ItemStack(Block.web), new ItemStack(Item.silk), new ItemStack(Item.spiderEye) });
        }

        private static final Random random = new Random();

        private static int currentDropIndex;
        private static ItemStack currentDropStack;
        private static String currentBlockName;
        public long lastUpdate = 0;
        public long interval = 1000;

        @Override
        public ItemStack getStack(IDataAccessor accessor, IPluginConfig iPluginConfig) {
            TileEntity tile = accessor.getTileEntity();
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
        public void modifyHead(ItemStack stack, ITaggedList<String, String> tooltip, IDataAccessor accessor, IPluginConfig config) {
            TileEntity tile = accessor.getTileEntity();
            if (tile instanceof TECrop) {
                TECrop cropTile = (TECrop) tile;
                CropCard crop = CropCard.getCrop(cropTile.id);
                if (crop != null) {
                    tooltip.add(0, crop.name());
                }
            }
        }

        @Override
        public void modifyBody(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

        @Override
        public void modifyTail(ItemStack itemStack, ITaggedList<String, String> iTaggedList, IDataAccessor iDataAccessor, IPluginConfig iPluginConfig) {}

        @Override
        public void appendServerData(TileEntity tileEntity, NBTTagCompound nbtTagCompound, IServerDataAccessor iServerDataAccessor, IPluginConfig iPluginConfig) {}
    }
}
