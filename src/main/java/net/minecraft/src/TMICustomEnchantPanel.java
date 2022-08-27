package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public class TMICustomEnchantPanel extends TMIArea
{
    private TMIItemButton itemButton = new TMIItemButton((new TMIStackBuilder("diamond_pickaxe")).stack(), true);
    private TMITextField nameField = new TMITextField();
    private List<TMIEnchantField> enchantFields = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 44;

    public TMICustomEnchantPanel()
    {
        this.addChild(this.itemButton);
        this.addChild(this.nameField);
        this.nameField.placeholder = "Name...";
        this.nameField.addEventListener(this);
        this.itemButton.addEventListener(this);
        this.updateFromItem();
    }

    private void updateFromItem()
    {
        this.blurFocused();
        this.enchantFields.clear();
        NBTTagCompound nbttagcompound = (new TMIStackBuilder(this.itemButton.stack)).tag();
        HashMap hashmap = new HashMap();

        if (nbttagcompound.hasKey("ench"))
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("ench", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                try
                {
                    NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.get(i);
                    hashmap.put(Integer.valueOf(nbttagcompound1.getShort("id")), Integer.valueOf(nbttagcompound1.getShort("lvl")));
                }
                catch (Throwable var6)
                {
                    ;
                }
            }
        }

        for (Enchantment enchantment : this.enchantmentsForItem(this.itemButton.stack))
        {
            TMIEnchantField tmienchantfield = new TMIEnchantField(enchantment, this.itemButton.stack);
            tmienchantfield.addEventListener(this);
            this.enchantFields.add(tmienchantfield);

            if (hashmap.containsKey(Integer.valueOf(enchantment.effectId)))
            {
                tmienchantfield.setValue("" + hashmap.get(Integer.valueOf(enchantment.effectId)));
            }
        }
    }

    private void recreateItem()
    {
        TMIStackBuilder tmistackbuilder = (new TMIStackBuilder(this.itemButton.stack)).clearEnch().clearName();

        for (TMIEnchantField tmienchantfield : this.enchantFields)
        {
            int i = tmienchantfield.intValue();

            if (i > 0)
            {
                tmistackbuilder.ench(tmienchantfield.ench, i);
            }
        }

        if (!this.nameField.value().equals(""))
        {
            tmistackbuilder.name(this.nameField.value());
        }

        this.itemButton.stack = tmistackbuilder.stack();
        this.layoutComponent();
    }

    private void fixPage()
    {
        this.removeChildrenOfType(TMIEnchantField.class);
        int i = (this.height - this.fieldsMargin - 4) / 16;
        int j;

        for (j = (int)Math.ceil((double)((float)this.enchantFields.size() / (float)i)); this.page < 0; this.page += j)
        {
            ;
        }

        this.page %= j;
        int k = i * this.page;

        for (int l = k; l < k + i && l < this.enchantFields.size(); ++l)
        {
            this.addChild((TMIArea)this.enchantFields.get(l));
        }
    }

    public void layoutComponent()
    {
        this.fixPage();
        this.itemButton.setPosition(this.x + this.width / 2 - 8, this.y + 4);
        this.nameField.setSize(this.width - 4, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        int i = this.y + this.fieldsMargin;

        for (TMIArea tmiarea : this.children)
        {
            if (tmiarea instanceof TMIEnchantField)
            {
                tmiarea.setSize(this.width - 4, 14);
                tmiarea.setPosition(this.x + 2, i);
                i += 16;
            }
        }
    }

    private List<Enchantment> enchantmentsForItem(final ItemStack p_enchantmentsForItem_1_)
    {
        List<Enchantment> list = Arrays.asList(Enchantment.enchantmentsBookList);
        Collections.sort(list, new Comparator<Enchantment>()
        {
            public int compare(Enchantment p_compare_1_, Enchantment p_compare_2_)
            {
                boolean flag = p_compare_1_.type.canEnchantItem(p_enchantmentsForItem_1_.getItem());
                boolean flag1 = p_compare_2_.type.canEnchantItem(p_enchantmentsForItem_1_.getItem());
                return flag == flag1 ? StatCollector.translateToLocal(p_compare_1_.getName()).compareTo(StatCollector.translateToLocal(p_compare_2_.getName())) : (flag1 ? 1 : -1);
            }
        });
        return list;
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.type == 2)
        {
            this.page -= p_mouseEvent_1_.wheel;
            this.layoutComponent();
            p_mouseEvent_1_.cancel();
        }
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 4 && p_controlEvent_1_.target == this.itemButton)
        {
            this.updateFromItem();
            this.layoutComponent();
        }
        else if (p_controlEvent_1_.type == 3)
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
                int i = this.children.indexOf(this.getFocused());

                if (i == -1)
                {
                    this.nameField.focus();
                }
                else if (i + 1 == this.children.size())
                {
                    ++this.page;
                    this.layoutComponent();

                    for (TMIArea tmiarea : this.children)
                    {
                        if (tmiarea instanceof TMIEnchantField)
                        {
                            tmiarea.focus();
                            break;
                        }
                    }
                }
                else
                {
                    ((TMIArea)this.children.get(i + 1)).focus();
                }
            }

            p_keyboardEvent_1_.cancel();
        }
    }
}
