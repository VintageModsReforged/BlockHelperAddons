package reforged.mods.blockhelper.addons.base.tooltiprenderers;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import mcp.mobius.waila.api.impl.TipList;
import mcp.mobius.waila.overlay.RayTracing;
import mcp.mobius.waila.overlay.Tooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Dimension;
import reforged.mods.blockhelper.addons.BlockHelperAddons;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.GuiHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseProgressBarRenderer implements ITooltipRenderer {

    private final Map<List<String>, Tooltip> subTooltips = new HashMap<List<String>, Tooltip>();
    private final Map<Tooltip, Integer> tooltipSize = new HashMap<Tooltip, Integer>();

    int offset = 4;

    @Override
    public Dimension getSize(String[] strings, ICommonAccessor accessor) {
        if (strings == null || strings.length == 0) {
            return new Dimension();
        }

        List<String> key = Arrays.asList(strings);
        Tooltip tooltip = this.subTooltips.get(key);

        if (tooltip == null) {
            TipList<String, String> list = new TipList<String, String>();
            list.addAll(key);
            tooltip = new Tooltip(list, RayTracing.instance().getTargetStack());
            this.subTooltips.put(key, tooltip);
        }

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        // Calculate max width of all lines
        int maxLineWidth = 0;
        for (String str : strings) {
            int width = font.getStringWidth(str);
            if (width > maxLineWidth) {
                maxLineWidth = width;
            }
        }

        tooltipSize.put(tooltip, maxLineWidth + offset);

        boolean isStringOnly = strings.length > 4 && "1".equals(strings[4]);
        int height = isStringOnly ? 10 : 11;

        return new Dimension(Math.max(BlockHelperAddons.barMaxWidth, maxLineWidth + offset), height);
    }

    /**
     * Tooltip Builder
     * param 0 - current
     * param 1 - max
     * param 2 - color
     * param 3 - text
     * param 4 - string only, 0 - false, 1 - true
     * param 5 - centered, 0 - false, 1 - true
     * param 6 - fluid id
     * */

    @Override
    public void draw(String[] strings, ICommonAccessor accessor, int x, int y) {
        if (strings == null || strings.length < 7) return;

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        int current = Integer.parseInt(strings[0]);
        int max = Integer.parseInt(strings[1]);
        int color = Integer.parseInt(strings[2]);
        String text = strings[3];
        boolean isStringOnly = "1".equals(strings[4]);
        boolean centered = "1".equals(strings[5]);
        String fluidStringId = strings[6];

        int maxLineWidth = 0;

        Tooltip tooltips = this.subTooltips.get(Arrays.asList(strings));
        if (tooltips != null) {
            maxLineWidth = tooltipSize.get(tooltips);
        }

        maxLineWidth = Math.max(BlockHelperAddons.barMaxWidth, maxLineWidth);

        int barHeight = 11;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int textX = x;
        if (!isStringOnly) {
            // Draw the background bar using full width
            GuiHelper.INSTANCE.render(current, max, x, y, maxLineWidth, barHeight + 1, color, fluidStringId);

            // Center the text inside the full-width bar
            textX += maxLineWidth / 2 - font.getStringWidth(text) / 2 + 1;
        }

        if (centered && isStringOnly) {
            textX += maxLineWidth / 2 - font.getStringWidth(text) / 2 + 1;
        }

        font.drawStringWithShadow(text, textX, y + 2, ColorUtils.WHITE);
    }
}