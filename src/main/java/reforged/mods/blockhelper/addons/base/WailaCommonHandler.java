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
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public class WailaCommonHandler {

    public static final WailaCommonHandler INSTANCE = new WailaCommonHandler();

    public static WeakHashMap<Integer, LiquidStack> FLUIDS = new WeakHashMap<Integer, LiquidStack>();
    protected List<IInfoProvider> INFO_PROVIDERS = new ArrayList<IInfoProvider>();

    public void init() {
        registerProviders( // IC2
                new EUStorageInfoProvider(),
                new CableInfoProvider(),
                new BaseMachineInfoProvider(),
                new IndividualInfoProvider(),
                new GeneratorInfoProvider(),
                new TransformerInfoProvider(),
                new TeleporterInfoProvider(),
                new CropInfoProvider(),
                new WrenchableInfoProvider()
        );

        if (Loader.isModLoaded("AdvancedSolarPanel")) {
            registerProviders(new AdvancedSolarPanelInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedMachines")) {
            registerProviders(new AdvancedMachinesInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedPowerManagement")) {
            registerProviders(new AdvancedPowerManagementInfoProvider());
        }
        if (Loader.isModLoaded("GregTech_Addon")) {
            registerProviders( // GregTech-Addon
                    new GT_BaseMetaMachineInfoProvider(),
                    new GT_MetaMachineInfoProvider(),
                    new GT_IndividualMachineInfoProvider(),
                    new GT_WrenchableInfoProvider()
            );
        }
    }

    public void registerProviders(IInfoProvider... providers) {
        INFO_PROVIDERS.addAll(Arrays.asList(providers));
    }

    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity != null) {
            for (IInfoProvider provider : INFO_PROVIDERS) {
                if (provider.canHandle(player)) {
                    provider.addInfo(helper, blockEntity, player);
                }
            }
        }
    }

    public void addTankInfo(IWailaHelper helper, TileEntity blockEntity) {
        if (blockEntity instanceof ITankContainer) {
            loadTankData(helper, (ITankContainer) blockEntity);
        }
    }

    public void loadTankData(IWailaHelper helper, ITankContainer tankContainer) {
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

    public void loadTankInfo(IWailaHelper helper, LiquidStack fluidStack, int capacity) {
        FLUIDS.put(fluidStack.itemID, fluidStack);
        helper.add(new CommonFluidElement(fluidStack.amount, capacity,
                new ChatComponentTranslation("probe.info.fluid", fluidStack.asItemStack().getDisplayName(),
                        Formatter.formatNumber(fluidStack.amount, String.valueOf(fluidStack.amount).length() - 1),
                        Formatter.formatNumber(capacity, String.valueOf(capacity).length() - 1)).getFormattedText(),
                fluidStack.itemID));
    }
}
