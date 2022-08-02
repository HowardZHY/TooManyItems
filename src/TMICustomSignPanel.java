package net.minecraft.src;
class TMICustomSignPanel extends TMIArea
{
    private TMIItemButton sign = new TMIItemButton((new TMIStackBuilder("sign")).stack());
    private TMITextField[] text = new TMITextField[4];
    public static final int LINES = 4;

    public TMICustomSignPanel()
    {
        this.addChild(this.sign);

        for (int var1 = 0; var1 < 4; ++var1)
        {
            this.text[var1] = new TMITextField();
            this.addChild(this.text[var1]);
            this.text[var1].addEventListener(this);
        }
    }

    public void layoutComponent()
    {
        this.sign.setSize(16, 16);
        this.sign.x = this.x + (this.width - 16) / 2;
        this.sign.y = this.y + 10;

        for (int var1 = 0; var1 < 4; ++var1)
        {
            this.text[var1].setSize(this.width - 4, 12);
            this.text[var1].setPosition(this.x + 2, this.y + 10 + 16 + 4 + var1 * 18);
        }
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 3)
        {
            TMIStackBuilder var2 = (new TMIStackBuilder("sign")).name("Sign with text");

            for (int var3 = 0; var3 < 4; ++var3)
            {
                var2.lore(this.text[var3].value());
                var2.blockEntity().setString("Text" + (var3 + 1), this.text[var3].value());
            }

            this.sign.stack = var2.stack();
        }
    }

    public void keyboardEvent(TMIEvent p_keyboardEvent_1_)
    {
        if (p_keyboardEvent_1_.keyCode == 15)
        {
            int var2 = this.children.indexOf(this.focusArea) - 1;

            if (var2 < 0)
            {
                var2 = -1;
            }

            var2 = (var2 + 1) % 4;
            this.text[var2].focus();
        }
    }
}