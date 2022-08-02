package net.minecraft.src;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class TMIButton extends TMIArea
{
    public String label;
    public Object data;
    public ItemStack stack;
    public boolean showState;
    public boolean state;
    public int textScale;
    public boolean centerText;
    public boolean itemTooltip;

    public TMIButton(Object p_i46376_1_, String p_i46376_2_, ItemStack p_i46376_3_)
    {
        this.stack = null;
        this.showState = false;
        this.state = false;
        this.textScale = 2;
        this.centerText = true;
        this.itemTooltip = false;
        this.data = p_i46376_1_;
        this.label = p_i46376_2_;
        this.stack = p_i46376_3_;
        this.setOwnWidth();
        this.height = p_i46376_3_ != null ? 16 : 12;
    }

    public TMIButton(Object p_i46377_1_, String p_i46377_2_)
    {
        this(p_i46377_1_, p_i46377_2_, (ItemStack)null);
    }

    public TMIButton()
    {
        this((Object)null, "");
    }

    public TMIButton item(String p_item_1_)
    {
        this.stack = (new TMIStackBuilder(p_item_1_)).stack();
        return this;
    }

    public TMIButton item(String p_item_1_, int p_item_2_)
    {
        this.stack = (new TMIStackBuilder(p_item_1_)).meta(p_item_2_).stack();
        return this;
    }

    public TMIButton center(boolean p_center_1_)
    {
        this.centerText = p_center_1_;
        return this;
    }

    public void setOwnWidth()
    {
        this.width = TMIDrawing.getTextWidth(this.label, this.textScale) + this.graphicWidth() + this.getMargin();
    }

    public int graphicWidth()
    {
        return this.stack != null ? 18 : 0;
    }

    public int getMargin()
    {
        return this.label != null && this.label.length() > 0 ? 6 : 2;
    }

    protected boolean drawGraphic(int p_drawGraphic_1_)
    {
        if (this.stack != null)
        {
            int var2 = this.y + (this.height - 16) / 2;
            TMIDrawing.drawItem(p_drawGraphic_1_, var2, this.stack);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        TMIDrawing.fillRect(this.x, this.y, this.width, this.height, this.contains(p_drawComponent_1_, p_drawComponent_2_) ? 587202559 : 0);
        TMIDrawing.drawText(this.x, this.y, "", -1);
        String var3 = this.label;
        int var4 = TMIDrawing.getTextWidth(var3, this.textScale);
        int var5;

        for (var5 = this.graphicWidth(); var4 + var5 > this.width && var3.length() > 0; var4 = TMIDrawing.getTextWidth(var3, this.textScale))
        {
            var3 = var3.substring(0, var3.length() - 1);
        }

        int var6 = var4 + var5;
        int var7 = this.x + this.getMargin();

        if (this.centerText)
        {
            var7 = this.x + (this.width - var6) / 2;
        }

        int var8 = this.y + (this.height - 8) / 2;
        boolean var9 = this.drawGraphic(var7);
        var7 += this.graphicWidth();

        if (var9 && var4 > 0)
        {
            var7 += 2;
        }

        TMIDrawing.drawText(var7, var8, var3, -1, this.textScale);
    }

    public List<String> getTooltip()
    {
        return this.itemTooltip && this.stack != null ? this.stack.getTooltip(Minecraft.getMinecraft().thePlayer, true) : null;
    }
}