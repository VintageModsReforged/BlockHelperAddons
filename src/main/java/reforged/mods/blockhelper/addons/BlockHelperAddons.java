package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import mods.vintage.core.helpers.ConfigHelper;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import reforged.mods.blockhelper.addons.base.WailaCommonHandler;
import reforged.mods.blockhelper.addons.utils.ToolIntegrationHelper;
import reforged.mods.blockhelper.addons.utils.ToolIntegrationRecipe;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

@Mod(modid = BlockHelperAddons.ID, useMetadata = true)
public class BlockHelperAddons {

    public static final String ID = "BlockHelperAddons";

    public Configuration CONFIG;
    public static String[] LANGS;
    public static int BAR_WIDTH;

    public BlockHelperAddons() {
        CONFIG = ConfigHelper.getConfigFor(ID);
        CONFIG.load();
        LANGS = ConfigHelper.getLocalizations(CONFIG, new String[] {"en_US", "ru_RU"}, ID);
        BAR_WIDTH = ConfigHelper.getInt(CONFIG, "general", "barWidth", 135, Integer.MAX_VALUE, 135, "Increase this if you don't like scrolling text.");
        CONFIG.save();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        WailaCommonHandler.INSTANCE.init();
        GameRegistry.addRecipe(new ToolIntegrationRecipe());
        try {
            Method register = Class.forName("mod_BlockHelper").getMethod("registerPlugin",
                    Class.forName("mcp.mobius.waila.api.IWailaPlugin"));
            register.invoke(null, new WailaPluginHandler());
        } catch (Throwable ignored) {}
    }

    @ForgeSubscribe
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (IC2.platform.isSimulating()) {
            EntityPlayer player = e.entityPlayer;
            if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                ItemStack helmet = player.getHeldItem();
                if (helmet != null) {
                    if (ToolIntegrationHelper.isHelmet(helmet)) {
                        List<String> tools = ToolIntegrationHelper.getInstalledTools(helmet);
                        if (!tools.isEmpty()) {
                            String first = tools.get(0);
                            ToolIntegrationHelper.Tools tool = ToolIntegrationHelper.Tools.valueOf(first.toUpperCase(Locale.ROOT));
                            ItemStack extractedTool = tool.toolInstance.get();
                            if (extractedTool != null) {
                                ToolIntegrationHelper.removeTool(helmet, ToolIntegrationHelper.Tools.valueOf(first.toUpperCase(Locale.ROOT)));
                                EntityItem drop = new EntityItem(player.worldObj, player.posX, player.posY + 1, player.posZ, extractedTool.copy());
                                player.worldObj.spawnEntityInWorld(drop);
                                player.addChatMessage(Translator.YELLOW.format("message.chat.extracted", Translator.AQUA.format(tool.description)));
                                e.setCanceled(true);
                            }
                        } else {
                            player.addChatMessage(Translator.RED.format("message.chat.extracted.none"));
                        }
                    }
                }
            }
        }
    }
}
