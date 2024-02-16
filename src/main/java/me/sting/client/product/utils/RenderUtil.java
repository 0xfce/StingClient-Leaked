package me.sting.client.product.utils;

import net.minecraft.client.*;
import me.sting.client.product.utils.timers.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RenderUtil
{
    protected Minecraft mc;
    public TimerUtil timer;
    
    public RenderUtil() {
        this.mc = Minecraft.getMinecraft();
        this.timer = new TimerUtil();
    }
    
    public void renderBlock(final BlockPos blockPos, final int n, final boolean b) {
        if (lIIIIlIIlII(blockPos)) {
            final double n2 = blockPos.getX() - this.mc.getRenderManager().viewerPosX;
            final double n3 = blockPos.getY() - this.mc.getRenderManager().viewerPosY;
            final double n4 = blockPos.getZ() - this.mc.getRenderManager().viewerPosZ;
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            final float n5 = (n >> 16 & 0xFF) / 255.0f;
            final float n6 = (n >> 8 & 0xFF) / 255.0f;
            final float n7 = (n & 0xFF) / 255.0f;
            GL11.glColor4d((double)n5, (double)n6, (double)n7, (double)((n >> 24 & 0xFF) / 255.0f));
            RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(n2, n3, n4, n2 + 1.0, n3 + 1.0, n4 + 1.0));
            if (lIIIIlIIllI(b ? 1 : 0)) {
                this.drawShaderBlock(new AxisAlignedBB(n2, n3, n4, n2 + 1.0, n3 + 1.0, n4 + 1.0), n5, n6, n7);
            }
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
    }
    
    public void drawShaderBlock(final AxisAlignedBB axisAlignedBB, final float n, final float n2, final float n3) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        getInstance.draw();
    }
    
    public void drawPolygon(final double n, final double n2, final double n3, final int n4, final int n5, final boolean b) {
        if (lIIIIlIIlll(n4, 3)) {
            return;
        }
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        getWorldRenderer.begin(6, DefaultVertexFormats.POSITION);
        for (int n6 = 0; lIIIIlIIlll(n6, n4); ++n6) {
            final double n7 = 6.283185307179586 * n6 / n4 + Math.toRadians(lIIIIlIIllI(b ? 1 : 0) ? 360.0 : 180.0);
            getWorldRenderer.pos(n + Math.sin(n7) * n3, n2 + Math.cos(n7) * n3, 0.0).endVertex();
            this.colorHex(n5);
        }
        getInstance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public void renderPlayer(final Entity entity, final int n, final float n2) {
        if (lIIIIlIlIII(entity)) {
            return;
        }
        final double n3 = entity.lastTickPosX + entity.posX - entity.lastTickPosX * this.timer.getTimer().timerSpeed - this.mc.getRenderManager().viewerPosX;
        final double n4 = entity.getEyeHeight() + entity.lastTickPosY + entity.posY - entity.lastTickPosY * this.timer.getTimer().timerSpeed - this.mc.getRenderManager().viewerPosY;
        final double n5 = entity.lastTickPosZ + entity.posZ - entity.lastTickPosZ * this.timer.getTimer().timerSpeed - this.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n2);
        this.colorHex(n);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0, (double)this.mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(n3, n4, n5);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void drawArrow(float n, float n2, final boolean b, final int n3) {
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.3, 1.3);
        if (lIIIIlIIllI(b ? 1 : 0)) {
            n2 -= 1.5f;
            n += 2.0f;
        }
        n /= 1.3f;
        n2 /= 1.3f;
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        this.colorHex(n3);
        GL11.glLineWidth(2.0f);
        if (lIIIIlIIllI(b ? 1 : 0)) {
            GL11.glBegin(1);
            GL11.glVertex2d((double)n, (double)n2);
            GL11.glVertex2d((double)(n + 2.0f), (double)(n2 + 2.0f));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2d((double)(n + 2.0f), (double)(n2 + 2.0f));
            GL11.glVertex2d((double)n, (double)(n2 + 4.0f));
            GL11.glEnd();
        }
        else {
            GL11.glBegin(1);
            GL11.glVertex2d((double)n, (double)n2);
            GL11.glVertex2d((double)(n + 2.0f), (double)(n2 + 2.0f));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2d((double)(n + 2.0f), (double)(n2 + 2.0f));
            GL11.glVertex2d((double)(n + 4.0f), (double)n2);
            GL11.glEnd();
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public void drawTexturedRectangle(final ResourceLocation resourceLocation, final double n, final double n2, final float n3, final float n4, final int n5) {
//        try {
        	GL11.glPushMatrix();
            GlStateManager.enableBlend();
//            System.out.println(resourceLocation.getResourcePath());
//            if(resourceLocation != null)
//            	if(Minecraft.getMinecraft() != null)
//            		if(Minecraft.getMinecraft().getTextureManager() != null)
            			Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
//            		else System.out.println("texture is null");
//            	else System.out.println("mc is null");
//            else System.out.println("NULL!");
            this.colorHex(n5);
            GL11.glBegin(7);
            GL11.glTexCoord2d(1.0, 1.0);
            GL11.glVertex2d(n, n2);
            GL11.glTexCoord2d(1.0, 2.0);
            GL11.glVertex2d(n, n2 + n4);
            GL11.glTexCoord2d(2.0, 2.0);
            GL11.glVertex2d(n + n3, n2 + n4);
            GL11.glTexCoord2d(2.0, 1.0);
            GL11.glVertex2d(n + n3, n2);
            GL11.glEnd();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
//        } catch(Exception ex) {
//        	ex.printStackTrace();
//        }
    	
    }
    
    public void drawCircle(final double n, final double n2, final int n3, final int n4, final int n5) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(9, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(n, n2, 0.0).endVertex();
        this.colorHex(n5);
        for (int n6 = 0; lIIIIlIlIIl(n6, n4); ++n6) {
            getWorldRenderer.pos(n + Math.sin(6.283185307179586 * n6 / n4 + Math.toRadians(180.0)) * n3, n2 + Math.cos(6.283185307179586 * n6 / n4 + Math.toRadians(180.0)) * n3, 0.0).endVertex();
        }
        getInstance.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public void colorHex(final int n) {
        GlStateManager.color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    private static boolean lIIIIlIIlll(final int n, final int n2) {
        return n < n2;
    }
    
    private static boolean lIIIIlIlIIl(final int n, final int n2) {
        return n <= n2;
    }
    
    private static boolean lIIIIlIIlII(final Object o) {
        return o != null;
    }
    
    private static boolean lIIIIlIlIII(final Object o) {
        return o == null;
    }
    
    private static boolean lIIIIlIIllI(final int n) {
        return n != 0;
    }
}
