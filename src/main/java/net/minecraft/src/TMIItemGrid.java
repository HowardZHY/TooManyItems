package net.minecraft.src;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class TMIItemGrid extends TMIArea
{
    public List<ItemStack> items = null;
    public int page;
    public boolean ignorePage = false;
    public boolean myItemsView = false;
    public int itemsPerPage;
    public int cols;
    public int rows;
    public int ITEMWIDTH = 16;
    public int SPACING;
    public int LEFT;
    public int TOP;
    protected final Item spawnerItem;
    protected final Item staticWaterItem;
    protected final Item staticLavaItem;

    public TMIItemGrid()
    {
        this.SPACING = this.ITEMWIDTH + 2;
        this.LEFT = 0;
        this.TOP = 0;
        this.spawnerItem = (new TMIStackBuilder("mob_spawner")).stack().getItem();
        this.staticWaterItem = (new TMIStackBuilder("water")).stack().getItem();
        this.staticLavaItem = (new TMIStackBuilder("lava")).stack().getItem();
    }

    public TMIItemGrid(List<ItemStack> p_i46390_1_)
    {
        this.SPACING = this.ITEMWIDTH + 2;
        this.LEFT = 0;
        this.TOP = 0;
        this.spawnerItem = (new TMIStackBuilder("mob_spawner")).stack().getItem();
        this.staticWaterItem = (new TMIStackBuilder("water")).stack().getItem();
        this.staticLavaItem = (new TMIStackBuilder("lava")).stack().getItem();
        this.items = p_i46390_1_;
    }

    public void layoutComponent()
    {
        this.cols = this.width / this.SPACING;
        this.rows = this.height / this.SPACING;
        this.itemsPerPage = this.rows * this.cols;
        this.LEFT = this.width % this.SPACING / 2;
    }

    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_)
    {
        if (this.items != null)
        {
            ItemStack var3 = null;
            ItemStack var4 = TMIGame.getHeldItem();

            if (this.isMouseover())
            {
                this.clearTooltip();

                if (var4 != null)
                {
                    if (this.myItemsView)
                    {
                        this.setTooltip("Add to My Items");
                    }
                    else if (!TMIGame.isMultiplayer())
                    {
                        this.setTooltip("DELETE " + TMIGame.stackName(var4));
                    }
                }
                else
                {
                    var3 = this.getItemAtXY(p_drawComponent_1_, p_drawComponent_2_);

                    if (var3 != null)
                    {
                        List var5 = var3.getTooltip(Minecraft.getMinecraft().thePlayer, true);
                        var5.add("\u00a7r\u00a7aClick: give stack");
                        var5.add("\u00a7r\u00a7aRight-click: give one");

                        if (this.myItemsView)
                        {
                            var5.add("\u00a7r\u00a7aShift-click: REMOVE from My Items");
                        }
                        else
                        {
                            var5.add("\u00a7r\u00a7aShift-click: add to My Items");
                        }

                        this.setTooltip(var5);
                    }
                }
            }

            int var17 = this.ignorePage ? 0 : this.page;
            int var6 = this.getX() + this.LEFT;
            int var7 = this.getY() + this.TOP;
            int var8 = var17 * this.itemsPerPage;

            for (int var9 = var8; var9 < var8 + this.itemsPerPage && var9 < this.items.size(); ++var9)
            {
                ItemStack var10 = (ItemStack)this.items.get(var9);
                int var11 = (var9 - var8) / this.cols;
                int var12 = (var9 - var8) % this.cols;

                if (var10 == var3)
                {
                    TMIDrawing.fillRect(var6 + var12 * this.SPACING, var7 + var11 * this.SPACING, this.SPACING, this.SPACING, -6250336);
                }

                int var13 = var6 + var12 * this.SPACING + 1;
                int var14 = var7 + var11 * this.SPACING + 1;
                this.drawItem(var13, var14, var10);

                if (var10.getItem() == this.spawnerItem)
                {
                    String var15 = TMIGame.stackName(var10);
                    Pattern var16 = Pattern.compile("\u00a7.");
                    var15 = var16.matcher(var15).replaceAll("");
                    var15 = var15.substring(0, 3);
                    TMIDrawing.drawText(var13 + 1, var14 + 1, var15, -2236963, 1);
                }
                else if (var10.getItem() == this.staticWaterItem || var10.getItem() == this.staticLavaItem)
                {
                    TMIDrawing.drawText(var13 + 1, var14 + 1, "Static", -2236963, 1);
                }
            }
        }
    }

    protected void drawItem(int p_drawItem_1_, int p_drawItem_2_, ItemStack p_drawItem_3_)
    {
        TMIDrawing.drawItem(p_drawItem_1_, p_drawItem_2_, p_drawItem_3_);
    }

    public int getEvenWidth(int p_getEvenWidth_1_)
    {
        return p_getEvenWidth_1_ - p_getEvenWidth_1_ % this.SPACING;
    }

    public ItemStack getFirstItem()
    {
        int var1 = this.page * this.itemsPerPage;
        return var1 >= this.items.size() ? null : (new TMIStackBuilder((ItemStack)this.items.get(var1))).maxStack();
    }

    public ItemStack getItemAtRowCol(int p_getItemAtRowCol_1_, int p_getItemAtRowCol_2_)
    {
        if (p_getItemAtRowCol_2_ >= 0 && p_getItemAtRowCol_2_ < this.cols && p_getItemAtRowCol_1_ >= 0 && p_getItemAtRowCol_1_ < this.rows)
        {
            int var3 = this.page * this.itemsPerPage + p_getItemAtRowCol_1_ * this.cols + p_getItemAtRowCol_2_;

            if (var3 < this.items.size())
            {
                return (ItemStack)this.items.get(var3);
            }
        }

        return null;
    }

    public ItemStack getItemAtXY(int p_getItemAtXY_1_, int p_getItemAtXY_2_)
    {
        if (this.contains(p_getItemAtXY_1_, p_getItemAtXY_2_))
        {
            int var3 = p_getItemAtXY_1_ - this.getX() - this.LEFT;
            int var4 = p_getItemAtXY_2_ - this.getY() - this.TOP;

            if (var3 >= 0 && var4 >= 0)
            {
                int var5 = var3 / this.SPACING;
                int var6 = var4 / this.SPACING;
                return this.getItemAtRowCol(var6, var5);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void mouseEvent(TMIEvent p_mouseEvent_1_)
    {
        if (p_mouseEvent_1_.target == this)
        {
            ItemStack var2;

            if (p_mouseEvent_1_.mouseButton == 0)
            {
                var2 = TMIGame.getHeldItem();

                if (var2 != null)
                {
                    if (this.myItemsView)
                    {
                        TMISaveFile.addFavorite(var2);
                        p_mouseEvent_1_.cancel();
                        return;
                    }

                    if (!TMIGame.isMultiplayer())
                    {
                        TMIGame.setHeldItem((ItemStack)null);
                        p_mouseEvent_1_.cancel();
                        return;
                    }
                }

                ItemStack var3 = this.getItemAtXY(p_mouseEvent_1_.x, p_mouseEvent_1_.y);

                if (var3 != null)
                {
                    if (!Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42))
                    {
                        TMIGame.giveStack((new TMIStackBuilder(var3)).maxStack());
                    }
                    else if (this.myItemsView)
                    {
                        TMISaveFile.removeFavorite(var3);
                    }
                    else
                    {
                        TMISaveFile.addFavorite(var3);
                    }
                }

                p_mouseEvent_1_.cancel();
            }
            else if (p_mouseEvent_1_.mouseButton == 1)
            {
                var2 = this.getItemAtXY(p_mouseEvent_1_.x, p_mouseEvent_1_.y);

                if (var2 != null)
                {
                    TMIGame.giveStack((new TMIStackBuilder(var2)).amount(1).stack());
                }

                p_mouseEvent_1_.cancel();
            }
            else if (p_mouseEvent_1_.type == 2)
            {
                if (p_mouseEvent_1_.wheel < 0)
                {
                    this.setPage(this.page + 1);
                }
                else
                {
                    this.setPage(this.page - 1);
                }

                p_mouseEvent_1_.cancel();
            }
        }
    }

    public void setPage(int p_setPage_1_)
    {
        int var2 = this.numPages();

        if (var2 == 0)
        {
            this.page = 0;
        }
        else
        {
            while (p_setPage_1_ < 0)
            {
                p_setPage_1_ += var2;
            }

            while (p_setPage_1_ >= var2)
            {
                p_setPage_1_ -= var2;
            }

            this.page = p_setPage_1_;
        }
    }

    public int numPages()
    {
        return this.items != null && this.items.size() != 0 ? 1 + (this.items.size() - 1) / this.itemsPerPage : 0;
    }
}