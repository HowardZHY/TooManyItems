
public class TMISavePanel extends TMIArea
{
    private TMIArea buttonPanel = new TMIArea();
    private TMIArea renamePanel = new TMIArea();
    private TMIButton[] saveButtons;
    private TMIButton[] clearButtons;
    private TMITextField[] renameFields;
    private TMIButton renameButton;
    private TMIButton okButton;

    public TMISavePanel()
    {
        this.saveButtons = new TMIButton[TMISaveFile.statesSaved.length];
        this.clearButtons = new TMIButton[TMISaveFile.statesSaved.length];
        this.renameFields = new TMITextField[TMISaveFile.statesSaved.length];
        this.renameButton = new TMIButton((Object)null, "Rename...");
        this.okButton = new TMIButton((Object)null, "OK");

        for (int var1 = 0; var1 < this.saveButtons.length; ++var1)
        {
            this.saveButtons[var1] = new TMIButton(Integer.valueOf(var1), TMIConfigFile.getSaveName(var1));
            this.saveButtons[var1].centerText = false;
            this.clearButtons[var1] = new TMIButton(Integer.valueOf(-var1 - 1), "x");
            this.renameFields[var1] = new TMITextField();
            this.buttonPanel.addChild(this.saveButtons[var1]);
            this.buttonPanel.addChild(this.clearButtons[var1]);
            this.renamePanel.addChild(this.renameFields[var1]);
        }

        this.buttonPanel.addChild(this.renameButton);
        this.renamePanel.addChild(this.okButton);
        this.addChild(this.buttonPanel);
    }

    public void layoutComponent()
    {
        this.buttonPanel.copyBounds(this);
        this.renamePanel.copyBounds(this);
        int var1 = this.x + 4;
        int var2 = this.x + this.width - 4 - 12;
        int var3 = this.y + 10;

        for (int var4 = 0; var4 < this.saveButtons.length; ++var4)
        {
            this.saveButtons[var4].setSize(this.width - 8 - 12 - 2, 12);
            this.saveButtons[var4].setPosition(var1, var3);
            this.clearButtons[var4].setSize(12, 12);
            this.clearButtons[var4].setPosition(var2, var3);
            this.renameFields[var4].setSize(this.width - 8, 12);
            this.renameFields[var4].setPosition(var1, var3);
            var3 += 16;
        }

        var3 += 10;
        this.renameButton.setYCenteredIn(var3, this);
        this.okButton.setYCenteredIn(var3, this);
    }

    public void showRename()
    {
        this.removeChild(this.buttonPanel);
        this.addChild(this.renamePanel);

        for (int var1 = 0; var1 < this.renameFields.length; ++var1)
        {
            this.renameFields[var1].setValue(TMIConfigFile.getSaveName(var1));
        }
    }

    public void saveRename()
    {
        for (int var1 = 0; var1 < this.renameFields.length; ++var1)
        {
            TMIConfigFile.setSaveName(var1, this.renameFields[var1].value());
        }

        this.removeChild(this.renamePanel);
        this.addChild(this.buttonPanel);
    }

    public void drawComponent()
    {
        for (int var1 = 0; var1 < this.saveButtons.length; ++var1)
        {
            boolean var2 = TMISaveFile.statesSaved[var1];
            this.saveButtons[var1].label = (var2 ? "Load " : "Save ") + TMIConfigFile.getSaveName(var1);
            this.clearButtons[var1].shown = var2;
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.mouseButton == 0)
        {
            if (p_mouseEvent_1_.target instanceof TMIButton)
            {
                TMIButton var2 = (TMIButton)p_mouseEvent_1_.target;

                if (var2 == this.renameButton)
                {
                    this.showRename();
                }
                else if (var2 == this.okButton)
                {
                    this.saveRename();
                }
                else
                {
                    int var3 = ((Integer)var2.data).intValue();

                    if (var3 < 0)
                    {
                        var3 = -(var3 + 1);
                        TMISaveFile.clearState(var3);
                    }
                    else if (TMISaveFile.statesSaved[var3])
                    {
                        TMISaveFile.loadState(var3);
                    }
                    else
                    {
                        TMISaveFile.saveState(var3);
                    }
                }
            }

            p_mouseEvent_1_.cancel();
        }
    }
}
