package net.minecraft.src;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;

public class TMIGame
{
    public static final int MODE_SURVIVAL = 0;
    public static final int MODE_CREATIVE = 1;
    public static final int MODE_ADVENTURE = 2;
    public static final int MODE_SPECTATOR = 3;
    public static final int TIME_SUNRISE = 0;
    public static final int TIME_NOON = 6000;
    public static final int TIME_MOONRISE = 13000;
    public static final int TIME_MIDNIGHT = 18000;

    public static EntityPlayer getPlayerClient()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static EntityPlayer getPlayerServer()
    {
        return MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(Minecraft.getMinecraft().thePlayer.getName());
    }

    public static void chat(String p_chat_0_)
    {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(p_chat_0_);
    }

    public static boolean isPlayerAlive()
    {
        return !Minecraft.getMinecraft().thePlayer.isDead;
    }

    public static void fillPlayerHealth()
    {
        if (isPlayerAlive())
        {
            getPlayerServer().setHealth(20.0F);
        }
    }

    public static void fillPlayerHunger()
    {
        if (isPlayerAlive())
        {
            getPlayerServer().getFoodStats().setFoodLevel(20);
            getPlayerServer().getFoodStats().setFoodSaturationLevel(5.0F);
        }
    }

    public static void removePlayerEffects()
    {
        getPlayerServer().clearActivePotions();
    }

    public static ItemStack[] getInventoryForPlayer(EntityPlayer p_getInventoryForPlayer_0_)
    {
        return p_getInventoryForPlayer_0_.inventory.mainInventory;
    }

    public static ItemStack[] getArmorForPlayer(EntityPlayer p_getArmorForPlayer_0_)
    {
        return p_getArmorForPlayer_0_.inventory.armorInventory;
    }

    public static ItemStack getHeldItem()
    {
        return Minecraft.getMinecraft().thePlayer.inventory.getItemStack();
    }

    public static void setHeldItem(ItemStack p_setHeldItem_0_)
    {
        getPlayerServer().inventory.setItemStack(p_setHeldItem_0_);
        getPlayerClient().inventory.setItemStack(p_setHeldItem_0_);
    }

    public static void giveStack(ItemStack p_giveStack_0_)
    {
        if (p_giveStack_0_ == null)
        {
            System.out.println("[TMI] Tried to give null item");
        }
        else
        {
            if (stackName(p_giveStack_0_).contains("Random Firework"))
            {
                p_giveStack_0_ = TMISpawnerBuilder.randomFireworkSpawner(1);
            }

            if (isMultiplayer())
            {
                String var1 = "";

                if (p_giveStack_0_.getTagCompound() != null)
                {
                    var1 = p_giveStack_0_.getTagCompound().toString();
                }

                String var2 = String.format("/give %s %s %d %d %s", new Object[] {Minecraft.getMinecraft().thePlayer.getName(), Item.itemRegistry.getNameForObject(p_giveStack_0_.getItem()), Integer.valueOf(p_giveStack_0_.stackSize), Integer.valueOf(p_giveStack_0_.getItemDamage()), var1});
                var2 = var2.replaceAll("\u00a7.", "");
                System.out.println(var2);

                if (var2.length() > 512)
                {
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[TMI] give command exceeds length limit - blame Mojang"));
                    return;
                }

                chat(var2);
            }
            else
            {
                getPlayerServer().inventory.addItemStackToInventory(p_giveStack_0_.copy());
                Minecraft.getMinecraft().thePlayer.inventory.addItemStackToInventory(p_giveStack_0_.copy());
            }
        }
    }

    public static ItemStack copyStack(ItemStack p_copyStack_0_)
    {
        return p_copyStack_0_ == null ? null : p_copyStack_0_.copy();
    }

    public static void deleteInventory()
    {
        if (isMultiplayer())
        {
            chat("/clear");
        }
        else
        {
            ItemStack[] var0 = getInventoryForPlayer(getPlayerServer());
            ItemStack[] var1 = getInventoryForPlayer(getPlayerClient());

            for (int var2 = 0; var2 < var0.length; ++var2)
            {
                var0[var2] = null;
                var1[var2] = null;
            }

            ItemStack[] var5 = getArmorForPlayer(getPlayerServer());
            ItemStack[] var3 = getArmorForPlayer(getPlayerClient());

            for (int var4 = 0; var4 < var5.length; ++var4)
            {
                var5[var4] = null;
                var3[var4] = null;
            }
        }
    }

    public static boolean isMultiplayer()
    {
        return !Minecraft.getMinecraft().isSingleplayer();
    }

    public static boolean isModloader()
    {
        try
        {
            Class.forName("ModLoader");
            return true;
        }
        catch (ClassNotFoundException var1)
        {
            return false;
        }
    }

    public static boolean isForge()
    {
        try
        {
            Class.forName("net.minecraftforge.common.MinecraftForge");
            return true;
        }
        catch (ClassNotFoundException var1)
        {
            return false;
        }
    }

    public static List<ItemStack> gameItems()
    {
        ArrayList var0 = new ArrayList();
        Iterator var1 = Item.itemRegistry.iterator();

        while (var1.hasNext())
        {
            Item var2 = (Item)var1.next();
            var2.getSubItems(var2, (CreativeTabs)null, var0);
        }

        return var0;
    }

    public static List<ItemStack> allItems()
    {
        List var0 = gameItems();
        var0.addAll(TMIExtraItems.items());
        return var0;
    }

    public static int getGameMode()
    {
        return Minecraft.getMinecraft().playerController.getCurrentGameType().getID();
    }

    public static void setGameMode(int p_setGameMode_0_)
    {
        if (isMultiplayer())
        {
            chat("/gamemode " + p_setGameMode_0_);
        }
        else
        {
            getPlayerServer().setGameType(WorldSettings.GameType.getByID(p_setGameMode_0_));
        }
    }

    public static boolean isRaining()
    {
        return Minecraft.getMinecraft().theWorld.getWorldInfo().isRaining();
    }

    public static void setRaining(boolean p_setRaining_0_)
    {
        if (isMultiplayer())
        {
            chat("/weather " + (p_setRaining_0_ ? "rain" : "clear"));
        }
        else
        {
            MinecraftServer.getServer().worldServers[0].getWorldInfo().setRaining(p_setRaining_0_);
            MinecraftServer.getServer().worldServers[0].getWorldInfo().setRainTime(p_setRaining_0_ ? 18000 : 180000);
        }
    }

    public static void toggleRaining()
    {
        setRaining(!isRaining());
    }

    public static long getTime()
    {
        return Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTotalTime();
    }

    public static void setTime(long p_setTime_0_)
    {
        if (isMultiplayer())
        {
            chat("/time set " + p_setTime_0_);
        }
        else
        {
            MinecraftServer.getServer().worldServers[0].setWorldTime(p_setTime_0_);
        }
    }

    public static int getDifficulty()
    {
        return Minecraft.getMinecraft().theWorld.getWorldInfo().getDifficulty().getDifficultyId();
    }

    public static void setDifficulty(int p_setDifficulty_0_)
    {
        if (isMultiplayer())
        {
            chat("/difficulty " + p_setDifficulty_0_);
        }
        else
        {
            MinecraftServer.getServer().setDifficultyForAllWorlds(EnumDifficulty.getDifficultyEnum(p_setDifficulty_0_));
        }
    }

    public static void incrementDifficulty()
    {
        setDifficulty(getDifficulty() + 1);
    }

    public static String getDifficultyName()
    {
        return I18n.format(Minecraft.getMinecraft().theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]);
    }

    public static void setCheats(boolean p_setCheats_0_)
    {
        MinecraftServer.getServer().worldServers[0].getWorldInfo().setAllowCommands(p_setCheats_0_);
    }

    public static boolean getCheats()
    {
        return MinecraftServer.getServer().worldServers[0].getWorldInfo().areCommandsAllowed();
    }

    public static boolean getKeepInventory()
    {
        return isMultiplayer() ? false : MinecraftServer.getServer().worldServers[0].getWorldInfo().getGameRulesInstance().getBoolean("keepInventory");
    }

    public static void setKeepInventory(boolean p_setKeepInventory_0_)
    {
        if (isMultiplayer())
        {
            chat("/gamerule keepInventory " + (p_setKeepInventory_0_ ? "true" : "false"));
        }
        else
        {
            MinecraftServer.getServer().worldServers[0].getWorldInfo().getGameRulesInstance().addGameRule("keepInventory", p_setKeepInventory_0_ ? "true" : "false", GameRules.ValueType.BOOLEAN_VALUE);
        }
    }

    public static String stackName(ItemStack p_stackName_0_)
    {
        List var1 = p_stackName_0_.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        return var1 != null && var1.size() != 0 ? (String)var1.get(0) : "Unnamed";
    }
}