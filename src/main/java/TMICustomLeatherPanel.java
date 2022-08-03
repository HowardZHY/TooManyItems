
import net.minecraft.item.ItemStack;

class TMICustomLeatherPanel extends TMIArea
{
    private String[] itemTypes = new String[] {"leather_chestplate", "leather_helmet", "leather_leggings", "leather_boots"};
    private TMIItemButton[] buttons = new TMIItemButton[4];
    private String[] colorLabels = new String[] {"Red (0-255)", "Green (0-255)", "Blue (0-255)"};
    private TMITextField[] colorInputs = new TMITextField[3];

    public TMICustomLeatherPanel()
    {
        int var1;

        for (var1 = 0; var1 < 3; ++var1)
        {
            this.colorInputs[var1] = new TMITextField();
            this.colorInputs[var1].setValue("255");
            this.colorInputs[var1].addEventListener(this);
            this.addChild(this.colorInputs[var1]);
        }

        for (var1 = 0; var1 < 4; ++var1)
        {
            this.buttons[var1] = new TMIItemButton((ItemStack)null);
            this.buttons[var1].setSize(16, 16);
            this.buttons[var1].addEventListener(this);
            this.addChild(this.buttons[var1]);
        }

        this.setStacksFromInputs();
    }

    private void setStacksFromInputs()
    {
        int var1 = this.getInputValue(0);
        int var2 = this.getInputValue(1);
        int var3 = this.getInputValue(2);
        byte var4 = 0;
        int var7 = var4 | (var1 & 255) << 16;
        var7 |= (var2 & 255) << 8;
        var7 |= var3 & 255;

        for (int var5 = 0; var5 < 4; ++var5)
        {
            TMIStackBuilder var6 = new TMIStackBuilder(this.itemTypes[var5]);
            var6.display().setInteger("color", var7);
            this.buttons[var5].stack = var6.stack();
        }
    }

    private int getInputValue(int p_getInputValue_1_)
    {
        try
        {
            return Integer.parseInt(this.colorInputs[p_getInputValue_1_].value());
        }
        catch (NumberFormatException var3)
        {
            return 0;
        }
    }

    public void drawComponent()
    {
        int var1 = this.y + 10 + 16 + 10;

        for (int var2 = 0; var2 < 3; ++var2)
        {
            TMIDrawing.drawText(this.x + 4, var1, this.colorLabels[var2]);
            var1 += 32;
        }
    }

    public void layoutComponent()
    {
        int var1 = this.y + 10;
        int var2 = this.x + (this.width - 64) / 2;
        int var3;

        for (var3 = 0; var3 < 4; ++var3)
        {
            this.buttons[var3].setPosition(var2, var1);
            var2 += 16;
        }

        var3 = this.x + 4;
        int var4 = this.y + 10 + 16 + 10 + 12 + 1;

        for (int var5 = 0; var5 < 3; ++var5)
        {
            this.colorInputs[var5].setSize(this.width - 8, 12);
            this.colorInputs[var5].setPosition(var3, var4);
            var4 += 32;
        }
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 3 && p_controlEvent_1_.target instanceof TMITextField)
        {
            TMITextField var2 = (TMITextField)p_controlEvent_1_.target;
            int var3 = 0;

            if (!var2.value().equals(""))
            {
                try
                {
                    var3 = Integer.parseInt(var2.value());
                }
                catch (NumberFormatException var5)
                {
                    var3 = -1;
                }
            }

            boolean var6;

            if (var3 < 0)
            {
                var6 = false;
                var2.setValue("0");
            }
            else if (var3 > 255)
            {
                var6 = true;
                var2.setValue("255");
            }

            this.setStacksFromInputs();
        }
    }
}
