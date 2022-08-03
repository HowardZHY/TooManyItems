package net.minecraft.src;
public class TMIEvent
{
    public TMIArea target;
    public boolean cancel = false;
    public int type;
    public int x = -1;
    public int y = -1;
    public int mouseButton = -1;
    public char key;
    public int keyCode = -1;
    public int wheel = -1;
    public static final int CLICK = 0;
    public static final int KEY = 1;
    public static final int SCROLL = 2;
    public static final int CHANGE = 3;
    public static final int SELECT = 4;

    public TMIEvent(int p_i46386_1_)
    {
        this.type = p_i46386_1_;
    }

    public static TMIEvent clickEvent(int p_clickEvent_0_, int p_clickEvent_1_, int p_clickEvent_2_)
    {
        TMIEvent var3 = new TMIEvent(0);
        var3.x = p_clickEvent_0_;
        var3.y = p_clickEvent_1_;
        var3.mouseButton = p_clickEvent_2_;
        return var3;
    }

    public static TMIEvent keypressEvent(char p_keypressEvent_0_, int p_keypressEvent_1_)
    {
        TMIEvent var2 = new TMIEvent(1);
        var2.key = p_keypressEvent_0_;
        var2.keyCode = p_keypressEvent_1_;
        return var2;
    }

    public static TMIEvent scrollEvent(int p_scrollEvent_0_, int p_scrollEvent_1_, int p_scrollEvent_2_)
    {
        TMIEvent var3 = new TMIEvent(2);
        var3.x = p_scrollEvent_0_;
        var3.y = p_scrollEvent_1_;
        var3.wheel = p_scrollEvent_2_;
        return var3;
    }

    public static TMIEvent controlEvent(int p_controlEvent_0_, TMIArea p_controlEvent_1_)
    {
        TMIEvent var2 = new TMIEvent(p_controlEvent_0_);
        var2.target = p_controlEvent_1_;
        return var2;
    }

    public void cancel()
    {
        this.cancel = true;
    }

    public String toString()
    {
        return String.format("TMIEvent(area=%s, type=%s, cancel=%s, x=%s, y=%s, mouseButton=%s, keyCode=%s)", new Object[] {this.target, Integer.valueOf(this.type), Boolean.valueOf(this.cancel), Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.mouseButton), Integer.valueOf(this.keyCode)});
    }
}