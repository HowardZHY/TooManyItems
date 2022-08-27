package net.minecraft.src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TMICycleButton extends TMIButton
{
    private List<String> options;
    private int index;
    private String prefix;

    public TMICycleButton(List<String> p_i46381_1_, String p_i46381_2_)
    {
        this.index = 0;
        this.prefix = "";
        this.options = new ArrayList(p_i46381_1_);
        this.height = 12;
        this.width = 0;
        Iterator var3 = p_i46381_1_.iterator();

        while (var3.hasNext())
        {
            String var4 = (String)var3.next();
            int var5 = TMIDrawing.getTextWidth(var4);

            if (var5 > this.width)
            {
                this.width = var5;
            }
        }

        this.doLabel();
    }

    public TMICycleButton(List<String> p_i46382_1_)
    {
        this(p_i46382_1_, "");
    }

    public TMICycleButton(String ... p_i46383_1_)
    {
        this(Arrays.asList(p_i46383_1_));
    }

    private void doLabel()
    {
        this.label = (this.prefix != null ? this.prefix : "") + (this.index >= 0 && this.index < this.options.size() ? (String)this.options.get(this.index) : "ERR");
    }

    public void setPrefix(String p_setPrefix_1_)
    {
        this.prefix = p_setPrefix_1_;
        this.doLabel();
    }

    public String getValue()
    {
        return (String)this.options.get(this.index);
    }

    public int getIntValue()
    {
        return Integer.parseInt((String)this.options.get(this.index));
    }

    public int getIndex()
    {
        return this.index;
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.mouseButton == 0)
        {
            ++this.index;
        }
        else if (p_mouseEvent_1_.mouseButton == 1)
        {
            --this.index;
        }
        else if (p_mouseEvent_1_.type == 2)
        {
            this.index -= p_mouseEvent_1_.wheel;
        }

        if (this.index < 0)
        {
            this.index = this.options.size() - 1;
        }

        this.index %= this.options.size();
        this.doLabel();
        this.emit(TMIEvent.controlEvent(3, this));
    }
}