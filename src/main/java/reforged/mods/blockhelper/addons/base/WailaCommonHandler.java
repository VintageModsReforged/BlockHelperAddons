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
import reforged.mods.blockhelper.addons.integrations.AdvancedPowerManagementInfoProvider;
import reforged.mods.blockhelper.addons.integrations.CompactSolarPanelsInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_BaseMetaMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_IndividualMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GT_MetaMachineInfoProvider;
import reforged.mods.blockhelper.addons.integrations.ic2.*;
import reforged.mods.blockhelper.addons.utils.Formatter;
import reforged.mods.blockhelper.addons.utils.interfaces.IInfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WailaCommonHandler {

    public static final WailaCommonHandler INSTANCE = new WailaCommonHandler();

    protected List<IInfoProvider> INFO_PROVIDERS = new ArrayList<IInfoProvider>();
    protected List<IInfoProvider> ADDON_PROVIDERS = new ArrayList<IInfoProvider>();

    public void init() {
        registerProviders( // IC2
                EUStorageInfoProvider.THIS,
                CableInfoProvider.THIS,
                BaseMachineInfoProvider.THIS,
                IndividualInfoProvider.THIS,
                GeneratorInfoProvider.THIS,
                TransformerInfoProvider.THIS,
                TeleporterInfoProvider.THIS,
                CropInfoProvider.THIS,
                PersonalInfoProvider.THIS,
                BarrelInfoProvider.THIS
        );
        if (Loader.isModLoaded("AdvancedPowerManagement")) {
            registerAddonProviders(AdvancedPowerManagementInfoProvider.THIS);
        }
        if (Loader.isModLoaded("CompactSolars")) {
            registerAddonProviders(CompactSolarPanelsInfoProvider.THIS);
        }
        if (Loader.isModLoaded("GregTech_Addon")) {
            registerAddonProviders( // GregTech-Addon
                    GT_BaseMetaMachineInfoProvider.THIS,
                    GT_MetaMachineInfoProvider.THIS,
                    GT_IndividualMachineInfoProvider.THIS
            );
        }
    }

    public void registerProviders(IInfoProvider... providers) {
        INFO_PROVIDERS.addAll(Arrays.asList(providers));
    }

    public void registerAddonProviders(IInfoProvider... providers) {
        ADDON_PROVIDERS.addAll(Arrays.asList(providers));
    }

    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity != null) {
            // register main providers
            for (IInfoProvider provider : INFO_PROVIDERS) {
                if (provider.canHandle(player)) {
                    provider.addInfo(helper, blockEntity, player);
                }
            }
            // register addon providers
            for (IInfoProvider provider : ADDON_PROVIDERS) {
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
        helper.add(new CommonFluidElement(fluidStack.amount, capacity,
                new ChatComponentTranslation("probe.info.fluid", fluidStack.asItemStack().getDisplayName(),
                        Formatter.formatNumber(fluidStack.amount, String.valueOf(fluidStack.amount).length() - 1),
                        Formatter.formatNumber(capacity, String.valueOf(capacity).length() - 1)).getFormattedText(),
                fluidStack.extra.getString("LiquidName")));
    }
}
