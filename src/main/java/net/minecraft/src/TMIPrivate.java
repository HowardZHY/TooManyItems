package net.minecraft.src;
import java.lang.reflect.Field;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.EntityList;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import org.lwjgl.input.Mouse;

public class TMIPrivate
{
    public static Field textFieldWidth = getPrivateField(GuiTextField.class, "i", "field_146218_h");
    public static Field textFieldHeight = getPrivateField(GuiTextField.class, "j", "field_146219_i");
    public static Field lwjglMouseEventDWheel = getPrivateField(Mouse.class, "event_dwheel", "event_dwheel");
    public static Field lwjglMouseDWheel = getPrivateField(Mouse.class, "dwheel", "dwheel");
    public static Field creativeSearchBox = getPrivateField(GuiContainerCreative.class, "A", "field_147062_A");
    public static Field entityIdClassMap = getPrivateField(EntityList.class, "e", "field_75623_d");
    public static Field shapedRecipeWidth = getPrivateField(ShapedRecipes.class, "a", "field_77576_b");
    public static Field shapedRecipeHeight = getPrivateField(ShapedRecipes.class, "b", "field_77577_c");
    public static Field shapedRecipeInputs = getPrivateField(ShapedRecipes.class, "c", "field_77574_d");
    public static Field shapelessRecipeInputs = getPrivateField(ShapelessRecipes.class, "b", "field_77579_b");

    private static Field getPrivateField(Class p_getPrivateField_0_, String p_getPrivateField_1_, String p_getPrivateField_2_)
    {
        try
        {
            Field var3;

            try
            {
                var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_1_);
            }
            catch (NoSuchFieldException var5)
            {
                var3 = p_getPrivateField_0_.getDeclaredField(p_getPrivateField_2_);
            }

            var3.setAccessible(true);
            Field var4 = Field.class.getDeclaredField("modifiers");
            var4.setAccessible(true);
            var4.setInt(var3, var3.getModifiers() & -17);
            return var3;
        }
        catch (Throwable var6)
        {
            TMIDebug.reportException(var6);
            return null;
        }
    }
}