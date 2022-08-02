package net.minecraft.src;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;

public class TMIEffectField extends TMINumField
{
    public Potion effect;

    public TMIEffectField(Potion p_i46384_1_)
    {
        super(I18n.format(p_i46384_1_.getName(), new Object[0]));
        this.effect = p_i46384_1_;
    }
}