
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import org.lwjgl.opengl.GL11;

public class TMITextField extends TMIArea
{
    public static final int LINE_HEIGHT = 12;
    public String placeholder = "";
    protected GuiTextField textField;

    public TMITextField()
    {
        this.textField = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 0, 0);
        this.setSize(40, 12);
    }

    public void drawComponent()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        try
        {
            this.textField.xPosition = this.x;
            this.textField.yPosition = this.y;
            TMIPrivate.textFieldWidth.setInt(this.textField, this.getWidth());
            TMIPrivate.textFieldHeight.setInt(this.textField, this.getHeight());
            this.textField.drawTextBox();

            if ((this.value() == null || this.value().equals("")) && !this.isFocused())
            {
                TMIDrawing.drawText(this.getX() + 3, this.getY() + 3, this.placeholder, -7829368);
            }
        }
        catch (IllegalAccessException var2)
        {
            TMIDebug.logWithError("Drawing text field", var2);
        }
    }

    public void focus()
    {
        super.focus();

        try
        {
            GuiScreen var1 = Minecraft.getMinecraft().currentScreen;

            if (var1 instanceof GuiContainerCreative)
            {
                GuiTextField var2 = (GuiTextField)TMIPrivate.creativeSearchBox.get(var1);
                var2.setCanLoseFocus(true);
                var2.setFocused(false);
            }
        }
        catch (Exception var3)
        {
            TMIDebug.logWithError("Removing focus from creative search", var3);
        }

        this.textField.setFocused(true);
    }

    public void blur()
    {
        super.blur();
        this.textField.setFocused(false);
    }

    public String value()
    {
        return this.textField.getText();
    }

    public void setValue(String p_setValue_1_)
    {
        this.textField.setText(p_setValue_1_);
        this.textField.setSelectionPos(0);

        if (this.isFocused())
        {
            this.textField.setCursorPositionEnd();
        }
        else
        {
            this.textField.setCursorPositionZero();
        }

        this.emit(TMIEvent.controlEvent(3, this));
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.mouseButton == 0)
        {
            this.focus();
            this.textField.mouseClicked(p_mouseEvent_1_.x, p_mouseEvent_1_.y, p_mouseEvent_1_.mouseButton);
            p_mouseEvent_1_.cancel();
        }
    }

    public void keyboardEvent(TMIEvent p_keyboardEvent_1_)
    {
        if (p_keyboardEvent_1_.keyCode != 1 && this.isFocused())
        {
            String var2 = this.value();
            this.textField.textboxKeyTyped(p_keyboardEvent_1_.key, p_keyboardEvent_1_.keyCode);
            this.emit(p_keyboardEvent_1_);
            p_keyboardEvent_1_.cancel();

            if (!var2.equals(this.value()))
            {
                this.emit(TMIEvent.controlEvent(3, this));
            }
        }
    }
}
