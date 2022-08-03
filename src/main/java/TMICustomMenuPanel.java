
import java.util.Iterator;

public class TMICustomMenuPanel extends TMIItemGrid
{
    private TMIItemGrid grid = new TMIItemGrid();

    public TMICustomMenuPanel()
    {
        this.addChild((new TMIButton(new TMICustomPotionPanel(), "Potions")).item("potion").center(false));
        this.addChild((new TMIButton(new TMICustomEnchantPanel(), "Enchanting")).item("enchanted_book").center(false));
        this.addChild((new TMIButton(new TMICustomFireworkPanel(), "Fireworks")).item("fireworks").center(false));
        this.addChild((new TMIButton(new TMICustomSignPanel(), "Signs with text")).item("sign").center(false));
        this.addChild((new TMIButton(new TMICustomLeatherPanel(), "Leather dying")).item("leather_chestplate").center(false));
        this.addChild((new TMIButton(new TMICustomHeadPanel(), "Player heads")).item("skull", 3).center(false));
        this.addChild((new TMIButton(new TMICustomNotePanel(), "Note blocks")).item("noteblock").center(false));
        this.addChild((new TMIButton(new TMICustomFlowerPanel(), "Flower pots")).item("flower_pot").center(false));
    }

    public void layoutComponent()
    {
        int var1 = this.y + 4;

        for (Iterator var2 = this.children.iterator(); var2.hasNext(); var1 += 18)
        {
            TMIArea var3 = (TMIArea)var2.next();
            var3.setSize(this.width, 16);
            var3.setPosition(this.x, var1);
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.target instanceof TMIButton)
        {
            TMIEvent var2 = TMIEvent.controlEvent(4, (TMIArea)((TMIButton)p_mouseEvent_1_.target).data);
            this.emit(var2);
        }
    }
}
