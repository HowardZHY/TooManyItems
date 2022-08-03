import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class TMIController
{
    public static final String COPYRIGHT = "Copyright 2011-2014 Marglyph. Free for personal or educational use only. Do not redistribute TooManyItems, including in mod packs, and do not use TooManyItems\' source code or graphics in your own mods. Cracked by HowardZHY en 2022.";
    private boolean once = false;
    private boolean isOpen = false;
    private int savedScreenWidth = 0;
    private int savedScreenHeight = 0;
    private int savedGuiWidth = 0;
    private int savedGuiHeight = 0;
    private TMIArea sidebar = new TMISidebar();
    private boolean skipMouseUpOnce = false;

    public void onCreate(GuiContainer p_onCreate_1_)
    {
        TMITickEntity.create();
        TMIConfigFile.read();
        TMISaveFile.read();
    }

    public void frameStart(int p_frameStart_1_, int p_frameStart_2_, int p_frameStart_3_, int p_frameStart_4_, int p_frameStart_5_, int p_frameStart_6_)
    {
        try
        {
            if (!TMIConfigFile.isEnabled())
            {
                return;
            }

            GuiScreen var7 = Minecraft.getMinecraft().currentScreen;

            if (var7 instanceof GuiContainerCreative)
            {
                GuiTextField var8 = (GuiTextField)TMIPrivate.creativeSearchBox.get(var7);
                var8.xPosition = p_frameStart_3_ + 82;
            }

            int var12 = Minecraft.getMinecraft().currentScreen.width;
            int var9 = Minecraft.getMinecraft().currentScreen.height;

            if (var12 != this.savedScreenWidth || var9 != this.savedScreenHeight || p_frameStart_5_ != this.savedGuiWidth || p_frameStart_6_ != this.savedGuiHeight)
            {
                this.savedScreenWidth = var12;
                this.savedScreenHeight = var9;
                this.savedGuiWidth = p_frameStart_5_;
                this.savedGuiHeight = p_frameStart_6_;
                int var10 = (var12 - p_frameStart_5_) / 2;
                this.sidebar.setSize(Math.min(175, var10 - 4), Math.min(340, var9 - 8));
                this.sidebar.setPosition(var12 - var10 + 2, (var9 - this.sidebar.getHeight()) / 2);
                this.sidebar.doLayout();
            }

            TMIDispatch.determineMouseover(this.sidebar, p_frameStart_1_, p_frameStart_2_);
        }
        catch (Throwable var11)
        {
            TMIDebug.reportExceptionWithTimeout(var11, 10000L);
        }
    }

    public void frameEnd(int p_frameEnd_1_, int p_frameEnd_2_, int p_frameEnd_3_, int p_frameEnd_4_, int p_frameEnd_5_, int p_frameEnd_6_)
    {
        if (TMIConfigFile.isEnabled())
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(-((float)p_frameEnd_3_), -((float)p_frameEnd_4_), 0.0F);

            try
            {
                this.sidebar.draw(p_frameEnd_1_, p_frameEnd_2_);
                TMIArea var7 = this.sidebar.getMouseoverArea();

                if (var7 != null)
                {
                    List var8 = var7.getTooltip();

                    if (var8 != null && var8.size() > 0)
                    {
                        TMIDrawing.drawTooltip(p_frameEnd_1_, p_frameEnd_2_, var8);
                    }
                }
            }
            catch (Throwable var9)
            {
                TMIDebug.reportExceptionWithTimeout(var9, 10000L);
            }

            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void onClose()
    {
        this.sidebar.blurFocused();
    }

    public boolean onClick(int p_onClick_1_, int p_onClick_2_, int p_onClick_3_, int p_onClick_4_, int p_onClick_5_, Container p_onClick_6_)
    {
        try
        {
            if (!TMIConfigFile.isEnabled())
            {
                return true;
            }
            else
            {
                this.sidebar.blurFocused();
                TMIEvent var7 = TMIEvent.clickEvent(p_onClick_1_, p_onClick_2_, p_onClick_3_);
                TMIDispatch.sendMouseEvent(var7, this.sidebar);

                if (!var7.cancel && p_onClick_3_ == 0)
                {
                    try
                    {
                        GuiScreen var8 = Minecraft.getMinecraft().currentScreen;

                        if (var8 instanceof GuiContainerCreative)
                        {
                            GuiTextField var9 = (GuiTextField)TMIPrivate.creativeSearchBox.get(var8);
                            var9.setFocused(true);
                        }
                    }
                    catch (Exception var10)
                    {
                        ;
                    }
                }

                this.skipMouseUpOnce = var7.cancel;
                return !var7.cancel;
            }
        }
        catch (Throwable var11)
        {
            TMIDebug.reportException(var11);
            return true;
        }
    }

    public boolean onKeypress(char p_onKeypress_1_, int p_onKeypress_2_)
    {
        try
        {
            if (!TMIConfigFile.isEnabled())
            {
                if (p_onKeypress_2_ == TMIConfigFile.getHotkey())
                {
                    TMIConfigFile.toggleEnabled();
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                TMIEvent var3 = TMIEvent.keypressEvent(p_onKeypress_1_, p_onKeypress_2_);
                TMIDispatch.sendKeypress(var3, this.sidebar);

                if (!var3.cancel && p_onKeypress_2_ == TMIConfigFile.getHotkey())
                {
                    TMIConfigFile.toggleEnabled();
                    return false;
                }
                else
                {
                    return !var3.cancel;
                }
            }
        }
        catch (Throwable var4)
        {
            TMIDebug.reportException(var4);
            return true;
        }
    }

    public void onMouseEvent()
    {
        try
        {
            if (!TMIConfigFile.isEnabled())
            {
                return;
            }

            int var1 = Mouse.getEventDWheel();

            if (var1 == 0)
            {
                return;
            }

            var1 = var1 < 0 ? -1 : 1;
            GuiScreen var2 = Minecraft.getMinecraft().currentScreen;
            int var3 = Mouse.getEventX() * var2.width / Minecraft.getMinecraft().displayWidth;
            int var4 = var2.height - Mouse.getEventY() * var2.height / Minecraft.getMinecraft().displayHeight - 1;
            TMIEvent var5 = TMIEvent.scrollEvent(var3, var4, var1);
            TMIDispatch.sendMouseEvent(var5, this.sidebar);

            if (var5.cancel)
            {
                TMIPrivate.lwjglMouseEventDWheel.setInt((Object)null, 0);
                TMIPrivate.lwjglMouseDWheel.setInt((Object)null, 0);
            }
        }
        catch (Throwable var6)
        {
            TMIDebug.reportException(var6);
        }
    }

    public boolean shouldPauseGame()
    {
        return false;
    }

    public boolean skipMouseUpOnce()
    {
        boolean var1 = this.skipMouseUpOnce;
        this.skipMouseUpOnce = false;
        return var1;
    }
}