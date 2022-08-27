package net.minecraft.src;
import net.minecraft.nbt.NBTTagCompound;

class TMICustomHeadPanel extends TMIArea
{
    private TMIItemButton head = new TMIItemButton((new TMIStackBuilder("skull")).meta(3).stack());
    private TMITextField name = new TMITextField();

    public TMICustomHeadPanel()
    {
        this.name.placeholder = "Player name...";
        this.addChild(this.head);
        this.addChild(this.name);
        this.name.addEventListener(this);
    }

    public void layoutComponent()
    {
        this.head.setSize(16, 16);
        this.head.x = this.x + (this.width - 16) / 2;
        this.head.y = this.y + 10;
        this.name.setSize(this.width - 4, 12);
        this.name.setPosition(this.x + 2, this.y + 10 + 16 + 4);
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 3)
        {
            if (this.name.value() == null || this.name.value().equals(""))
            {
                this.head.stack = (new TMIStackBuilder("skull")).meta(3).stack();
                return;
            }

            TMIStackBuilder var2 = (new TMIStackBuilder("skull")).meta(3);
            NBTTagCompound var3 = new NBTTagCompound();
            var3.setString("Name", this.name.value());
            var2.tag().setTag("SkullOwner", var3);
            this.head.stack = var2.stack();
        }
    }
}