package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.metatileentity.BaseTileEntity;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_MetaMachineInfoProvider extends InfoProvider {

    public static final GT_MetaMachineInfoProvider THIS = new GT_MetaMachineInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof BaseTileEntity) {
            BaseTileEntity baseTile = (BaseTileEntity) blockEntity;
            if (baseTile instanceof GT_TileEntityMetaID_Machine) {
                GT_TileEntityMetaID_Machine machine = (GT_TileEntityMetaID_Machine) blockEntity;
                if (machine.getEnergyCapacity() > 0) {
                    int storage = Math.min(machine.getStored(), machine.getEnergyCapacity());
                    bar(helper, storage, machine.getEnergyCapacity(), translate("info.energy", storage, machine.getEnergyCapacity()), ColorUtils.RED);
                }
                if (machine.maxEUInput() > 0) {
                    text(helper, tier(Helper.getTierFromEU(machine.maxEUInput())));
                    text(helper, maxIn(machine.maxEUInput()));
                }
                int maxOut = machine.maxEUOutput();
                if (maxOut > 0) {
                    text(helper, translate("info.generator.max_output", maxOut));
                }
            }
        }
    }
}
