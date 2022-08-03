
import org.lwjgl.opengl.GL11;

public class TMINumField extends TMITextField
{
    public String label;
    public int labelColor;
    public int inputWidth;

    public TMINumField(String p_i46393_1_)
    {
        this(p_i46393_1_, 30);
    }

    public TMINumField(String p_i46394_1_, int p_i46394_2_)
    {
        this.labelColor = -1;
        this.inputWidth = 30;
        this.label = p_i46394_1_;
        this.inputWidth = p_i46394_2_;
    }

    public void drawComponent()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        TMIDrawing.drawText(this.x, this.y + 1, TMIDrawing.cutTextToWidth(this.label, this.width - this.inputWidth - 2), this.labelColor);

        try
        {
            this.textField.xPosition = this.x + this.width - this.inputWidth - 2;
            this.textField.yPosition = this.y;
            TMIPrivate.textFieldWidth.setInt(this.textField, this.inputWidth);
            TMIPrivate.textFieldHeight.setInt(this.textField, 12);
            this.textField.drawTextBox();
        }
        catch (IllegalAccessException var2)
        {
            TMIDebug.logWithError("Drawing text field", var2);
        }
    }

    public int intValue()
    {
        if (this.value().equals(""))
        {
            return 0;
        }
        else
        {
            try
            {
                return Integer.parseInt(this.value());
            }
            catch (NumberFormatException var2)
            {
                TMIDebug.reportException(var2);
                return 0;
            }
        }
    }

    public void setValue(String p_setValue_1_)
    {
        try
        {
            Integer.parseInt(p_setValue_1_);
            super.setValue(p_setValue_1_);
        }
        catch (NumberFormatException var3)
        {
            ;
        }
    }

    public void keyboardEvent(TMIEvent p_keyboardEvent_1_)
    {
        if (p_keyboardEvent_1_.keyCode == 14 || p_keyboardEvent_1_.keyCode == 211 || p_keyboardEvent_1_.keyCode == 15 || p_keyboardEvent_1_.key >= 48 && p_keyboardEvent_1_.key <= 57)
        {
            super.keyboardEvent(p_keyboardEvent_1_);
        }
    }
}
