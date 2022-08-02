package net.minecraft.src;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class TMIControlPanel extends TMIArea
{
    private float scale;
    private int controlHeight;
    private int controlPadding = 1;
    private int top = 8;
    private int left = 8;
    private List<TMIControlPanel.Control> controls = Arrays.asList(TMIControlPanel.Control.values());

    public void layoutComponent()
    {
        int var1 = TMIControlPanel.Control.values().length * 16;
        this.scale = 1.0F;

        if (var1 > this.height - 20 - this.top)
        {
            this.scale = 0.66F;
        }

        this.controlHeight = (int)(16.0F * this.scale);
    }

    private void updateText()
    {
        TMIControlPanel.Control.SURVIVAL.value = null;
        TMIControlPanel.Control.CREATIVE.value = null;
        TMIControlPanel.Control.ADVENTURE.value = null;
        TMIControlPanel.Control.SPECTATOR.value = null;
        ((TMIControlPanel.Control)this.controls.get(TMIGame.getGameMode())).value = "selected";
        TMIControlPanel.Control.CHEATS.value = TMIGame.isMultiplayer() ? "SP only" : (TMIGame.getCheats() ? "on" : "off");
        TMIControlPanel.Control.DIFFICULTY.value = TMIGame.isMultiplayer() ? "SP only" : TMIGame.getDifficultyName();
        TMIControlPanel.Control.RAIN.value = TMIGame.isRaining() ? "on" : "off";
        TMIControlPanel.Control.DRY.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.preventRain ? "on" : "off");
        TMIControlPanel.Control.KEEP.value = TMIGame.getKeepInventory() ? "keep items" : "drop items";
        TMIControlPanel.Control.INFINITE.value = TMIGame.isMultiplayer() ? "SP only" : (TMITickEntity.infiniteStack ? "on" : "off");
        TMIControlPanel.Control.HEAL.value = TMIGame.isMultiplayer() ? "SP only" : null;
        TMIControlPanel.Control.MILK.value = TMIGame.isMultiplayer() ? "SP only" : null;
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        this.updateText();
        int var3 = this.x + this.left;
        int var4 = this.y + this.top;
        boolean var5 = this.contains(p_drawComponent_1_, p_drawComponent_2_);
        GL11.glPushMatrix();
        GL11.glScalef(this.scale, this.scale, 1.0F);
        float var6 = 1.0F / this.scale;

        for (Iterator var7 = this.controls.iterator(); var7.hasNext(); var4 += this.controlHeight)
        {
            TMIControlPanel.Control var8 = (TMIControlPanel.Control)var7.next();

            if (var5 && p_drawComponent_2_ >= var4 && p_drawComponent_2_ < var4 + this.controlHeight)
            {
                TMIDrawing.fillRect((int)((float)this.x * var6), (int)((float)var4 * var6), (int)((float)this.width * var6), (int)((float)this.controlHeight * var6), 587202559);
            }

            String var9 = var8.text;

            if (var8.value != null)
            {
                var9 = var9 + ": " + var8.value;
            }

            TMIDrawing.drawText((int)((float)var3 * var6), (int)((float)(var4 + this.controlPadding) * var6), var9);
        }

        var4 = (int)((float)(this.y + this.height - 15) * var6);
        TMIDrawing.drawText((int)((float)var3 * var6), var4, "TMI 1.8 2014-09-07", -3355444);
        GL11.glPopMatrix();
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.mouseButton == 0)
        {
            int var2 = (p_mouseEvent_1_.y - this.y - this.top) / this.controlHeight;

            if (var2 >= 0 && var2 < this.controls.size())
            {
                TMIControlPanel.Control var3 = (TMIControlPanel.Control)this.controls.get(var2);

                switch (TMIControlPanel.NamelessClass2086403506.$SwitchMap$TMIControlPanel$Control[var3.ordinal()])
                {
                    case 1:
                        TMIGame.setGameMode(0);
                        break;

                    case 2:
                        TMIGame.setGameMode(1);
                        break;

                    case 3:
                        TMIGame.setGameMode(2);
                        break;

                    case 4:
                        TMIGame.setGameMode(3);
                        break;

                    case 5:
                        if (!TMIGame.isMultiplayer())
                        {
                            TMIGame.setCheats(!TMIGame.getCheats());
                        }

                        break;

                    case 6:
                        TMIGame.incrementDifficulty();
                        break;

                    case 7:
                        TMIGame.toggleRaining();
                        break;

                    case 8:
                        if (!TMIGame.isMultiplayer())
                        {
                            TMIGame.fillPlayerHealth();
                            TMIGame.fillPlayerHunger();
                        }

                        break;

                    case 9:
                        if (!TMIGame.isMultiplayer())
                        {
                            TMIGame.removePlayerEffects();
                        }

                        break;

                    case 10:
                        if (!TMIGame.isMultiplayer())
                        {
                            TMITickEntity.infiniteStack = !TMITickEntity.infiniteStack;
                        }

                        break;

                    case 11:
                        TMIGame.setTime(0L);
                        break;

                    case 12:
                        TMIGame.setTime(6000L);
                        break;

                    case 13:
                        TMIGame.setTime(13000L);
                        break;

                    case 14:
                        TMIGame.setTime(18000L);
                        break;

                    case 15:
                        TMIGame.setKeepInventory(!TMIGame.getKeepInventory());
                        break;

                    case 16:
                        if (!TMIGame.isMultiplayer())
                        {
                            TMITickEntity.preventRain = !TMITickEntity.preventRain;
                        }
                }
            }
        }
    }

    static class NamelessClass2086403506
    {
        static final int[] $SwitchMap$TMIControlPanel$Control = new int[TMIControlPanel.Control.values().length];

        static
        {
            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.SURVIVAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError var16)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.CREATIVE.ordinal()] = 2;
            }
            catch (NoSuchFieldError var15)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.ADVENTURE.ordinal()] = 3;
            }
            catch (NoSuchFieldError var14)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.SPECTATOR.ordinal()] = 4;
            }
            catch (NoSuchFieldError var13)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.CHEATS.ordinal()] = 5;
            }
            catch (NoSuchFieldError var12)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.DIFFICULTY.ordinal()] = 6;
            }
            catch (NoSuchFieldError var11)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.RAIN.ordinal()] = 7;
            }
            catch (NoSuchFieldError var10)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.HEAL.ordinal()] = 8;
            }
            catch (NoSuchFieldError var9)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.MILK.ordinal()] = 9;
            }
            catch (NoSuchFieldError var8)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.INFINITE.ordinal()] = 10;
            }
            catch (NoSuchFieldError var7)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.TIME_SUNRISE.ordinal()] = 11;
            }
            catch (NoSuchFieldError var6)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.TIME_NOON.ordinal()] = 12;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.TIME_MOONRISE.ordinal()] = 13;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.TIME_MIDNIGHT.ordinal()] = 14;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.KEEP.ordinal()] = 15;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                $SwitchMap$TMIControlPanel$Control[TMIControlPanel.Control.DRY.ordinal()] = 16;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }

    static enum Control
    {
        SURVIVAL("SURVIVAL", 0, "Survival mode"),
        CREATIVE("CREATIVE", 1, "Creative mode"),
        ADVENTURE("ADVENTURE", 2, "Adventure mode"),
        SPECTATOR("SPECTATOR", 3, "Spectator mode"),
        CHEATS("CHEATS", 4, "Cheats"),
        KEEP("KEEP", 5, "On death"),
        DIFFICULTY("DIFFICULTY", 6, "Difficulty"),
        RAIN("RAIN", 7, "Rain/snow"),
        DRY("DRY", 8, "Prevent rain"),
        TIME_SUNRISE("TIME_SUNRISE", 9, "Time - sunrise"),
        TIME_NOON("TIME_NOON", 10, "Time - noon"),
        TIME_MOONRISE("TIME_MOONRISE", 11, "Time - moonrise"),
        TIME_MIDNIGHT("TIME_MIDNIGHT", 12, "Time - midnight"),
        HEAL("HEAL", 13, "Heal + fill hunger"),
        MILK("MILK", 14, "Remove potion effects"),
        INFINITE("INFINITE", 15, "TMI infinite stacks");
        public final String text;
        public String value;

        private static final TMIControlPanel.Control[] $VALUES = new TMIControlPanel.Control[]{SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR, CHEATS, KEEP, DIFFICULTY, RAIN, DRY, TIME_SUNRISE, TIME_NOON, TIME_MOONRISE, TIME_MIDNIGHT, HEAL, MILK, INFINITE};

        private Control(String p_i46379_1_, int p_i46379_2_, String p_i46379_3_)
        {
            this.text = p_i46379_3_;
        }
    }
}