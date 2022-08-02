package net.minecraft.src;
import java.util.Iterator;
import java.util.LinkedList;

public class TMIDispatch
{
    public static void sendMouseEvent(TMIEvent p_sendMouseEvent_0_, TMIArea p_sendMouseEvent_1_)
    {
        TMIArea var2 = findTopmostContainingXY(p_sendMouseEvent_1_, p_sendMouseEvent_0_.x, p_sendMouseEvent_0_.y);

        if (var2 != null)
        {
            p_sendMouseEvent_0_.target = var2;
            var2.mouseEvent(p_sendMouseEvent_0_);
        }

        TMIArea var3 = var2;

        while (!p_sendMouseEvent_0_.cancel && var3 != null && var3.getParent() != null)
        {
            var3 = var3.getParent();
            var3.mouseEvent(p_sendMouseEvent_0_);
        }
    }

    public static void sendKeypress(TMIEvent p_sendKeypress_0_, TMIArea p_sendKeypress_1_)
    {
        if (p_sendKeypress_1_.focusArea != null)
        {
            p_sendKeypress_0_.target = p_sendKeypress_1_.focusArea;
            p_sendKeypress_1_.focusArea.keyboardEvent(p_sendKeypress_0_);
        }
        else
        {
            p_sendKeypress_1_.keyboardEvent(p_sendKeypress_0_);
        }
    }

    public static void determineMouseover(TMIArea p_determineMouseover_0_, int p_determineMouseover_1_, int p_determineMouseover_2_)
    {
        TMIArea var3 = findTopmostContainingXY(p_determineMouseover_0_, p_determineMouseover_1_, p_determineMouseover_2_);
        p_determineMouseover_0_.mouseover(var3);
    }

    public static TMIArea findTopmostContainingXY(TMIArea p_findTopmostContainingXY_0_, int p_findTopmostContainingXY_1_, int p_findTopmostContainingXY_2_)
    {
        LinkedList var3 = new LinkedList();
        TMIArea var4 = null;
        var3.offer(p_findTopmostContainingXY_0_);

        while (!var3.isEmpty())
        {
            TMIArea var5 = (TMIArea)var3.remove();

            if (var5.visible() && var5.contains(p_findTopmostContainingXY_1_, p_findTopmostContainingXY_2_))
            {
                if (var4 == null || var4.getZ() <= var5.getZ())
                {
                    var4 = var5;
                }

                if (var5.hasChildren())
                {
                    Iterator var6 = var5.children().iterator();

                    while (var6.hasNext())
                    {
                        TMIArea var7 = (TMIArea)var6.next();
                        var3.offer(var7);
                    }
                }
            }
        }

        return var4;
    }
}