
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TMISwatchPicker extends TMIArea
{
    private boolean isMulti;
    private int swatchWidth;

    public TMISwatchPicker(int[] p_i46402_1_, int p_i46402_2_, boolean p_i46402_3_)
    {
        this.isMulti = p_i46402_3_;
        this.swatchWidth = p_i46402_2_;

        for (int var4 = 0; var4 < p_i46402_1_.length; ++var4)
        {
            TMISwatchPicker.Swatch var5 = new TMISwatchPicker.Swatch(p_i46402_1_[var4]);
            var5.setSize(p_i46402_2_, p_i46402_2_);
            this.addChild(var5);
        }
    }

    public TMISwatchPicker(int[] p_i46403_1_, int p_i46403_2_)
    {
        this(p_i46403_1_, p_i46403_2_, false);
    }

    public void layoutComponent()
    {
        int var1 = this.width / this.swatchWidth;
        int var2 = 0;
        int var3 = 0;
        Iterator var4 = this.children.iterator();

        while (var4.hasNext())
        {
            TMIArea var5 = (TMIArea)var4.next();
            var5.setPosition(this.x + var3 * this.swatchWidth, this.y + var2 * this.swatchWidth);
            ++var3;

            if (var3 == var1)
            {
                var3 = 0;
                ++var2;
            }
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.target instanceof TMISwatchPicker.Swatch)
        {
            TMISwatchPicker.Swatch var4;

            if (this.isMulti)
            {
                TMISwatchPicker.Swatch var2 = (TMISwatchPicker.Swatch)p_mouseEvent_1_.target;
                var2.selected = !var2.selected;
            }
            else
            {
                for (Iterator var5 = this.children.iterator(); var5.hasNext(); var4.selected = var4 == p_mouseEvent_1_.target)
                {
                    TMIArea var3 = (TMIArea)var5.next();
                    var4 = (TMISwatchPicker.Swatch)var3;
                }
            }

            this.emit(TMIEvent.controlEvent(3, this));
        }
    }

    public List<Integer> getValues()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.children.iterator();

        while (var2.hasNext())
        {
            TMIArea var3 = (TMIArea)var2.next();
            TMISwatchPicker.Swatch var4 = (TMISwatchPicker.Swatch)var3;

            if (var4.selected)
            {
                var1.add(Integer.valueOf(var4.color));
            }
        }

        return var1;
    }

    public int[] getArray()
    {
        List var1 = this.getValues();
        int[] var2 = new int[var1.size()];

        for (int var3 = 0; var3 < var2.length; ++var3)
        {
            var2[var3] = ((Integer)var1.get(var3)).intValue();
        }

        return var2;
    }

    public int getFirst()
    {
        Iterator var1 = this.children.iterator();
        TMISwatchPicker.Swatch var3;

        do
        {
            if (!var1.hasNext())
            {
                return -1;
            }

            TMIArea var2 = (TMIArea)var1.next();
            var3 = (TMISwatchPicker.Swatch)var2;
        }
        while (!var3.selected);

        return var3.color;
    }

    class Swatch extends TMIArea
    {
        int color;
        boolean selected;

        public Swatch(int p_i46401_2_)
        {
            this.color = p_i46401_2_;
        }

        public void drawComponent()
        {
            if (this.selected)
            {
                TMIDrawing.fillRect(this.x, this.y, this.width, this.height, -2236963);
            }

            TMIDrawing.fillRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2, -16777216 | this.color);
        }
    }
}
