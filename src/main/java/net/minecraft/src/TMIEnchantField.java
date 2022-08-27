package net.minecraft.src;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class TMIEnchantField extends TMINumField
{
    public Enchantment ench;

    public TMIEnchantField(Enchantment p_i46385_1_, ItemStack p_i46385_2_)
    {
        super(StatCollector.translateToLocal(p_i46385_1_.getName()));
        this.ench = p_i46385_1_;
        this.labelColor = p_i46385_1_.type.canEnchantItem(p_i46385_2_.getItem()) ? -1 : -2285022;
    }
}