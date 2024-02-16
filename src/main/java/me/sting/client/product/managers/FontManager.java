package me.sting.client.product.managers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import me.sting.client.product.storage.CharacterData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class FontManager
{
    public HashMap map;
    public Font font;
    public BufferedImage image;
    public DynamicTexture dynamic;
    public boolean antiAlias;
    public boolean fractionalMetrics;
    public int fontHeight;
    public int size;
    
    public FontManager(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        this.map = new HashMap();
        this.font = null;
        this.image = null;
        this.dynamic = null;
        this.antiAlias = false;
        this.fractionalMetrics = false;
        this.fontHeight = -1;
        this.size = 0;
        this.font = font;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
    }
    
    public void renderCharacter(final char[] array) {
        double width = -1.0;
        double height = -1.0;
        final FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), this.antiAlias, this.fractionalMetrics);
        
        for (int var8 = 0; var8 < array.length; ++var8) {
            char var9 = array[var8];
            Rectangle2D var10 = this.font.getStringBounds(Character.toString(var9), fontRenderContext);

            if (width < 0.0 || width > var10.getWidth()) {
                width = var10.getWidth();
            }

            if (height < 0.0 || height > var10.getHeight()) {
                height = var10.getHeight();
            }
        }

        final double n2 = width + 2.0;
        final double n3 = height + 2.0;
        this.size = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(n2 * n2 * array.length) / n2), Math.ceil(Math.sqrt(n3 * n3 * array.length) / n3)) * Math.max(n2, n3)) + 1;
        this.image = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = this.image.createGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.size, this.size);
        graphics2D.setColor(Color.white);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int height2 = 0;
        int x = 0;
        int y = 1;
        
        for (int n4 = 0; n4 < array.length; ++n4) {
            final char c = array[n4];
            final CharacterData characterData = new CharacterData();
            final Rectangle2D stringBounds2 = fontMetrics.getStringBounds(Character.toString(c), graphics2D);
            characterData.setWidth(stringBounds2.getBounds().width + 8);
            characterData.setHeight(stringBounds2.getBounds().height);
            
            if (n4 + characterData.height >= this.size) {
                throw new IllegalStateException("Characters not fit");
            }
            
            if (x + characterData.width >= this.size) {
                x = 0;
                y += height2;
                height2 = 0;
            }
            
            characterData.setX(x);
            characterData.setY(y);
            
            if (characterData.height >= this.fontHeight) {
                this.fontHeight = characterData.height;
            }
            
            if (characterData.height >= height2) {
                height2 = characterData.height;
            }
            
            graphics2D.drawString(Character.toString(c), x + 2, y + fontMetrics.getAscent());
            x += characterData.width;
            this.map.put(c, characterData);
        }
    }

    public float statusSize(final char c, final float n, final float n2) {
        CharacterData characterData = (CharacterData) this.map.get(c);
        if (characterData == null) {
            characterData = (CharacterData) this.map.get('@');
        }
        final float n3 = characterData.x / this.size;
        final float n4 = characterData.y / this.size;
        final float n5 = characterData.width / this.size;
        final float n6 = characterData.height / this.size;
        final float n7 = characterData.width;
        final float n8 = characterData.height;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(n3 + n5, n4);
        GL11.glVertex2f(n + n7, n2);
        GL11.glTexCoord2f(n3, n4);
        GL11.glVertex2f(n, n2);
        GL11.glTexCoord2f(n3, n4 + n6);
        GL11.glVertex2f(n, n2 + n8);
        GL11.glTexCoord2f(n3 + n5, n4 + n6);
        GL11.glVertex2f(n + n7, n2 + n8);
        GL11.glEnd();
        return n7 - 8.0f;
    }

    
    public void dynamicTexture() {
        this.dynamic = new DynamicTexture(this.image);
    }
    
    public void bindTexture() {
        GlStateManager.bindTexture(this.dynamic.getGlTextureId());
    }
    
    public float getCharacterWidth(final char c) {
        return (float)((CharacterData)this.map.get(Character.valueOf(c))).width;
    }
}
