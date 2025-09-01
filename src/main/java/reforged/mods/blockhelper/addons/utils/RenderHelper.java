package reforged.mods.blockhelper.addons.utils;

import mcp.mobius.waila.overlay.OverlayConfig;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraftforge.liquids.LiquidStack;
import org.lwjgl.opengl.GL11;

public class RenderHelper {

    public static final RenderHelper INSTANCE = new RenderHelper();

    private static final ScissorsStack STACK = new ScissorsStack();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawScrollingString(FontRenderer font, String text, float x, float y, float width, float height, int color) {
        int textWidth = font.getStringWidth(text);
        int clipX = MathHelper.floor_float(x);
        int clipY = MathHelper.floor_float(y);
        int clipW = MathHelper.ceiling_float_int(width);
        int clipH = MathHelper.ceiling_float_int(height);
        float drawY = y + (height - font.FONT_HEIGHT) / 2F;

        if (textWidth > width) {
            float scrollOffset = getScrollOffset(width, textWidth);
            float drawX = x - scrollOffset;

            pushScissors(clipX, clipY, clipW, clipH);
            font.drawStringWithShadow(text, (int) drawX + 2, (int) drawY, color);
            popScissors();
        } else {
            font.drawStringWithShadow(text, (int) x + 2, (int) drawY, color);
        }
    }

    public static void pushScissors(int x, int y, int width, int height) {
        pushScissors(new ScreenRectangle(x, y, width, height));
    }

    public static void pushScissors(ScreenRectangle rect) {
        STACK.push(rect);
        applyScissors(rect);
    }

    public static void popScissors() {
        applyScissors(STACK.pop());
    }

    private static void applyScissors(ScreenRectangle rect) {
        if (rect == null) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            return;
        }

        ScaledResolution resolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        double scaleX = (double) mc.displayWidth / resolution.getScaledWidth();
        double scaleY = (double) mc.displayHeight / resolution.getScaledHeight();

        float overlayScale = OverlayConfig.scale;

        int x = (int) (rect.getX() * overlayScale * scaleX);
        int y = (int) ((resolution.getScaledHeight() - (rect.getY() + rect.getHeight()) * overlayScale) * scaleY);
        int w = (int) (rect.getWidth() * overlayScale * scaleX);
        int h = (int) (rect.getHeight() * overlayScale * scaleY);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, y, w, h);
    }

    private static float getScrollOffset(float width, int textWidth) {
        float scrollRange = textWidth - width + 4F;
        float scrollSpeed = 5F;

        long now = System.currentTimeMillis();
        float totalScrollTime = scrollRange / scrollSpeed;
        float cycleTime = totalScrollTime * 2F;

        float timeInCycle = (now % (long) (cycleTime * 1000L)) / 1000F;
        boolean reverse = timeInCycle >= totalScrollTime;
        float timeInDirection = reverse ? (timeInCycle - totalScrollTime) : timeInCycle;

        float offset = timeInDirection * scrollSpeed;
        return reverse ? (scrollRange - offset) : offset;
    }

    /**
     * Modified copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/apiimpl/client/ElementProgressRender.java#L15">ElementProgressRender#render</a>
     */
    public void render(long current, long max, int x, int y, int w, int h, int mainColor, int stringId) {
        drawThickBeveledBox(x, y, x + w + 1, y + h, 1, ColorUtils.WHITE, ColorUtils.WHITE, ColorUtils.doubleDarker(mainColor));
        if (stringId != 0) {
            LiquidStack stack = new LiquidStack(stringId, 1000);
            renderTank(Minecraft.getMinecraft(), x, y, w, h, mainColor, (int) current, (int) max, stack);
        } else {
            if (current > 0 && max > 0) {
                int dx = (int) Math.min((current * (w - 2) / max), w - 2);
                int secondColor = ColorUtils.darker(mainColor);
                if (mainColor == secondColor) {
                    if (dx > 0) {
                        drawThickBeveledBox(x + 1, y + 1, x + dx + 1, y + h - 1, 1, mainColor, mainColor, mainColor);
                    }
                } else {
                    for (int xx = x + 1; xx <= x + dx + 1; xx++) {
                        int color = (xx & 1) == 0 ? mainColor : secondColor;
                        drawVerticalLine(xx, y + 1, y + h - 1, color);
                    }
                }
            }
        }
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L209">RenderHelper#drawVerticalLine</a>
     * */
    public void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L275">RenderHelper#drawThickBeveledBox</a>
     * */
    public void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void renderTank(Minecraft mc, int x, int y, int width, int height, int color, int current, int capacity, LiquidStack liquid) {
        if (liquid == null || liquid.itemID <= 0 || capacity <= 0 || current <= 0) return;
        String texture;
        float u = 0;
        float v = 0;
        if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID] != null) {
            texture = "/terrain.png";
        } else if (liquid.itemID < Item.itemsList.length && Item.itemsList[liquid.itemID] != null) {
            texture = "/gui/items.png";
        } else {
            return;
        }

        mc.renderEngine.bindTexture(mc.renderEngine.getTexture(texture));

        GL11.glColor4f(
                ((color >> 16) & 255) / 255.0F,
                ((color >> 8) & 255) / 255.0F,
                (color & 255) / 255.0F,
                ((color >> 24) & 255) / 255.0F
        );

        int fillWidth = (int)(((double) current / capacity) * width);
        int drawX = x;

        while (fillWidth > 0) {
            int drawWidth = Math.min(16, fillWidth);
            drawTexturedModalRect(drawX, y, u, v, drawWidth, height);
            drawX += drawWidth;
            fillWidth -= drawWidth;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawTexturedModalRect(int x, int y, float u, float v, int width, int height) {
        float zLevel = 0.01f;
        float textureSize = 256.0F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.addVertexWithUV(x, y + height, zLevel, u / textureSize, (v + height) / textureSize);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) / textureSize, (v + height) / textureSize);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + width) / textureSize, v / textureSize);
        tessellator.addVertexWithUV(x, y, zLevel, u / textureSize, v / textureSize);
        tessellator.draw();
    }
}
