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
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.RenderHelper;
import reforged.mods.blockhelper.addons.utils.TooltipData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseProgressBarRenderer implements ITooltipRenderer {

    private final Map<List<String>, Tooltip> subTooltips = new HashMap<List<String>, Tooltip>();

    int offset = 4;
    int barWidth = 135;

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

        TooltipData data = TooltipData.parse(strings);
        int height = data.isStringOnly ? 10 : 11;

        return new Dimension(barWidth + offset, height);
    }

    @Override
    public void draw(String[] strings, ICommonAccessor accessor, int x, int y) {
        if (strings == null || strings.length < 7) return;

        TooltipData tooltip = TooltipData.parse(strings);

        int current = tooltip.current;
        int max = tooltip.max;
        int color = tooltip.color;
        String text = tooltip.text;
        boolean isStringOnly = tooltip.isStringOnly;
        boolean centered = tooltip.centered;
        int fluidStringId = tooltip.fluidId;

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        int maxLineWidth = barWidth;
        int barHeight = 11;
        int textWidth = font.getStringWidth(text);
        int textX = x;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (!isStringOnly) {
            RenderHelper.INSTANCE.render(current, max, x, y, maxLineWidth, barHeight + 1, color, fluidStringId);
            textX += (maxLineWidth - textWidth) / 2 + 1;
        } else if (centered) {
            textX += (maxLineWidth - textWidth) / 2 + 1;
        }

        float centerY = y + 2 + (float) font.FONT_HEIGHT / 2;
        RenderHelper.drawScrollingString(font, text, textX + textWidth / 2F, centerY, maxLineWidth + 1, font.FONT_HEIGHT * 1.5F, ColorUtils.WHITE);
    }
}