package net.minecraft.src;
import java.util.Iterator;

public class TMITabset extends TMIArea
{
    public final TMITabset.Tab items = new TMITabset.Tab(0, 6, "Basic Items", false);
    public final TMITabset.Tab myItems = new TMITabset.Tab(1, 8, "My Items", false);
    public final TMITabset.Tab customs = new TMITabset.Tab(0, 8, "Custom", false);
    public final TMITabset.Tab controls = new TMITabset.Tab(1, 5, "Controls", false);
    public final TMITabset.Tab saves = new TMITabset.Tab(0, 7, "Saves", true);

    public TMITabset()
    {
        this.addChild(this.items);
        this.addChild(this.myItems);
        this.addChild(this.customs);
        this.addChild(this.controls);
        this.addChild(this.saves);
    }

    public void layoutComponent()
    {
        int var1 = this.getWidth() / this.children.size();
        int var2 = this.x;

        for (Iterator var3 = this.children.iterator(); var3.hasNext(); var2 += var1)
        {
            TMIArea var4 = (TMIArea)var3.next();
            var4.setSize(var1, this.getHeight());
            var4.setPosition(var2, this.y);
        }
    }

    class Tab extends TMIChromeButton
    {
        private final boolean isSingleplayerOnly;
        private final String name;

        public Tab(int p_i46404_2_, int p_i46404_3_, String p_i46404_4_, boolean p_i46404_5_)
        {
            super(p_i46404_2_, p_i46404_3_, p_i46404_4_);
            this.name = p_i46404_4_;
            this.isSingleplayerOnly = p_i46404_5_;
        }

        public void layoutComponent()
        {
            this.setTooltip(this.name + (this.isSingleplayerOnly && TMIGame.isMultiplayer() ? "\nSingle player only" : ""));
        }
    }
}