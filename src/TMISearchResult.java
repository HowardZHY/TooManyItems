package net.minecraft.src;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class TMISearchResult implements Comparable<TMISearchResult>
{
    public ItemStack stack;
    protected String normalizedName;
    protected int internalDistance;
    protected int firstPosition;
    protected int lastPosition;
    protected static Pattern diacritics = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    protected static Pattern styles = Pattern.compile("\u00a7.");

    public static TMISearchResult scan(ItemStack p_scan_0_, char p_scan_1_)
    {
        if (p_scan_0_ == null)
        {
            return null;
        }
        else
        {
            List var2 = p_scan_0_.getTooltip(Minecraft.getMinecraft().thePlayer, true);

            if (var2 != null && var2.size() != 0)
            {
                String var3 = clean((String)var2.get(0));
                p_scan_1_ = Character.toLowerCase(p_scan_1_);
                int var4 = var3.indexOf(p_scan_1_);

                if (var4 == -1)
                {
                    return null;
                }
                else
                {
                    TMISearchResult var5 = new TMISearchResult();
                    var5.stack = p_scan_0_;
                    var5.firstPosition = var5.lastPosition = var4;
                    var5.internalDistance = 0;
                    var5.normalizedName = var3;
                    return var5;
                }
            }
            else
            {
                return null;
            }
        }
    }

    public TMISearchResult scan(char p_scan_1_)
    {
        p_scan_1_ = Character.toLowerCase(p_scan_1_);
        int var2 = this.normalizedName.indexOf(p_scan_1_, this.lastPosition + 1);

        if (var2 == -1)
        {
            return null;
        }
        else
        {
            TMISearchResult var3 = new TMISearchResult();
            var3.stack = this.stack;
            var3.normalizedName = this.normalizedName;
            var3.firstPosition = this.firstPosition;
            var3.lastPosition = var2;
            var3.internalDistance = this.internalDistance + var2 - this.lastPosition;
            return var3;
        }
    }

    public int compareTo(TMISearchResult p_compareTo_1_)
    {
        return this.internalDistance > p_compareTo_1_.internalDistance ? 1 : (this.internalDistance < p_compareTo_1_.internalDistance ? -1 : (this.firstPosition > p_compareTo_1_.firstPosition ? 1 : (this.firstPosition < p_compareTo_1_.firstPosition ? -1 : 0)));
    }

    public static String clean(String p_clean_0_)
    {
        p_clean_0_ = deaccent(p_clean_0_);
        p_clean_0_ = p_clean_0_.toLowerCase();
        p_clean_0_ = styles.matcher(p_clean_0_).replaceAll("");
        return p_clean_0_;
    }

    public static String deaccent(String p_deaccent_0_)
    {
        try
        {
            Class.forName("java.text.Normalizer");
        }
        catch (ClassNotFoundException var2)
        {
            return p_deaccent_0_;
        }

        String var1 = Normalizer.normalize(p_deaccent_0_, Form.NFD);
        return diacritics.matcher(var1).replaceAll("");
    }

    public int compareTo2(TMISearchResult p_compareTo_1_)
    {
        return this.compareTo2((TMISearchResult)p_compareTo_1_);
    }
}