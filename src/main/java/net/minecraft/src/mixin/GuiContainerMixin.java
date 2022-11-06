package net.minecraft.src.mixin;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin extends GuiScreen {
	private static final String TARGET_CTOR = "<init>";
	@Shadow protected int guiLeft;
	@Shadow protected int guiTop;
	@Shadow public Container inventorySlots;
	@Shadow private Slot clickedSlot;
	@Shadow private Slot lastClickSlot;
	@Shadow private long lastClickTime;
	@Shadow private int lastClickButton;
	@Shadow protected int xSize;
	@Shadow protected int ySize;

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void onInit(Container inventorySlots, CallbackInfo ci ) {
	          TMI.instance.controller.onCreate((GuiContainer) (Object) this);
	}

	public void handleMouseInput() throws IOException{
		TMI.instance.controller.onMouseEvent();
		super.handleMouseInput();
	}


	@Inject( method = "drawScreen", at = @At( "HEAD" ) )
	public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci ) {
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		TMI.instance.controller.frameStart(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize);
	}

    @Inject(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V", shift = At.Shift.BEFORE, ordinal = 1)})
    public void drawScreenEnd(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      TMI.instance.controller.frameEnd(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize);
    }
  
	@Inject(method = {"mouseClicked"}, at = {@At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/inventory/GuiContainer;getSlotAtPosition(II)Lnet/minecraft/inventory/Slot;",
			shift = At.Shift.AFTER)}, cancellable = true)
	public void MouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
	    if (! TMI.instance.controller.onClick(mouseX, mouseY, mouseButton, guiTop, guiLeft, this.inventorySlots))
			ci.cancel();
	}

	@Inject( method = "keyTyped", at = @At("HEAD"), cancellable = true )
	public void keyTyped(char typedChar, int keyCode, CallbackInfo ci ) {
		if (! TMI.instance.controller.onKeypress(typedChar, keyCode) )
			ci.cancel();
	}

	@Inject( method = "onGuiClosed", at = @At("HEAD") )
	public void onGuiClosed( CallbackInfo ci ) {
		TMI.instance.controller.onClose();
	}

	@Inject(method = "doesGuiPauseGame", at = @At("HEAD"), cancellable = true)
	public void doesGuiPauseGame( CallbackInfoReturnable<Boolean> cir ) {
		cir.setReturnValue( TMI.instance.controller.shouldPauseGame() );
	}
}