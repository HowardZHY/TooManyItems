import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TMIDrawing
{
    public static final int TEXT_SCALE_AGATE = 1;
    public static final int TEXT_SCALE_NORMAL = 2;
    public static final int TEXT_SCALE_BIG = 4;
    public static final int TEXT_HEIGHT = 8;
    public static final int WHITE = -1;
    public static final int BLACK = -16777216;
    public static final int HIGHLIGHT = 587202559;
    private static int textureID = -1;
    private static RenderItem drawItems = Minecraft.getMinecraft().getRenderItem();
    private static ItemStack invalid = new ItemStack((Item)((Item)Item.itemRegistry.getObject(new ResourceLocation("barrier"))));

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_)
    {
        drawText(p_drawText_0_, p_drawText_1_, p_drawText_2_, -1, 2);
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_, int p_drawText_3_)
    {
        drawText(p_drawText_0_, p_drawText_1_, p_drawText_2_, p_drawText_3_, 2);
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, String p_drawText_2_, int p_drawText_3_, int p_drawText_4_)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_drawText_0_, (float)p_drawText_1_, 0.0F);

        if (p_drawText_4_ != 2)
        {
            float var5 = (float)p_drawText_4_ / 2.0F;
            GL11.glScalef(var5, var5, 1.0F);
        }

        Minecraft.getMinecraft().fontRendererObj.drawString(p_drawText_2_, 0, 0, p_drawText_3_);
        GL11.glPopMatrix();
    }

    public static void drawText(int p_drawText_0_, int p_drawText_1_, List<String> p_drawText_2_, int p_drawText_3_, int p_drawText_4_)
    {
        for (Iterator var5 = p_drawText_2_.iterator(); var5.hasNext(); p_drawText_1_ += 8 * p_drawText_4_ / 2 + p_drawText_4_)
        {
            String var6 = (String)var5.next();
            drawText(p_drawText_0_, p_drawText_1_, var6, p_drawText_3_, p_drawText_4_);
        }
    }

    public static void drawTextCentered(int p_drawTextCentered_0_, int p_drawTextCentered_1_, int p_drawTextCentered_2_, int p_drawTextCentered_3_, String p_drawTextCentered_4_, int p_drawTextCentered_5_, int p_drawTextCentered_6_)
    {
        int var7 = getTextWidth(p_drawTextCentered_4_) * p_drawTextCentered_6_ / 2;
        p_drawTextCentered_0_ += (p_drawTextCentered_2_ - var7) / 2;
        p_drawTextCentered_1_ += (p_drawTextCentered_3_ - 8 * p_drawTextCentered_6_ / 2) / 2;
        drawText(p_drawTextCentered_0_, p_drawTextCentered_1_, p_drawTextCentered_4_, p_drawTextCentered_5_, p_drawTextCentered_6_);
    }

    public static int getTextWidth(String p_getTextWidth_0_)
    {
        return getTextWidth(p_getTextWidth_0_, 2);
    }

    public static int getTextWidth(String p_getTextWidth_0_, int p_getTextWidth_1_)
    {
        if (p_getTextWidth_0_ != null && !p_getTextWidth_0_.equals(""))
        {
            int var2 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(p_getTextWidth_0_);

            if (p_getTextWidth_1_ != 2)
            {
                var2 = (int)((float)var2 * ((float)p_getTextWidth_1_ / 2.0F));
            }

            return var2;
        }
        else
        {
            return 0;
        }
    }

    public static int getTextWidth(List<String> p_getTextWidth_0_)
    {
        int var1 = 0;
        Iterator var2 = p_getTextWidth_0_.iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            int var4 = getTextWidth(var3);

            if (var4 > var1)
            {
                var1 = var4;
            }
        }

        return var1;
    }

    public static String cutTextToWidth(String p_cutTextToWidth_0_, int p_cutTextToWidth_1_)
    {
        while (p_cutTextToWidth_0_ != null && p_cutTextToWidth_0_.length() > 0 && getTextWidth(p_cutTextToWidth_0_) > p_cutTextToWidth_1_)
        {
            p_cutTextToWidth_0_ = p_cutTextToWidth_0_.substring(0, p_cutTextToWidth_0_.length() - 1);
        }

        return p_cutTextToWidth_0_;
    }

    public static void drawTooltip(int p_drawTooltip_0_, int p_drawTooltip_1_, List<String> p_drawTooltip_2_)
    {
        int var3 = getTextWidth(p_drawTooltip_2_);
        int var4 = p_drawTooltip_2_.size() * 10 + (p_drawTooltip_2_.size() > 1 ? 2 : 0);
        int var5 = var3 + 6;
        int var6 = var4 + 6;
        int var7 = Minecraft.getMinecraft().currentScreen.width;
        int var8 = Minecraft.getMinecraft().currentScreen.height;
        int var9 = p_drawTooltip_0_ + 12;
        int var10 = p_drawTooltip_1_ - 15;

        if (var9 + var5 > var7)
        {
            var9 = p_drawTooltip_0_ - var5 - 12;
        }

        if (var10 + var6 > var8)
        {
            var10 = var8 - var6 - 2;
        }

        if (var10 < 2)
        {
            var10 = 2;
        }

        drawTooltipPanel(var9, var10, var5, var6);
        int var11 = var9 + 3;
        int var12 = var10 + 4;
        boolean var13 = true;

        for (Iterator var14 = p_drawTooltip_2_.iterator(); var14.hasNext(); var12 += 10)
        {
            String var15 = (String)var14.next();
            drawText(var11, var12, var15, -1, 2);

            if (var13)
            {
                var12 += 2;
                var13 = false;
            }
        }
    }

    public static void drawIcon(int p_drawIcon_0_, int p_drawIcon_1_, int p_drawIcon_2_, int p_drawIcon_3_, int p_drawIcon_4_, int p_drawIcon_5_)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTMITexture();
        Minecraft.getMinecraft().currentScreen.drawTexturedModalRect(p_drawIcon_0_, p_drawIcon_1_, p_drawIcon_2_, p_drawIcon_3_, p_drawIcon_4_, p_drawIcon_5_);
    }

    public static void drawItem(int p_drawItem_0_, int p_drawItem_1_, ItemStack p_drawItem_2_)
    {
        drawItem(p_drawItem_0_, p_drawItem_1_, p_drawItem_2_, false);
    }

    public static void drawItem(int p_drawItem_0_, int p_drawItem_1_, ItemStack p_drawItem_2_, boolean p_drawItem_3_)
    {
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        try
        {
            drawItems.renderItemAndEffectIntoGUI(p_drawItem_2_, p_drawItem_0_, p_drawItem_1_);

            if (p_drawItem_3_)
            {
                ;
            }
        }
        catch (Throwable var5)
        {
            drawItems.renderItemAndEffectIntoGUI(p_drawItem_2_, p_drawItem_0_, p_drawItem_1_);
        }
    }

    public static void fillRect(int p_fillRect_0_, int p_fillRect_1_, int p_fillRect_2_, int p_fillRect_3_, int p_fillRect_4_)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GuiScreen var10000 = Minecraft.getMinecraft().currentScreen;
        GuiScreen.drawRect(p_fillRect_0_, p_fillRect_1_, p_fillRect_0_ + p_fillRect_2_, p_fillRect_1_ + p_fillRect_3_, p_fillRect_4_);
    }

    public static void fillGradientRect(int p_fillGradientRect_0_, int p_fillGradientRect_1_, int p_fillGradientRect_2_, int p_fillGradientRect_3_, int p_fillGradientRect_4_, int p_fillGradientRect_5_)
    {
        fillRect(p_fillGradientRect_0_, p_fillGradientRect_1_, p_fillGradientRect_2_, p_fillGradientRect_3_, p_fillGradientRect_4_);
    }

    public static void drawPanel(int p_drawPanel_0_, int p_drawPanel_1_, int p_drawPanel_2_, int p_drawPanel_3_, int p_drawPanel_4_, int p_drawPanel_5_, int p_drawPanel_6_)
    {
        fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_, p_drawPanel_2_ - 2, 1, p_drawPanel_5_);
        fillRect(p_drawPanel_0_ + p_drawPanel_2_ - 1, p_drawPanel_1_ + 1, 1, p_drawPanel_3_ - 2, p_drawPanel_5_);
        fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_ + p_drawPanel_3_ - 1, p_drawPanel_2_ - 2, 1, p_drawPanel_6_);
        fillRect(p_drawPanel_0_, p_drawPanel_1_ + 1, 1, p_drawPanel_3_ - 2, p_drawPanel_6_);
        fillRect(p_drawPanel_0_ + 1, p_drawPanel_1_ + 1, p_drawPanel_2_ - 2, p_drawPanel_3_ - 2, p_drawPanel_4_);
    }

    public static void drawTooltipPanel(int p_drawTooltipPanel_0_, int p_drawTooltipPanel_1_, int p_drawTooltipPanel_2_, int p_drawTooltipPanel_3_)
    {
        int var4 = -15728624;
        int var5 = -14088865;
        int var6 = -14743493;
        fillRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_, p_drawTooltipPanel_2_ - 2, p_drawTooltipPanel_3_, var4);
        fillRect(p_drawTooltipPanel_0_, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_2_, p_drawTooltipPanel_3_ - 2, var4);
        fillRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_2_ - 2, 1, var5);
        int var10000 = p_drawTooltipPanel_0_ + 1;
        int var10001 = p_drawTooltipPanel_1_ + p_drawTooltipPanel_3_ - 2;
        byte p_drawTooltipPanel_2_1 = 2;
        fillRect(var10000, var10001, 2, 1, var6);
        fillGradientRect(p_drawTooltipPanel_0_ + p_drawTooltipPanel_2_1 - 2, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_3_ - 2, 1, var5, var6);
        fillGradientRect(p_drawTooltipPanel_0_ + 1, p_drawTooltipPanel_1_ + 1, p_drawTooltipPanel_3_ - 2, 1, var5, var6);
    }

    private static void bindTMITexture()
    {
        if (textureID == -1)
        {
            InputStream var0 = null;

            try
            {
                textureID = 729294;
                var0 = TMIDrawing.class.getResourceAsStream("tmi.png");
                BufferedImage var1 = ImageIO.read(var0);
                BufferedImage var2 = new BufferedImage(512, 512, 2);
                var2.createGraphics().drawImage(var1, 0, 0, (ImageObserver)null);
                TextureUtil.uploadTextureImageAllocate(textureID, var2, false, false);
            }
            catch (Exception var11)
            {
                System.out.println("[TMI] SEVERE: couldn\'t load tmi.png");
                var11.printStackTrace();
            }
            finally
            {
                if (var0 != null)
                {
                    try
                    {
                        var0.close();
                    }
                    catch (Exception var10)
                    {
                        ;
                    }
                }
            }
        }

        TextureUtil.bindTexture(textureID);
    }
}
