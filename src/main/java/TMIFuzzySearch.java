
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class TMIFuzzySearch
{
    private List<ItemStack> baseItems;
    private String lastQuery;
    private Deque<List<TMISearchResult>> resultStack = new LinkedList();

    public TMIFuzzySearch(List<ItemStack> p_i46387_1_)
    {
        this.baseItems = p_i46387_1_;
    }

    public List<ItemStack> query(String p_query_1_)
    {
        if (p_query_1_ != null && !p_query_1_.equals(""))
        {
            int var2;

            if (this.lastQuery != null && this.lastQuery.startsWith(p_query_1_))
            {
                for (var2 = 0; var2 < this.lastQuery.length() - p_query_1_.length(); ++var2)
                {
                    this.resultStack.pollLast();
                }

                return this.getResults();
            }
            else
            {
                var2 = 0;

                if (this.lastQuery != null && p_query_1_.startsWith(this.lastQuery))
                {
                    var2 = this.lastQuery.length();
                }
                else
                {
                    while (true)
                    {
                        if (this.resultStack.pollLast() != null)
                        {
                            continue;
                        }
                    }
                }

                for (int var3 = var2; var3 < p_query_1_.length(); ++var3)
                {
                    this.pushQuery(p_query_1_.charAt(var3));
                }

                return this.getResults();
            }
        }
        else
        {
            return this.baseItems;
        }
    }

    public List<ItemStack> getResults()
    {
        ArrayList var1 = new ArrayList();
        List var2 = (List)this.resultStack.peekLast();

        if (var2 == null)
        {
            return null;
        }
        else
        {
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                TMISearchResult var4 = (TMISearchResult)var3.next();
                var1.add(var4.stack);
            }

            return var1;
        }
    }

    private void pushQuery(char p_pushQuery_1_)
    {
        ArrayList var2 = new ArrayList();
        List var3 = (List)this.resultStack.peekLast();
        Iterator var4;
        TMISearchResult var6;

        if (var3 == null)
        {
            var4 = this.baseItems.iterator();

            while (var4.hasNext())
            {
                ItemStack var5 = (ItemStack)var4.next();
                var6 = TMISearchResult.scan(var5, p_pushQuery_1_);

                if (var6 != null)
                {
                    var2.add(var6);
                }
            }
        }
        else
        {
            var4 = var3.iterator();

            while (var4.hasNext())
            {
                TMISearchResult var7 = (TMISearchResult)var4.next();
                var6 = var7.scan(p_pushQuery_1_);

                if (var6 != null)
                {
                    var2.add(var6);
                }
            }
        }

        Collections.sort(var2);
        this.resultStack.addLast(var2);
    }
}
