package net.minecraft.src;
import java.util.ArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class TMICustomFireworkPanel extends TMIArea
{
    public static int[] colors = new int[] {16777215, 14188339, 11685080, 6724056, 15066419, 8375321, 15892389, 5000268, 10066329, 5013401, 8339378, 3361970, 6704179, 6717235, 10040115, 1644825};
    private TMIItemButton fireworkButton = new TMIItemButton((ItemStack)null);
    private TMIItemButton chargeButton = new TMIItemButton((ItemStack)null);
    private TMICycleButton shapeButton;
    private TMICycleButton flightButton = new TMICycleButton(new String[] {"1", "2", "3", "4", "5"});
    private TMICycleButton flickerButton = new TMICycleButton(new String[] {"Flicker", "No flicker"});
    private TMICycleButton trailButton = new TMICycleButton(new String[] {"Trail", "No trail"});
    private TMITextField nameField = new TMITextField();
    private TMILabel colorLabel = new TMILabel("Colors:");
    private TMISwatchPicker colorPicker;
    private TMILabel fadeColorLabel;
    private TMISwatchPicker fadeColorPicker;

    public TMICustomFireworkPanel()
    {
        this.colorPicker = new TMISwatchPicker(colors, 12, true);
        this.fadeColorLabel = new TMILabel("Fade colors:");
        this.fadeColorPicker = new TMISwatchPicker(colors, 12, true);
        ArrayList var1 = new ArrayList();

        for (int var2 = 0; var2 < 5; ++var2)
        {
            var1.add(I18n.format("item.fireworksCharge.type." + var2, new Object[0]));
        }

        this.shapeButton = new TMICycleButton(var1);
        this.addChild(this.fireworkButton);
        this.addChild(this.chargeButton);
        this.addChild(this.shapeButton);
        this.addChild(this.flightButton);
        this.addChild(this.flickerButton);
        this.addChild(this.trailButton);
        this.addChild(this.nameField);
        this.addChild(this.colorLabel);
        this.addChild(this.colorPicker);
        this.addChild(this.fadeColorLabel);
        this.addChild(this.fadeColorPicker);
        this.flightButton.setPrefix("Flight: ");
        this.nameField.placeholder = "Name...";
        this.nameField.addEventListener(this);
        this.shapeButton.addEventListener(this);
        this.flightButton.addEventListener(this);
        this.flickerButton.addEventListener(this);
        this.trailButton.addEventListener(this);
        this.colorPicker.addEventListener(this);
        this.recreateItem();
    }

    private void recreateItem()
    {
        TMIFireworkBuilder var1 = (new TMIFireworkBuilder()).flight(this.flightButton.getIntValue());
        var1.explosion(this.shapeButton.getIndex(), this.colorPicker.getArray(), this.fadeColorPicker.getArray(), this.flickerButton.getIndex() == 0, this.trailButton.getIndex() == 0);
        this.fireworkButton.stack = var1.firework().name(this.nameField.value()).stack();
        this.chargeButton.stack = var1.charge().name(this.nameField.value()).stack();
    }

    public void layoutComponent()
    {
        int var1 = this.x + (this.width - 16 - 16 - 2) / 2;
        this.fireworkButton.setPosition(var1, this.y + 4);
        this.chargeButton.setPosition(var1 + 16 + 2, this.y + 4);
        int var2 = this.x + 4;
        int var3 = this.x + 4 + (this.width - 8) / 2;
        int var4 = this.y + 4 + 16 + 4;
        int var5 = var4 + 12;
        this.shapeButton.setSize((this.width - 4) / 2, 12);
        this.flightButton.setSize((this.width - 4) / 2, 12);
        this.flickerButton.setSize((this.width - 4) / 2, 12);
        this.trailButton.setSize((this.width - 4) / 2, 12);
        this.shapeButton.setPosition(var2, var4);
        this.flightButton.setPosition(var3, var4);
        this.flickerButton.setPosition(var2, var5);
        this.trailButton.setPosition(var3, var5);
        this.nameField.setSize(this.width - 4, 12);
        this.nameField.setPosition(this.x + 2, this.trailButton.y + 14 + 2);
        this.colorLabel.setPosition(this.x + 2, this.nameField.y + 14 + 4);
        this.colorPicker.setSize(this.width - 4, 24);
        this.colorPicker.setPosition(this.x + 2, this.colorLabel.y + 12 + 1);
        this.fadeColorLabel.setPosition(this.x + 2, this.colorPicker.y + 24 + 6);
        this.fadeColorPicker.setSize(this.width - 4, 24);
        this.fadeColorPicker.setPosition(this.x + 2, this.fadeColorLabel.y + 12 + 1);
    }

    public void controlEvent(TMIEvent p_controlEvent_1_)
    {
        if (p_controlEvent_1_.type == 3)
        {
            this.recreateItem();
        }
    }
}