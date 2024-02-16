package me.sting.client.product.utils;

import java.awt.Font;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import me.sting.client.product.managers.FontManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class FontUtil
{
    public FontManager fontManager;
    public FontManager fontManagerStyles;
    public FontManager fontManagerBold;
    public FontManager fontManagerItalic;
    public int[] colorCode;
    public float posX;
    public float posY;
    public float red;
    public float blue;
    public float green;
    public float alpha;
    public boolean randomStyle;
    public boolean boldStyle;
    public boolean italicStyle;
    public boolean underlineStyle;
    public boolean strikethroughStyle;
    
    public FontUtil(FontManager fontManager, FontManager fontManagerStyles, FontManager fontManagerBold, FontManager fontManagerItalic) {
        this.fontManager = fontManager;
        this.fontManagerStyles = fontManagerStyles;
        this.fontManagerBold = fontManagerBold;
        this.fontManagerItalic = fontManagerItalic;
        this.colorCode = new int[32];
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.red = 0.0f;
        this.blue = 0.0f;
        this.green = 0.0f;
        this.alpha = 0.0f;
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
        initializeColorCode();
    }
    
    private void initializeColorCode() {
        for (int n = 0; n < 32; ++n) {
            int n2 = (n >> 3 & 0x1) * 85;
            int n3 = (n >> 2 & 0x1) * 170 + n2;
            int n4 = (n >> 1 & 0x1) * 170 + n2;
            int n5 = (n >> 0 & 0x1) * 170 + n2;
            if (n == 6) {
                n3 += 85;
            }
            if (n >= 16) {
                n3 /= 4;
                n4 /= 4;
                n5 /= 4;
            }
            colorCode[n] = ((n3 & 0xFF) << 16 | (n4 & 0xFF) << 8 | (n5 & 0xFF));
        }
    }
    
    public static FontUtil renderFont(String s, int n, boolean b, boolean b2, boolean b3) {
        char[] array = new char[256];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (char) i;
        }
        FontManager fontManager = createFontManager(s, n, Font.PLAIN);
        FontManager fontManager2 = createFontManager(s, n, b ? Font.BOLD : Font.PLAIN);
        FontManager fontManager3 = createFontManager(s, n, b2 ? Font.ITALIC : Font.PLAIN);
        FontManager fontManager4 = createFontManager(s, n, b3 ? Font.BOLD + Font.ITALIC : Font.PLAIN);
        return new FontUtil(fontManager, fontManager2, fontManager3, fontManager4);
    }
    
    public FontManager getFontManager() {
        if (boldStyle && italicStyle) {
            return fontManagerStyles;
        } else if (boldStyle) {
            return fontManagerBold;
        } else if (italicStyle) {
            return fontManagerItalic;
        }
        return fontManager;
    }
    
    private static FontManager createFontManager(String fontName, int fontSize, int fontStyle) {
        Font font = new Font(fontName, fontStyle, fontSize);
        FontManager fontManager = new FontManager(font, true, true);
        fontManager.renderCharacter(new char[256]);
        fontManager.dynamicTexture();
        return fontManager;
    }

    public void resetStyles() {
        randomStyle = false;
        boldStyle = false;
        italicStyle = false;
        underlineStyle = false;
        strikethroughStyle = false;
    }
    
    public void renderStringAtPos(final String s, final boolean b) {
        final FontManager fontManager = this.getFontManager();
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableTexture2D();
        fontManager.bindTexture();
        GL11.glTexParameteri(3553, 10240, 9729);

        for (int n = 0; n < s.length(); ++n) {
            final char char1 = s.charAt(n);
            if (char1 == 167 && n + 1 < s.length()) {
                int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase(Locale.ENGLISH).charAt(n + 1));
                if (index >= 0) {
                    if (index < 16) {
                        this.randomStyle = false;
                        this.boldStyle = false;
                        this.strikethroughStyle = false;
                        this.underlineStyle = false;
                        this.italicStyle = false;
                        if (index >= 0 && index <= 15) {
                            index = 15;
                        }
                        if (b) {
                            index += 16;
                        }
                        final int n2 = this.colorCode[index];
                        GlStateManager.color((n2 >> 16) / 255.0f, (n2 >> 8 & 0xFF) / 255.0f, (n2 & 0xFF) / 255.0f, this.alpha);
                    } else {
                        switch (index) {
                            case 16:
                                this.randomStyle = true;
                                break;
                            case 17:
                                this.boldStyle = true;
                                break;
                            case 18:
                                this.strikethroughStyle = true;
                                break;
                            case 19:
                                this.underlineStyle = true;
                                break;
                            case 20:
                                this.italicStyle = true;
                                break;
                            case 21:
                                this.randomStyle = false;
                                this.boldStyle = false;
                                this.strikethroughStyle = false;
                                this.underlineStyle = false;
                                this.italicStyle = false;
                                GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                                break;
                            default:
                                break;
                        }
                    }
                    ++n;
                }
            } else {
                fontManager.bindTexture();
                this.drawCharacter(fontManager.statusSize(char1, this.posX, this.posY), fontManager);
            }
        }
        GlStateManager.bindTexture(0);
        GL11.glPopMatrix();
    }

    
    public void drawCharacter(final float n, final FontManager fontManager) {
        if (strikethroughStyle) {
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION);
            worldRenderer.pos(this.posX, this.posY + fontManager.fontHeight / 2, 0.0).endVertex();
            worldRenderer.pos(this.posX + n, this.posY + fontManager.fontHeight / 2, 0.0).endVertex();
            worldRenderer.pos(this.posX + n, this.posY + fontManager.fontHeight / 2 - 1.0f, 0.0).endVertex();
            worldRenderer.pos(this.posX, this.posY + fontManager.fontHeight / 2 - 1.0f, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        if (underlineStyle) {
            final Tessellator tessellator2 = Tessellator.getInstance();
            final WorldRenderer worldRenderer2 = tessellator2.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldRenderer2.begin(7, DefaultVertexFormats.POSITION);
            final int n2 = underlineStyle ? -1 : 0;
            worldRenderer2.pos(this.posX + n2, this.posY + fontManager.fontHeight, 0.0).endVertex();
            worldRenderer2.pos(this.posX + n, this.posY + fontManager.fontHeight, 0.0).endVertex();
            worldRenderer2.pos(this.posX + n, this.posY + fontManager.fontHeight - 1.0f, 0.0).endVertex();
            worldRenderer2.pos(this.posX + n2, this.posY + fontManager.fontHeight - 1.0f, 0.0).endVertex();
            tessellator2.draw();
            GlStateManager.enableTexture2D();
        }
        this.posX += (int) n;
    }

    
    public int drawString(String s, float n, float n2, int n3, boolean b, boolean b2) {
        GlStateManager.enableBlend();
        resetStyles();
        boolean centered = b ? b2 : false;
        float xPos = centered ? n - getStringWidth(s) / 2.0f + 12.0f : n + 1.0f;
        return Math.max(renderString(s, xPos, n2 + 0.7f, n3, true),
                        renderString(s, xPos, n2, n3, false));
    }
    
    public int getStringWidth(String s) {
        if (s == null) {
            return 0;
        }
        FontManager fontManager = getFontManager();
        int width = 0;
        for (int i = 0; i < s.length(); ++i) {
            width += (int) (fontManager.getCharacterWidth(s.charAt(i)) - 8.0f);
        }
        return width / 2;
    }
    
    public int renderString(String s, float n, float n2, int n3, boolean b) {
        if (s == null) {
            return 0;
        }
        if ((n3 & 0xFC000000) == 0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 0xFCFCFC) >> 2 | (n3 & 0xFF000000));
        }
        red = (n3 >> 16 & 0xFF) / 255.0f;
        blue = (n3 >> 8 & 0xFF) / 255.0f;
        green = (n3 & 0xFF) / 255.0f;
        alpha = (n3 >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(red, blue, green, alpha);
        posX = n * 2.0f;
        posY = n2 * 2.0f;
        renderStringAtPos(s, b);
        return (int) (posX / 4.0f);
    }
    
   
}
