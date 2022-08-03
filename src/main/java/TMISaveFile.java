
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TMISaveFile extends NBTTagList
{
    public static final String filename = "TMI.nbt";
    public static final int INVENTORY_SIZE = 44;
    public static final List<ItemStack> favorites = new ArrayList();
    private static ItemStack[][] states = new ItemStack[7][44];
    public static boolean[] statesSaved = new boolean[7];

    public static void Start()
    {
        try{
            Class NBTTagList = Class.forName("net.minecraft.nbt.NBTTagList");
            Method method = NBTTagList.getDeclaredMethod("read");
            method.setAccessible(true);
        } catch (Throwable var10){
                TMIDebug.reportException(var10);
        }
    } 

    public static File file()
    {
        return new File(Minecraft.getMinecraft().mcDataDir, "TMI.nbt");
    }

    public static boolean read()
    {
        try
        {
            File var0 = file();

            if (!var0.exists())
            {
                return false;
            }
            else
            {
                DataInputStream var1 = new DataInputStream(new FileInputStream(var0));
                NBTTagList var2 = new NBTTagList();
                var2.read(var1, 1, new NBTSizeTracker(2097152L));
                int var3 = var2.tagCount();

                if (var3 == 0)
                {
                    return false;
                }
                else
                {
                    favorites.clear();
                    NBTTagList var4 = (NBTTagList)var2.get(0);
                    int var5;

                    for (var5 = 0; var5 < var4.tagCount(); ++var5)
                    {
                        ItemStack var6 = new ItemStack((Item)null);
                        var6.readFromNBT((NBTTagCompound)var4.get(var5));
                        favorites.add(var6);
                    }

                    for (var5 = 0; var5 < var3 - 1 && var5 < states.length; ++var5)
                    {
                        NBTTagList var11 = (NBTTagList)var2.get(var5 + 1);

                        if (var11.tagCount() != 0)
                        {
                            ItemStack[] var7 = states[var5];

                            for (int var8 = 0; var8 < var11.tagCount(); ++var8)
                            {
                                NBTTagCompound var9 = var11.getCompoundTagAt(var8);

                                if (var9.hasKey("id"))
                                {
                                    var7[var8] = new ItemStack((Item)null);
                                    var7[var8].readFromNBT(var9);
                                }
                                else
                                {
                                    var7[var8] = null;
                                }
                            }

                            statesSaved[var5] = true;
                        }
                    }

                    return true;
                }
            }
        }
        catch (Throwable var10)
        {
            TMIDebug.reportException(var10);
            return false;
        }
    }

    public static void write()
    {
        try
        {
            NBTTagList var0 = new NBTTagList();
            NBTTagList var1 = new NBTTagList();
            NBTTagCompound var4;

            for (Iterator var2 = favorites.iterator(); var2.hasNext(); var1.appendTag(var4))
            {
                ItemStack var3 = (ItemStack)var2.next();
                var4 = new NBTTagCompound();

                if (var3 != null)
                {
                    var3.writeToNBT(var4);
                }
            }

            var0.appendTag(var1);

            for (int var8 = 0; var8 < states.length; ++var8)
            {
                if (statesSaved[var8])
                {
                    ItemStack[] var10 = states[var8];
                    NBTTagList var11 = new NBTTagList();

                    for (int var5 = 0; var5 < var10.length; ++var5)
                    {
                        NBTTagCompound var6 = new NBTTagCompound();

                        if (var10[var5] != null)
                        {
                            var10[var5].writeToNBT(var6);
                        }

                        var11.appendTag(var6);
                    }

                    var0.appendTag(var11);
                }
                else
                {
                    var0.appendTag(new NBTTagList());
                }
            }

            DataOutputStream var9 = new DataOutputStream(new FileOutputStream(file()));
            var0.write(var9);
        }
        catch (Throwable var7)
        {
            TMIDebug.reportException(var7);
        }
    }

    public static void addFavorite(ItemStack p_addFavorite_0_)
    {
        favorites.add(p_addFavorite_0_);
        write();
    }

    public static void removeFavorite(ItemStack p_removeFavorite_0_)
    {
        favorites.remove(p_removeFavorite_0_);
        write();
    }

    public static void saveState(int p_saveState_0_)
    {
        ItemStack[] var1 = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        ItemStack[] var2 = Minecraft.getMinecraft().thePlayer.inventory.armorInventory;
        int var3;

        for (var3 = 0; var3 < 4; ++var3)
        {
            states[p_saveState_0_][var3 + 4] = TMIGame.copyStack(var2[var3]);
        }

        for (var3 = 0; var3 < 27; ++var3)
        {
            states[p_saveState_0_][var3 + 8] = TMIGame.copyStack(var1[var3 + 9]);
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            states[p_saveState_0_][var3 + 8 + 27] = TMIGame.copyStack(var1[var3]);
        }

        statesSaved[p_saveState_0_] = true;
        write();
    }

    public static void loadState(int p_loadState_0_)
    {
        if (statesSaved[p_loadState_0_])
        {
            ItemStack[] var1 = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
            ItemStack[] var2 = Minecraft.getMinecraft().thePlayer.inventory.armorInventory;
            ItemStack[] var3 = TMIGame.getPlayerServer().inventory.mainInventory;
            ItemStack[] var4 = TMIGame.getPlayerServer().inventory.armorInventory;
            int var5;

            for (var5 = 0; var5 < 4; ++var5)
            {
                var4[var5] = TMIGame.copyStack(states[p_loadState_0_][var5 + 4]);
                var2[var5] = TMIGame.copyStack(states[p_loadState_0_][var5 + 4]);
            }

            for (var5 = 0; var5 < 27; ++var5)
            {
                var3[var5 + 9] = TMIGame.copyStack(states[p_loadState_0_][var5 + 8]);
                var1[var5 + 9] = TMIGame.copyStack(states[p_loadState_0_][var5 + 8]);
            }

            for (var5 = 0; var5 < 9; ++var5)
            {
                var3[var5] = TMIGame.copyStack(states[p_loadState_0_][var5 + 8 + 27]);
                var1[var5] = TMIGame.copyStack(states[p_loadState_0_][var5 + 8 + 27]);
            }
        }
    }

    public static void clearState(int p_clearState_0_)
    {
        statesSaved[p_clearState_0_] = false;
        write();
    }
}
