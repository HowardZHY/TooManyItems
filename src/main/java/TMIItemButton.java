
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class TMIItemButton extends TMIButton
{
    public boolean dropTarget;

    public TMIItemButton(ItemStack p_i46388_1_)
    {
        this(p_i46388_1_, false);
    }

    public TMIItemButton(ItemStack p_i46389_1_, boolean p_i46389_2_)
    {
        this.dropTarget = false;
        this.stack = p_i46389_1_;
        this.itemTooltip = true;
        this.dropTarget = p_i46389_2_;
        this.width = 16;
        this.height = 16;
    }

    public List<String> getTooltip()
    {
        if (this.stack != null)
        {
            List var1 = this.stack.getTooltip(Minecraft.getMinecraft().thePlayer, true);
            var1.add("\u00a7r\u00a7aClick: give stack");
            var1.add("\u00a7r\u00a7aRight-click: give one");
            var1.add("\u00a7r\u00a7aShift-click: add to My Items");

            if (this.dropTarget)
            {
                var1.add("\u00a7r\u00a7aYou can drop an item here");
            }

            return var1;
        }
        else
        {
            return null;
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.mouseButton == 0)
        {
            ItemStack var2 = TMIGame.getHeldItem();

            if (var2 != null && this.dropTarget)
            {
                this.stack = var2.copy();
                this.emit(TMIEvent.controlEvent(4, this));
            }
            else if (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54))
            {
                TMIGame.giveStack((new TMIStackBuilder(this.stack)).maxStack());
            }
            else
            {
                TMISaveFile.addFavorite(this.stack);
            }

            p_mouseEvent_1_.cancel();
        }
        else if (p_mouseEvent_1_.mouseButton == 1)
        {
            TMIGame.giveStack((new TMIStackBuilder(this.stack)).amount(1).stack());
            p_mouseEvent_1_.cancel();
        }
    }
}
