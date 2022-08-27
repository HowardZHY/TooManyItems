package net.minecraft.src;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TMIFireworkBuilder
{
    public static final Random random = new Random();
    private byte flight = 2;
    private List<NBTTagCompound> explosions = new ArrayList();

    public TMIFireworkBuilder flight(int p_flight_1_)
    {
        this.flight = (byte)p_flight_1_;
        return this;
    }

    public TMIStackBuilder firework()
    {
        TMIStackBuilder var1 = new TMIStackBuilder("fireworks");
        NBTTagCompound var2 = TMIStackBuilder.getTagWithCreate(var1.tag(), "Fireworks");
        NBTTagList var3 = TMIStackBuilder.getTagListWithCreate(var2, "Explosions");
        var2.setByte("Flight", this.flight);
        Iterator var4 = this.explosions.iterator();

        while (var4.hasNext())
        {
            NBTTagCompound var5 = (NBTTagCompound)var4.next();
            var3.appendTag(var5);
        }

        return var1;
    }

    public TMIStackBuilder charge()
    {
        TMIStackBuilder var1 = new TMIStackBuilder("firework_charge");

        if (this.explosions.size() > 0)
        {
            var1.tag().setTag("Explosion", (NBTBase)this.explosions.get(0));
        }

        return var1;
    }

    public TMIFireworkBuilder explosion(int p_explosion_1_, int[] p_explosion_2_, int[] p_explosion_3_, boolean p_explosion_4_, boolean p_explosion_5_)
    {
        NBTTagCompound var6 = new NBTTagCompound();
        var6.setBoolean("Flicker", p_explosion_4_);
        var6.setBoolean("Trail", p_explosion_5_);
        var6.setByte("Type", (byte)(p_explosion_1_ & 15));

        if (p_explosion_2_ != null && p_explosion_2_.length > 0)
        {
            var6.setIntArray("Colors", p_explosion_2_);
        }

        if (p_explosion_3_ != null && p_explosion_3_.length > 0)
        {
            var6.setIntArray("FadeColors", p_explosion_3_);
        }

        this.explosions.add(var6);
        return this;
    }

    public static int randomBrightColor()
    {
        return Color.HSBtoRGB(random.nextFloat(), random.nextFloat(), random.nextFloat() * 0.5F + 0.5F);
    }

    public static TMIStackBuilder randomFirework()
    {
        TMIFireworkBuilder var0 = new TMIFireworkBuilder();
        var0.flight(random.nextInt(3) + 1);
        int[] var1;

        if (random.nextBoolean())
        {
            var1 = new int[] {randomBrightColor(), randomBrightColor()};
        }
        else
        {
            var1 = new int[] {randomBrightColor()};
        }

        int[] var2;

        if (random.nextBoolean())
        {
            var2 = new int[] {randomBrightColor()};
        }
        else
        {
            var2 = null;
        }

        var0.explosion(random.nextInt(4), var1, var2, random.nextBoolean(), random.nextBoolean());
        return var0.firework().name("Random Firework");
    }
}