
import net.minecraft.nbt.NBTTagList;

public class TMIPotionBuilder extends TMIStackBuilder
{
    public TMIPotionBuilder()
    {
        super("potion");
    }

    public NBTTagList potionEffects()
    {
        return getTagListWithCreate(this.tag(), "CustomPotionEffects");
    }

    public TMIPotionBuilder splash(boolean p_splash_1_)
    {
        int var2 = this.meta();

        if (p_splash_1_)
        {
            var2 &= -8193;
            var2 |= 16384;
        }
        else
        {
            var2 &= -16385;
            var2 |= 8192;
        }

        this.meta(var2);
        return this;
    }

    public TMIPotionBuilder color(int p_color_1_)
    {
        p_color_1_ &= 15;
        int var2 = this.meta() & -16;
        var2 |= p_color_1_;
        this.meta(var2);
        return this;
    }
}
