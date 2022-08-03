
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TMISorting
{
    public static void sortByCreativeTab(List<ItemStack> p_sortByCreativeTab_0_)
    {
        Collections.sort(p_sortByCreativeTab_0_, new Comparator()
        {
            public int compare(ItemStack p_compare_1_, ItemStack p_compare_2_)
            {
                int var3 = 255;
                int var4 = 255;

                try
                {
                    var3 = p_compare_1_.getItem().getCreativeTab().getTabIndex();
                }
                catch (NullPointerException var8)
                {
                    ;
                }

                try
                {
                    var4 = p_compare_2_.getItem().getCreativeTab().getTabIndex();
                }
                catch (NullPointerException var7)
                {
                    ;
                }

                int var5 = Item.getIdFromItem(p_compare_1_.getItem());
                int var6 = Item.getIdFromItem(p_compare_2_.getItem());
                return var3 == var4 ? (var5 == var6 ? (p_compare_1_.getItemDamage() > p_compare_2_.getItemDamage() ? 1 : -1) : (var5 > var6 ? 1 : -1)) : (var3 > var4 ? 1 : -1);
            }
            public int compare(Object p_compare_1_, Object p_compare_2_)
            {
                return this.compare((ItemStack)p_compare_1_, (ItemStack)p_compare_2_);
            }
        });
    }
}
