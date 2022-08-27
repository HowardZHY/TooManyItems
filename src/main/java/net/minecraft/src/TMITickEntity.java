package net.minecraft.src;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TMITickEntity extends Entity
{
    public static long timeout = 0L;
    public static boolean quietErrors = false;
    public static boolean infiniteStack = false;
    public static boolean preventRain = false;
    private ItemStack previousStack = null;
    private int previousSlot = -1;
    private int previousSize = -1;

    protected TMITickEntity(World p_i46405_1_)
    {
        super(p_i46405_1_);
    }

    public static TMITickEntity create()
    {
        Iterator var0 = Minecraft.getMinecraft().theWorld.weatherEffects.iterator();
        Entity var1;

        do
        {
            if (!var0.hasNext())
            {
                TMITickEntity var2 = new TMITickEntity(Minecraft.getMinecraft().theWorld);
                Minecraft.getMinecraft().theWorld.weatherEffects.add(var2);
                return var2;
            }

            var1 = (Entity)var0.next();
        }
        while (!(var1 instanceof TMITickEntity));

        return (TMITickEntity)var1;
    }

    public void doInfiniteStack()
    {
        if (Minecraft.getMinecraft().currentScreen != null)
        {
            this.previousStack = null;
        }
        else
        {
            InventoryPlayer var1 = TMIGame.getPlayerServer().inventory;
            int var2 = var1.currentItem;

            if (var2 >= 0 && var2 <= 8)
            {
                ItemStack var3 = var1.getCurrentItem();

                if (this.previousStack != null && this.previousSlot == var2)
                {
                    if (var3 == null || var3.stackSize < this.previousSize)
                    {
                        this.previousStack.stackSize = this.previousSize;
                        var1.mainInventory[var2] = this.previousStack;
                        Minecraft.getMinecraft().thePlayer.inventory.mainInventory[var2] = this.previousStack;
                    }
                }
                else
                {
                    this.previousStack = var3;
                    this.previousSlot = var2;
                    this.previousSize = var3 != null ? var3.stackSize : -1;
                }
            }
            else
            {
                this.previousStack = null;
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        long var1 = System.currentTimeMillis();

        if (var1 >= timeout)
        {
            timeout = var1 + 100L;

            try
            {
                if (infiniteStack && Minecraft.getMinecraft().isSingleplayer())
                {
                    this.doInfiniteStack();
                }

                if (preventRain && TMIGame.isRaining())
                {
                    TMIGame.setRaining(false);
                }
            }
            catch (Throwable var4)
            {
                if (!quietErrors)
                {
                    TMIDebug.reportException(var4);
                    quietErrors = true;
                }
            }
        }
    }

    protected void entityInit() {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
}