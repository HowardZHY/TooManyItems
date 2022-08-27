package net.minecraft.src;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class TMICustomPotionPanel extends TMIArea
{
    private TMIButton prevColor = new TMIButton((Object)null, "<");
    private TMIButton nextColor = new TMIButton((Object)null, ">");
    private TMIItemButton potionButton = new TMIItemButton((ItemStack)null);
    private TMIItemButton splashButton = new TMIItemButton((ItemStack)null);
    private TMITextField nameField = new TMITextField();
    private TMINumField durField = new TMINumField("Seconds:", 40);
    private List<TMIEffectField> effectList = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 56;
    private int color = 0;
    public static final int DRINKABLE = 8192;
    public static final int SPLASH = 16384;

    public TMICustomPotionPanel()
    {
        this.addChild(this.prevColor);
        this.addChild(this.nextColor);
        this.addChild(this.potionButton);
        this.addChild(this.splashButton);
        this.addChild(this.nameField);
        this.addChild(this.durField);
        this.nameField.addEventListener(this);
        this.durField.addEventListener(this);
        this.nameField.placeholder = "Name...";
        this.durField.setValue("60");

        for (int var1 = 0; var1 < Potion.potionTypes.length; ++var1)
        {
            if (Potion.potionTypes[var1] != null)
            {
                TMIEffectField var2 = new TMIEffectField(Potion.potionTypes[var1]);
                var2.addEventListener(this);
                this.effectList.add(var2);
            }
        }

        this.recreateItem();
    }

    private void recreateItem()
    {
        TMIStackBuilder var1 = new TMIStackBuilder("potion");

        if (!this.nameField.value().equals(""))
        {
            var1.name(this.nameField.value());
        }

        Iterator var2 = this.effectList.iterator();

        while (var2.hasNext())
        {
            TMIEffectField var3 = (TMIEffectField)var2.next();
            int var4 = var3.intValue();
            int var5 = this.durField.intValue();

            if (var4 > 0 && var5 > 0)
            {
                var1.effect(var3.effect, var4 - 1, var5 * 20);
            }
        }

        this.potionButton.stack = var1.meta(8192 | this.color).stack();
        this.splashButton.stack = var1.meta(16384 | this.color).stack();
    }

    public void layoutComponent()
    {
        this.fixPage();
        int var1 = this.x + (this.width - 32 - this.prevColor.width * 2 - 6) / 2;
        this.prevColor.setPosition(var1, this.y + 6);
        this.potionButton.setPosition(var1 + this.prevColor.width + 2, this.y + 4);
        this.splashButton.setPosition(this.potionButton.x + 16 + 2, this.y + 4);
        this.nextColor.setPosition(this.splashButton.x + 16 + 2, this.y + 6);
        this.nameField.setSize(this.width - 6, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        this.durField.setSize(this.width - 4, 12);
        this.durField.setPosition(this.x + 2, this.nameField.y + 14 + 2);
        int var2 = this.y + this.fieldsMargin;
        Iterator var3 = this.children.iterator();

        while (var3.hasNext())
        {
            TMIArea var4 = (TMIArea)var3.next();

            if (var4 instanceof TMIEffectField)
            {
                var4.setSize(this.width - 4, 14);
                var4.setPosition(this.x + 2, var2);
                var2 += 16;
            }
        }
    }

    private void fixPage()
    {
        this.removeChildrenOfType(TMIEffectField.class);
        int var1 = (this.height - this.fieldsMargin - 4) / 16;
        int var2;

        for (var2 = (int)Math.ceil((double)((float)this.effectList.size() / (float)var1)); this.page < 0; this.page += var2)
        {
            ;
        }

        this.page %= var2;
        int var3 = var1 * this.page;

        for (int var4 = var3; var4 < var3 + var1 && var4 < this.effectList.size(); ++var4)
        {
            this.addChild((TMIArea)this.effectList.get(var4));
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.type == 2)
        {
            this.page -= p_mouseEvent_1_.wheel;
            this.layoutComponent();
            p_mouseEvent_1_.cancel();
        }
        else if (p_mouseEvent_1_.mouseButton == 0)
        {
            if (p_mouseEvent_1_.target == this.nextColor)
            {
                this.color = (this.color + 1) % 15;
                this.recreateItem();
            }
            else if (p_mouseEvent_1_.target == this.prevColor)
            {
                --this.color;

                if (this.color < 0)
                {
                    this.color = 15;
                }

                this.recreateItem();
            }
        }
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 3)
        {
            this.recreateItem();
        }
    }

    public void keyboardEvent(TMIEvent p_keyboardEvent_1_)
    {
        if (p_keyboardEvent_1_.keyCode == 15)
        {
            if (this.getFocused() == null)
            {
                this.nameField.focus();
            }
            else
            {
                int var2 = this.children.indexOf(this.getFocused());

                if (var2 == -1)
                {
                    this.nameField.focus();
                }
                else if (var2 + 1 == this.children.size())
                {
                    ++this.page;
                    this.layoutComponent();
                    Iterator var3 = this.children.iterator();

                    while (var3.hasNext())
                    {
                        TMIArea var4 = (TMIArea)var3.next();

                        if (var4 instanceof TMIEffectField)
                        {
                            var4.focus();
                            break;
                        }
                    }
                }
                else
                {
                    ((TMIArea)this.children.get(var2 + 1)).focus();
                }
            }

            p_keyboardEvent_1_.cancel();
        }
    }
}