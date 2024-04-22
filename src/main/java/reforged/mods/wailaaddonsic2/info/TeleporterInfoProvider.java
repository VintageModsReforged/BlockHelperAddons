package reforged.mods.wailaaddonsic2.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.wailaaddonsic2.TextColor;
import reforged.mods.wailaaddonsic2.i18n.I18n;

public class TeleporterInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityTeleporter) {
            TileEntityTeleporter tp = (TileEntityTeleporter) tile;
            boolean hasTarget = tp.targetSet;
            infoHolder.add(TextColor.GOLD.format(I18n.format("info.teleporter.target.set", hasTarget ? TextColor.GREEN.format(I18n.format(true)) : TextColor.RED.format(I18n.format(false)))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.teleporter.target", tp.targetX, tp.targetY, tp.targetZ)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
