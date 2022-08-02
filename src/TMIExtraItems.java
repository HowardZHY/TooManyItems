package net.minecraft.src;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class TMIExtraItems
{
    public static List<ItemStack> items()
    {
        ArrayList var0 = new ArrayList();
        ItemStack var2 = (new TMIStackBuilder("bow")).stack();
        ItemStack var3 = (new TMIStackBuilder("stone_sword")).stack();
        var0.add((new TMIFireworkBuilder()).flight(1).explosion(3, new int[] {4312372}, new int[] {15435844}, true, false).firework().name("Creepy Sparkler").stack());
        var0.add((new TMIFireworkBuilder()).flight(4).explosion(2, new int[] {6719955}, (int[])null, false, false).firework().name("Star").stack());
        var0.add((new TMIFireworkBuilder()).explosion(1, new int[] {11743532}, (int[])null, false, false).firework().name("Big Red").stack());
        var0.add((new TMIFireworkBuilder()).explosion(1, new int[] {11743532, 15790320, 2437522}, new int[] {15790320}, true, true).firework().name("Old Glory").stack());
        String[] var4 = new String[] {"FallingSand", "Boat", "Creeper", "Skeleton", "Spider", "Giant", "Zombie", "Slime", "Ghast", "PigZombie", "Enderman", "CaveSpider", "Silverfish", "Blaze", "LavaSlime", "EnderDragon", "WitherBoss", "Bat", "Witch", "Endermite", "Guardian", "Pig", "Sheep", "Cow", "Chicken", "Squid", "Wolf", "MushroomCow", "SnowMan", "Ozelot", "VillagerGolem", "Rabbit"};
        String[] var5 = var4;
        int var6 = var4.length;
        int var7;

        for (var7 = 0; var7 < var6; ++var7)
        {
            String var8 = var5[var7];
            var0.add((new TMISpawnerBuilder(var8)).name(var8 + " Spawner").stack());
        }

        var0.add((new TMISpawnerBuilder("Creeper")).data("powered", true).name("Charged Creeper Spawner").stack());
        var0.add((new TMISpawnerBuilder("Pig")).data("Saddle", true).name("Saddled Pig Spawner").stack());
        var0.add((new TMISpawnerBuilder("Skeleton")).data("SkeletonType", (byte)1).equipment(new ItemStack[] {var3}).name("Wither Skeleton Spawner").stack());
        var0.add((new TMISpawnerBuilder("Zombie")).data("IsVillager", true).name("Zombie Villager Spawner").stack());
        var0.add((new TMISpawnerBuilder("Zombie")).data("IsBaby", true).name("Zombie Baby Spawner").stack());
        var0.add((new TMISpawnerBuilder("Zombie")).data("IsVillager", true).data("IsBaby", true).name("Zombie Baby Villager Spawner").stack());
        NBTTagCompound var9 = new NBTTagCompound();
        var9.setString("id", "Spider");
        var0.add((new TMISpawnerBuilder("Skeleton")).data("Riding", (NBTBase)var9).equipment(new ItemStack[] {var2}).name("Spider Jockey Spawner").stack());
        var0.add((new TMISpawnerBuilder("Skeleton")).data("SkeletonType", (byte)1).data("Riding", (NBTBase)var9).equipment(new ItemStack[] {var2}).name("Wither Jockey Spawner").stack());
        String[] var10 = new String[] {"Farmer", "Librarian", "Priest", "Blacksmith", "Butcher", "Generic"};

        for (var7 = 0; var7 < var10.length; ++var7)
        {
            var0.add((new TMISpawnerBuilder("Villager")).data("Profession", var7).name("Village " + var10[var7] + " Spawner").stack());
        }

        String[] var11 = new String[] {"Horse", "Donkey", "Mule", "Zombie Horse", "Skeleton Horse"};

        for (int var12 = 0; var12 < var11.length; ++var12)
        {
            var0.add((new TMISpawnerBuilder("EntityHorse")).data("Age", (int)6000).data("Type", var12).name(var11[var12] + " Spawner").stack());
        }

        var0.add((new TMISpawnerBuilder("FallingSand")).data("Block", "torch").data("Time", (byte)2).data("DropItem", false).data("HurtEntities", false).attr("MinSpawnDelay", 15).attr("MaxSpawnDelay", 15).attr("SpawnCount", 10).attr("MaxNearbyEntities", 15).attr("RequiredPlayerRange", 16).attr("SpawnRange", 136).name("TMI Wide-Area Torch Spawner").lore("It\'s full of torches!").stack());
        var0.add(TMISpawnerBuilder.randomFireworkSpawner(1));
        return var0;
    }
}