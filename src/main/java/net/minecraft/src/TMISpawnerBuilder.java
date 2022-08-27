package net.minecraft.src;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

class TMISpawnerBuilder extends TMIStackBuilder
{
    public static final Random random = new Random();

    public TMISpawnerBuilder()
    {
        super("mob_spawner");
    }

    public TMISpawnerBuilder(String p_i46395_1_)
    {
        super("mob_spawner");
        this.entityID(p_i46395_1_);
    }

    public TMISpawnerBuilder(TMISpawnerBuilder p_i46396_1_)
    {
        super(p_i46396_1_.stack());
    }

    public NBTTagCompound spawnData()
    {
        return getTagWithCreate(this.blockEntity(), "SpawnData");
    }

    public NBTTagList spawnPotentials()
    {
        return getTagListWithCreate(this.blockEntity(), "SpawnPotentials");
    }

    public short attr(String p_attr_1_)
    {
        return this.blockEntity().getShort(p_attr_1_);
    }

    public TMISpawnerBuilder attr(String p_attr_1_, int p_attr_2_)
    {
        this.blockEntity().setShort(p_attr_1_, (short)p_attr_2_);
        return this;
    }

    public String entityID()
    {
        return this.blockEntity().getString("EntityId");
    }

    public TMISpawnerBuilder entityID(String p_entityID_1_)
    {
        this.blockEntity().setString("EntityId", p_entityID_1_);
        return this;
    }

    public TMISpawnerBuilder weight(int p_weight_1_)
    {
        this.bePotential();
        this.spawnPotentials().getCompoundTagAt(0).setInteger("Weight", p_weight_1_);
        return this;
    }

    public TMISpawnerBuilder addPotential(int p_addPotential_1_, TMISpawnerBuilder p_addPotential_2_)
    {
        this.bePotential();
        this.addPotential(p_addPotential_1_, p_addPotential_2_, false);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, boolean p_data_2_)
    {
        this.spawnData().setBoolean(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, byte p_data_2_)
    {
        this.spawnData().setByte(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, int p_data_2_)
    {
        this.spawnData().setInteger(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, NBTBase p_data_2_)
    {
        this.spawnData().setTag(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, String p_data_2_)
    {
        this.spawnData().setString(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder equipment(ItemStack ... p_equipment_1_)
    {
        NBTTagList var2 = getTagListWithCreate(this.spawnData(), "Equipment");

        for (int var3 = 0; var3 < 5; ++var3)
        {
            if (var3 < p_equipment_1_.length && p_equipment_1_[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                p_equipment_1_[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
            else
            {
                var2.appendTag(new NBTTagCompound());
            }
        }

        return this;
    }

    public static ItemStack randomFireworkSpawner(int p_randomFireworkSpawner_0_)
    {
        TMISpawnerBuilder var1 = null;

        for (int var2 = 0; var2 < 10; ++var2)
        {
            TMISpawnerBuilder var3 = (new TMISpawnerBuilder("FireworksRocketEntity")).attr("MinSpawnDelay", 20).attr("MaxSpawnDelay", 20).attr("SpawnCount", 1).attr("MaxNearbyEntities", 5).attr("RequiredPlayerRange", 120).attr("SpawnRange", 2);
            var3.data("FireworksItem", (NBTBase)TMIFireworkBuilder.randomFirework().stack().writeToNBT(new NBTTagCompound()));
            var3.data("LifeTime", random.nextInt(15) + random.nextInt(15) + 20);

            if (var2 == 0)
            {
                var1 = var3;
            }
            else
            {
                var1.addPotential(1, var3);
            }
        }

        return var1.name("TMI Random Firework Spawner").lore("Every one is different!").amount(p_randomFireworkSpawner_0_).stack();
    }

    private void addPotential(int p_addPotential_1_, TMISpawnerBuilder p_addPotential_2_, boolean p_addPotential_3_)
    {
        if (!p_addPotential_3_)
        {
            this.bePotential();
        }

        NBTTagCompound var4 = new NBTTagCompound();
        var4.setInteger("Weight", p_addPotential_1_);
        var4.setString("Type", p_addPotential_2_.blockEntity().getString("EntityId"));
        var4.setTag("Properties", p_addPotential_2_.spawnData().copy());
        this.spawnPotentials().appendTag(var4);
    }

    private void bePotential()
    {
        NBTTagList var1 = this.spawnPotentials();

        if (var1.tagCount() == 0)
        {
            this.addPotential(1, this, true);
        }
    }
}