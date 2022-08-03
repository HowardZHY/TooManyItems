
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TMIArea implements TMIListener
{
    protected int x;
    protected int y;
    protected int z;
    protected int width;
    protected int height;
    protected boolean shown = true;
    public List<TMIArea> children = new ArrayList();
    public List<TMIListener> listeners = new ArrayList();
    protected TMIArea parent = null;
    protected TMIArea focusArea = null;
    protected TMIArea mouseoverArea = null;
    public List<String> tooltip;

    public void setSize(int p_setSize_1_, int p_setSize_2_)
    {
        this.width = p_setSize_1_;
        this.height = p_setSize_2_;
    }

    public void setPosition(int p_setPosition_1_, int p_setPosition_2_)
    {
        this.x = p_setPosition_1_;
        this.y = p_setPosition_2_;
    }

    public void setPositionRelative(TMIArea p_setPositionRelative_1_, int p_setPositionRelative_2_, int p_setPositionRelative_3_)
    {
        this.x = p_setPositionRelative_1_.getX() + p_setPositionRelative_2_;
        this.y = p_setPositionRelative_1_.getY() + p_setPositionRelative_3_;
    }

    public void setPositionCenteredIn(int p_setPositionCenteredIn_1_, int p_setPositionCenteredIn_2_, int p_setPositionCenteredIn_3_, int p_setPositionCenteredIn_4_)
    {
        this.x = p_setPositionCenteredIn_1_ + (p_setPositionCenteredIn_3_ - this.getWidth()) / 2;
        this.y = p_setPositionCenteredIn_2_ + (p_setPositionCenteredIn_4_ - this.getHeight()) / 2;
    }

    public void setYCenteredIn(int p_setYCenteredIn_1_, TMIArea p_setYCenteredIn_2_)
    {
        this.y = p_setYCenteredIn_1_;
        this.x = p_setYCenteredIn_2_.x + (p_setYCenteredIn_2_.width - this.width) / 2;
    }

    public void copyBounds(TMIArea p_copyBounds_1_)
    {
        this.x = p_copyBounds_1_.getX();
        this.y = p_copyBounds_1_.getY();
        this.width = p_copyBounds_1_.getWidth();
        this.height = p_copyBounds_1_.getHeight();
    }

    public void addChild(TMIArea p_addChild_1_)
    {
        if (!this.children.contains(p_addChild_1_))
        {
            this.children.add(p_addChild_1_);

            if (p_addChild_1_.parent != null)
            {
                p_addChild_1_.parent.removeChild(p_addChild_1_);
            }

            p_addChild_1_.parent = this;
        }
    }

    public boolean contains(int p_contains_1_, int p_contains_2_)
    {
        return p_contains_1_ >= this.x && p_contains_2_ >= this.y && p_contains_1_ <= this.x + this.width && p_contains_2_ <= this.y + this.height;
    }

    public void removeChild(TMIArea p_removeChild_1_)
    {
        this.children.remove(p_removeChild_1_);
        p_removeChild_1_.parent = null;
    }

    public void removeChildrenOfType(Class p_removeChildrenOfType_1_)
    {
        Iterator var2 = this.children.iterator();

        while (var2.hasNext())
        {
            TMIArea var3 = (TMIArea)var2.next();

            if (p_removeChildrenOfType_1_.isInstance(var3))
            {
                var3.parent = null;
                var2.remove();
            }
        }
    }

    public List<TMIArea> children()
    {
        return this.children;
    }

    public boolean hasChildren()
    {
        return this.children.size() > 0;
    }

    public TMIArea getParent()
    {
        return this.parent;
    }

    public boolean isFocused()
    {
        return this.focusArea == this;
    }

    public TMIArea getFocused()
    {
        return this.focusArea;
    }

    public void focus()
    {
        this.focus(this);
    }

    public void blur()
    {
        this.blur(this);
    }

    public void blurFocused()
    {
        if (this.focusArea != null)
        {
            this.focusArea.blur();
        }
    }

    protected void focus(TMIArea p_focus_1_)
    {
        this.blurFocused();
        this.focusArea = p_focus_1_;

        if (this.parent != null)
        {
            this.parent.focus(p_focus_1_);
        }
    }

    protected void blur(TMIArea p_blur_1_)
    {
        if (this.focusArea == p_blur_1_)
        {
            this.focusArea = null;

            if (this.parent != null)
            {
                this.parent.blur(p_blur_1_);
            }
        }
    }

    protected void mouseover()
    {
        this.mouseover(this);
    }

    protected void mouseover(TMIArea p_mouseover_1_)
    {
        this.mouseoverArea = p_mouseover_1_;

        if (this.parent != null)
        {
            this.parent.mouseover(p_mouseover_1_);
        }
    }

    public boolean isMouseover()
    {
        TMIArea var1;

        for (var1 = this; var1.parent != null; var1 = var1.parent)
        {
            ;
        }

        return var1.mouseoverArea == this;
    }

    public TMIArea getMouseoverArea()
    {
        return this.mouseoverArea;
    }

    public void setTooltip(String p_setTooltip_1_)
    {
        this.tooltip = new ArrayList(Arrays.asList(p_setTooltip_1_.split("\n")));
    }

    public void setTooltip(List<String> p_setTooltip_1_)
    {
        this.tooltip = p_setTooltip_1_;
    }

    public void setTooltipLine(int p_setTooltipLine_1_, String p_setTooltipLine_2_)
    {
        this.tooltip.set(p_setTooltipLine_1_, p_setTooltipLine_2_);
    }

    public void clearTooltip()
    {
        this.tooltip = null;
    }

    public void addTooltipLine(String p_addTooltipLine_1_)
    {
        if (this.tooltip == null)
        {
            this.tooltip = new ArrayList();
        }

        this.tooltip.add(p_addTooltipLine_1_);
    }

    public List<String> getTooltip()
    {
        return this.tooltip;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(int p_setZ_1_)
    {
        this.z = p_setZ_1_;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public boolean visible()
    {
        return this.shown;
    }

    public void show(boolean p_show_1_)
    {
        this.shown = p_show_1_;
    }

    public void show()
    {
        this.shown = true;
    }

    public void hide()
    {
        this.shown = false;
    }

    public void addEventListener(TMIListener p_addEventListener_1_)
    {
        if (!this.listeners.contains(p_addEventListener_1_))
        {
            this.listeners.add(p_addEventListener_1_);
        }
    }

    public int centerX()
    {
        return this.x + this.width / 2;
    }

    public int centerY()
    {
        return this.y + this.height / 2;
    }

    public static int center(int p_center_0_, int p_center_1_, int p_center_2_)
    {
        return p_center_0_ + (p_center_1_ - p_center_2_) / 2;
    }

    public void emit(TMIEvent p_emit_1_)
    {
        Iterator var2 = this.listeners.iterator();

        while (var2.hasNext())
        {
            TMIListener var3 = (TMIListener)var2.next();

            if (p_emit_1_.type == 0)
            {
                var3.mouseEvent(p_emit_1_);
            }
            else if (p_emit_1_.type == 1)
            {
                var3.keyboardEvent(p_emit_1_);
            }
            else
            {
                var3.controlEvent(p_emit_1_);
            }
        }
    }

    public void draw(int p_draw_1_, int p_draw_2_)
    {
        this.drawComponent(p_draw_1_, p_draw_2_);
        Iterator var3 = this.children.iterator();

        while (var3.hasNext())
        {
            TMIArea var4 = (TMIArea)var3.next();

            if (var4.visible())
            {
                var4.draw(p_draw_1_, p_draw_2_);
            }
        }
    }

    public void doLayout()
    {
        this.layoutComponent();
        Iterator var1 = this.children.iterator();

        while (var1.hasNext())
        {
            TMIArea var2 = (TMIArea)var1.next();
            var2.doLayout();
        }
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        this.drawComponent();
    }

    public void drawComponent() {}

    public void layoutComponent() {}

    public void mouseEvent(TMIEvent p_mouseEvent_1_) {}

    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {}

    public void controlEvent(TMIEvent p_controlEvent_1_) {}
}
