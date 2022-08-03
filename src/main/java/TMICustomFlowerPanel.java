
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class TMICustomFlowerPanel extends TMIItemGrid
{
    public static final List<String> plantNames = Arrays.asList(new String[] {"red_flower", "yellow_flower", "red_mushroom", "brown_mushroom", "sapling", "tallgrass", "deadbush", "cactus"});
    private ItemStack subitem = null;
    private int metaMargin = TMIDrawing.getTextWidth("Meta: ");

    public TMICustomFlowerPanel()
    {
        super(getPlantStacks());
        this.TOP = 20;
    }

    private static List<ItemStack> getPlantStacks()
    {
        ArrayList var0 = new ArrayList();
        Iterator var1 = plantNames.iterator();

        while (var1.hasNext())
        {
            String var2 = (String)var1.next();
            Item var3 = (new TMIStackBuilder(var2)).stack().getItem();
            var3.getSubItems(var3, (CreativeTabs)null, var0);
        }

        return var0;
    }

    public ItemStack getItemAtXY(int p_getItemAtXY_1_, int p_getItemAtXY_2_)
    {
        ItemStack var3 = super.getItemAtXY(p_getItemAtXY_1_, p_getItemAtXY_2_);

        if (var3 == null)
        {
            return null;
        }
        else
        {
            TMIStackBuilder var4 = new TMIStackBuilder("flower_pot");
            var4.name(TMIGame.stackName(var4.stack()) + " (" + TMIGame.stackName(var3) + ")");
            String var5 = Item.itemRegistry.getNameForObject(var3.getItem()).toString();
            var4.blockEntity().setString("Item", var5);
            var4.blockEntity().setInteger("Data", var3.getItemDamage());
            return var4.stack();
        }
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        super.drawComponent(p_drawComponent_1_, p_drawComponent_2_);
        TMIDrawing.drawText(this.x + 4, this.y + 4, "Flower pot with:");
    }
}
