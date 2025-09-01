package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.personal.TileEntityTradeOMat;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class PersonalInfoProvider extends InfoProvider {

    public static final PersonalInfoProvider THIS = new PersonalInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IPersonalBlock) {
            boolean canAccess = ((IPersonalBlock) blockEntity).canAccess(player);
            text(helper, translate(Translator.GOLD, "info.personal.access", Translator.RESET.formattedBoolean(canAccess)));
        }
        String owner = "";
        if (blockEntity instanceof TileEntityPersonalChest) {
            owner = ((TileEntityPersonalChest) blockEntity).owner;
        } else if (blockEntity instanceof TileEntityTradeOMat) {
            owner = ((TileEntityTradeOMat) blockEntity).owner;
        } else if (blockEntity instanceof TileEntityEnergyOMat) {
            owner = ((TileEntityEnergyOMat) blockEntity).owner;
        }
        if (!"".equals(owner)) {
            text(helper, translate(Translator.LIGHT_PURPLE, "info.personal.owner", Translator.AQUA.literal(owner)));
        }
    }
}
