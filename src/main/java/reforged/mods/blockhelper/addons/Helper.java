package reforged.mods.blockhelper.addons;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import de.thexxturboxx.blockhelper.integration.ForgeIntegration;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class Helper {

    public static void addTankInfo(BlockHelperBlockState state, InfoHolder info, TileEntity tile) {
        if (tile instanceof ITankContainer) {
            ITankContainer fluidHandler = (ITankContainer) tile;
            ILiquidTank tank = fluidHandler.getTanks(ForgeDirection.getOrientation(state.mop.sideHit))[0];
            LiquidStack fluid = tank.getLiquid();
            if (tank.getCapacity() != 0 && fluid != null && fluid.amount > 0) {
                info.add(I18n.format("info.liquid", ForgeIntegration.getLiquidName(fluid), fluid.amount, tank.getCapacity()));
            }
        }
    }

    public static String getTierForDisplay(int tier) {
        switch (tier) {
            case 0:
                return I18n.format("info.tier.ulv");
            case 1:
                return I18n.format("info.tier.lv");
            case 2:
                return I18n.format("info.tier.mv");
            case 3:
                return I18n.format("info.tier.hv");
            case 4:
                return I18n.format("info.tier.ev");
            case 5:
                return I18n.format("info.tier.iv");
            case 6:
                return I18n.format("info.tier.luv");
            case 7:
                return I18n.format("info.tier.zpm");
            case 8:
                return I18n.format("info.tier.uv");
            case 9:
                return I18n.format("info.tier.uhv");
            case 10:
                return I18n.format("info.tier.uev");
            case 11:
                return I18n.format("info.tier.uiv");
            case 12:
                return I18n.format("info.tier.umv");
            case 13:
                return I18n.format("info.tier.uxv");
            case 14:
                return I18n.format("info.tier.max");
            default:
                return TextColor.RED.format("ERROR, please report!");
        }
    }

    public static int getTierFromEUValue(int value) {
        if (value > 0 && value <= 8) {
            return 0;
        } else if (value > 8 && value <= 32) {
            return 1;
        } else if (value > 32 && value <= 128) {
            return 2;
        } else if (value > 128 && value <= 512) {
            return 3;
        } else if (value > 512 && value <= 2048) {
            return 4;
        } else if (value > 2048 && value <= 8192) {
            return 5;
        } else if (value > 8192 && value <= 32768) {
            return 6;
        } else if (value > 32768 && value <= 131072) {
            return 7;
        } else if (value > 131072 && value <= 524288) {
            return 8;
        } else if (value > 524288 && value <= 2097152) {
            return 9;
        } else if (value > 2097152 && value <= 8388608) {
            return 10;
        } else if (value > 8388608 && value <= 33554432) {
            return 11;
        } else if (value > 33554432 && value <= 134217728) {
            return 12;
        } else if (value > 134217728 && value <= 536870912) {
            return 13;
        } else if (value > 536870912) {
            return 14;
        } else {
            return 0;
        }
    }

    public static int getMaxInputFromTier(int tier) {
        switch (tier) {
            case 1:
                return 32;
            case 2:
                return 128;
            case 3:
                return 3;
            case 4:
                return 2048;
            case 5:
                return 8192;
            case 6:
                return 32768;
            default:
                return 0;
        }
    }

    public static String getTextColor(int value) {
        String colorCode = "f"; // white
        if (value >= 90) {
            colorCode = "a"; // green
        }
        if ((value <= 90) && (value > 75)) {
            colorCode = "e"; // yellow
        }
        if ((value <= 75) && (value > 50)) {
            colorCode = "6"; // gold
        }
        if ((value <= 50) && (value > 35)) {
            colorCode = "c"; // red
        }
        if (value <= 35) {
            colorCode = "4"; // dark_red
        }

        return "\247" + colorCode;
    }

    public static boolean hasHotbarItems(EntityPlayer player, ItemStack filter) {
        if (player != null) {
            InventoryPlayer inv = player.inventory;
            for (int i = 0; i < 9; ++i) {
                ItemStack hotbarStack = inv.getStackInSlot(i);
                if (StackUtil.isStackEqual(hotbarStack, filter)) {
                    return true;
                }
            }
        }
        return false;
    }
}
