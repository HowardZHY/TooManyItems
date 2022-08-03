package net.minecraft.src;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class TMICustomNotePanel extends TMIItemGrid
{
    public static final String[] notes = new String[] {"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};
    private int counter;

    public TMICustomNotePanel()
    {
        TMIStackBuilder var1 = new TMIStackBuilder("noteblock");
        this.items = new ArrayList();

        for (int var2 = 0; var2 <= 24; ++var2)
        {
            var1.blockEntity().setByte("note", (byte)var2);
            var1.name("\u00a7r\u00a79Note Block (" + var2 + ", " + notes[var2] + ")");
            this.items.add(var1.stack());
        }

        this.TOP = 10;
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        this.counter = 0;
        super.drawComponent(p_drawComponent_1_, p_drawComponent_2_);
    }

    protected void drawItem(int p_drawItem_1_, int p_drawItem_2_, ItemStack p_drawItem_3_)
    {
        TMIDrawing.drawItem(p_drawItem_1_, p_drawItem_2_, p_drawItem_3_);
        TMIDrawing.drawTextCentered(p_drawItem_1_, p_drawItem_2_, 16, 16, "" + this.counter, -1, 2);
        ++this.counter;
    }
}