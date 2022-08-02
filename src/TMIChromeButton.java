package net.minecraft.src;
import java.util.List;

public class TMIChromeButton extends TMIButton
{
    private final int iconX;
    private final int iconY;
    private final int iconWidth = 12;
    private final int iconHeight = 12;

    public TMIChromeButton(int p_i46378_1_, int p_i46378_2_, String p_i46378_3_)
    {
        this.iconX = p_i46378_2_ * 12;
        this.iconY = p_i46378_1_ * 12;
        this.setSize(12, 12);
        this.setTooltip(p_i46378_3_);
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        TMIDrawing.drawIcon(this.x + (this.width - 12) / 2, this.y + (this.height - 12) / 2, this.iconX, this.iconY, 12, 12);
    }

    public List<String> getTooltip()
    {
        return this.tooltip;
    }
}