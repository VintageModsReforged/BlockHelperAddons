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
import reforged.mods.blockhelper.addons.utils.RenderHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseProgressBarRenderer implements ITooltipRenderer {

    private final Map<List<String>, Tooltip> subTooltips = new HashMap<List<String>, Tooltip>();

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

        int width = BlockHelperAddons.BAR_WIDTH;

        boolean isStringOnly = strings.length > 4 && "1".equals(strings[4]);
        int height = isStringOnly ? 10 : 11;

        return new Dimension(width + offset, height);
    }

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
        int fluidStringId = Integer.parseInt(strings[6]);

        int maxLineWidth = BlockHelperAddons.BAR_WIDTH;
        int barHeight = 11;
        int textWidth = font.getStringWidth(text);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (!isStringOnly) {
            RenderHelper.INSTANCE.render(current, max, x, y, maxLineWidth, barHeight + 1, color, fluidStringId);
        }

        float boxX = x + 2;
        float boxWidth = maxLineWidth - 4;
        float boxHeight = font.FONT_HEIGHT * 1.5F;
        float drawOffsetX = centered ? (boxWidth - textWidth) / 2f : 0f;

        RenderHelper.drawScrollingString(font, text, boxX + drawOffsetX, y, boxWidth - drawOffsetX, boxHeight, ColorUtils.WHITE);
    }
}