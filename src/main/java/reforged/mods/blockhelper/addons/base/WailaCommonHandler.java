package reforged.mods.blockhelper.addons.base;

import cpw.mods.fml.common.Loader;
import mods.vintage.core.platform.lang.components.ChatComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import reforged.mods.blockhelper.addons.base.elements.CommonFluidElement;
import reforged.mods.blockhelper.addons.integrations.AdvancedMachinesInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedPowerManagementInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedSolarPanelInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_BaseMetaMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_IndividualMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_MetaMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_WrenchableInfoProvider;
import reforged.mods.blockhelper.addons.integrations.ic2.*;
import reforged.mods.blockhelper.addons.utils.Formatter;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class WailaCommonHandler {

    public static WeakHashMap<Integer, LiquidStack> FLUIDS = new WeakHashMap<Integer, LiquidStack>();
    public static List<IInfoProvider> INFO_PROVIDERS = new ArrayList<IInfoProvider>();

    public static void init() {
        INFO_PROVIDERS.add(new EUStorageInfoProvider());
        INFO_PROVIDERS.add(new CableInfoProvider());
        INFO_PROVIDERS.add(new BaseMachineInfoProvider());
        INFO_PROVIDERS.add(new IndividualInfoProvider());
        INFO_PROVIDERS.add(new GeneratorInfoProvider());
        INFO_PROVIDERS.add(new TransformerInfoProvider());
        INFO_PROVIDERS.add(new TeleporterInfoProvider());
        INFO_PROVIDERS.add(new CropInfoProvider());
        if (Loader.isModLoaded("AdvancedSolarPanel")) {
            INFO_PROVIDERS.add(new AdvancedSolarPanelInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedMachines")) {
            INFO_PROVIDERS.add(new AdvancedMachinesInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedPowerManagement")) {
            INFO_PROVIDERS.add(new AdvancedPowerManagementInfoProvider());
        }
        INFO_PROVIDERS.add(new WrenchableInfoProvider());
        if (Loader.isModLoaded("GregTech_Addon")) {
            INFO_PROVIDERS.add(new GT_BaseMetaMachineInfoProvider());
            INFO_PROVIDERS.add(new GT_MetaMachineInfoProvider());
            INFO_PROVIDERS.add(new GT_IndividualMachineInfoProvider());
            INFO_PROVIDERS.add(new GT_WrenchableInfoProvider());
        }
    }

    public static void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity != null) {
            for (IInfoProvider provider : INFO_PROVIDERS) {
                if (provider.canHandle(player)) {
                    provider.addInfo(helper, blockEntity, player);
                }
            }
        }
    }

    public static void addTankInfo(IWailaHelper helper, TileEntity blockEntity) {
        if (blockEntity instanceof ITankContainer) {
            loadTankData(helper, (ITankContainer) blockEntity);
        }
    }

    public static void loadTankData(IWailaHelper helper, ITankContainer tankContainer) {
        ILiquidTank[] tanks = tankContainer.getTanks(ForgeDirection.NORTH);
        for (int i = 0; i < tanks.length; i++) {
            ILiquidTank tank = tanks[i];
            LiquidStack fluid = tank.getLiquid();
            int capacity = tank.getCapacity();
            if (fluid != null) {
                if (fluid.amount > 0) {
                    loadTankInfo(helper, fluid, capacity);
                }
            }
        }
    }

    public static void loadTankInfo(IWailaHelper helper, LiquidStack fluidStack, int capacity) {
        FLUIDS.put(fluidStack.itemID, fluidStack);
        helper.add(new CommonFluidElement(fluidStack.amount, capacity,
                new ChatComponentTranslation("probe.info.fluid", fluidStack.asItemStack().getDisplayName(),
                        Formatter.formatNumber(fluidStack.amount, String.valueOf(fluidStack.amount).length() - 1),
                        Formatter.formatNumber(capacity, String.valueOf(capacity).length() - 1)).getFormattedText(),
                fluidStack.itemID));
    }
}
