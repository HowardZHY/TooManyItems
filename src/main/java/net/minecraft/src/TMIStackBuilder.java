package net.minecraft.src;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class TMIStackBuilder
{
    private ItemStack stack;

    public TMIStackBuilder(Item p_i46397_1_)
    {
        this.stack = null;
        this.stack = new ItemStack(p_i46397_1_);
    }

    public TMIStackBuilder(Block p_i46398_1_)
    {
        this.stack = null;
        this.stack = new ItemStack(p_i46398_1_);
    }

    public TMIStackBuilder(ItemStack p_i46399_1_)
    {
        this.stack = null;
        this.stack = p_i46399_1_.copy();
    }

    public TMIStackBuilder(String p_i46400_1_)
    {
        this((Item)((Item)Item.itemRegistry.getObject(new ResourceLocation(p_i46400_1_))));
    }

    public ItemStack stack()
    {
        return this.stack.copy();
    }

    public ItemStack maxStack()
    {
        ItemStack var1 = this.stack.copy();
        var1.stackSize = var1.getItem().getItemStackLimit();
        return var1;
    }

    public NBTTagCompound asTag()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.stack.writeToNBT(var1);
        return var1;
    }

    public TMIStackBuilder amount(int p_amount_1_)
    {
        this.stack.stackSize = p_amount_1_;
        return this;
    }

    public TMIStackBuilder meta(int p_meta_1_)
    {
        this.stack.setItemDamage(p_meta_1_);
        return this;
    }

    public int amount()
    {
        return this.stack.stackSize;
    }

    public int meta()
    {
        return this.stack.getItemDamage();
    }

    public NBTTagCompound tag()
    {
        if (this.stack.getTagCompound() == null)
        {
            this.stack.setTagCompound(new NBTTagCompound());
        }

        return this.stack.getTagCompound();
    }

    public NBTTagCompound display()
    {
        return getTagWithCreate(this.tag(), "display");
    }

    public NBTTagCompound blockEntity()
    {
        return getTagWithCreate(this.tag(), "BlockEntityTag");
    }

    public TMIStackBuilder name(String p_name_1_)
    {
        if (p_name_1_ != null && !p_name_1_.equals(""))
        {
            this.display().setString("Name", "\u00a7r" + p_name_1_);
        }
        else
        {
            this.clearName();
        }

        return this;
    }

    public TMIStackBuilder clearName()
    {
        if (this.display().hasKey("Name"))
        {
            this.display().removeTag("Name");
        }

        return this;
    }

    public TMIStackBuilder clearEnch()
    {
        if (this.tag().hasKey("ench"))
        {
            this.tag().removeTag("ench");
        }

        return this;
    }

    public TMIStackBuilder lore(String p_lore_1_)
    {
        NBTTagList var2 = getTagListWithCreate(this.display(), "Lore", 8);
        var2.appendTag(new NBTTagString(p_lore_1_));
        return this;
    }

    public TMIStackBuilder ench(Enchantment p_ench_1_, int p_ench_2_)
    {
        this.stack.addEnchantment(p_ench_1_, p_ench_2_);
        return this;
    }

    public TMIStackBuilder effect(Potion p_effect_1_, int p_effect_2_, int p_effect_3_)
    {
        NBTTagList var4 = getTagListWithCreate(this.tag(), "CustomPotionEffects");
        NBTTagCompound var5 = new NBTTagCompound();
        PotionEffect var6 = new PotionEffect(p_effect_1_.id, p_effect_1_.isInstant() ? 0 : p_effect_3_, p_effect_2_);
        var6.writeCustomPotionEffectToNBT(var5);
        var4.appendTag(var5);
        return this;
    }

    public static NBTTagCompound getTagWithCreate(NBTTagCompound p_getTagWithCreate_0_, String p_getTagWithCreate_1_)
    {
        if (!p_getTagWithCreate_0_.hasKey(p_getTagWithCreate_1_))
        {
            p_getTagWithCreate_0_.setTag(p_getTagWithCreate_1_, new NBTTagCompound());
        }

        return p_getTagWithCreate_0_.getCompoundTag(p_getTagWithCreate_1_);
    }

    public static NBTTagList getTagListWithCreate(NBTTagCompound p_getTagListWithCreate_0_, String p_getTagListWithCreate_1_, int p_getTagListWithCreate_2_)
    {
        if (!p_getTagListWithCreate_0_.hasKey(p_getTagListWithCreate_1_))
        {
            p_getTagListWithCreate_0_.setTag(p_getTagListWithCreate_1_, new NBTTagList());
        }

        return p_getTagListWithCreate_0_.getTagList(p_getTagListWithCreate_1_, p_getTagListWithCreate_2_);
    }

    public static NBTTagList getTagListWithCreate(NBTTagCompound p_getTagListWithCreate_0_, String p_getTagListWithCreate_1_)
    {
        return getTagListWithCreate(p_getTagListWithCreate_0_, p_getTagListWithCreate_1_, 10);
    }

    public static TMIStackBuilder commandBlock(String p_commandBlock_0_)
    {
        TMIStackBuilder var1 = new TMIStackBuilder("command_block");
        var1.blockEntity().setString("Command", p_commandBlock_0_);
        return var1;
    }
}