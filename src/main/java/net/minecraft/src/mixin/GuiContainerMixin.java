package net.minecraft.src.mixin;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin extends GuiScreen {
	@Shadow protected int guiLeft;
	@Shadow protected int guiTop;
	@Shadow public Container inventorySlots;
	@Shadow private Slot clickedSlot;
	@Shadow private Slot lastClickSlot;
	@Shadow private long lastClickTime;
	@Shadow private int lastClickButton;
	@Shadow protected abstract Slot getSlotAtPosition( int x, int y );
	@Shadow protected int xSize;
	@Shadow protected int ySize;

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void onInit(GuiContainer guicontainer, CallbackInfo ci ) {
		TMI.instance.controller.onCreate((GuiContainer) (Object) this);
	}

	@SoftOverride
	@Override
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

	@Inject(
		method = "drawScreen",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V",
			shift = At.Shift.BEFORE,
			ordinal = 1
		)
	)
	public void drawScreenEnd(int mouseX, int mouseY, float partialTicks, CallbackInfo ci ) {
		TMI.instance.controller.frameEnd(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize);
	}

	@Inject(
		method = "mouseClicked",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/inventory/GuiContainer;getSlotAt(II)Lnet/minecraft/inventory/Slot;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void onMouseClicked( int mouseX, int mouseY, int mouseButton, CallbackInfo ci ) {
		if (! TMI.instance.controller.onClick(mouseX, mouseY, mouseButton, guiTop, guiLeft, this.inventorySlots)) {
			this.lastClickSlot = this.getSlotAtPosition( mouseX, mouseY );
			long i = Minecraft.getSystemTime();
			this.lastClickButton = mouseButton;
			ci.cancel();
		}
	}

	@Inject( method = "keyPressed", at = @At("HEAD"), cancellable = true )
	public void keyTyped(char typedChar, int keyCode, CallbackInfo ci ) {
		if (! TMI.instance.controller.onKeypress(typedChar, keyCode) )
			ci.cancel();
	}

	@Inject( method = "onGuiClosed", at = @At("HEAD") )
	public void onGuiClosed( CallbackInfo ci ) {
		TMI.instance.controller.onClose();
	}

	@Inject( method = "doesGuiPauseGame", at = @At("HEAD") )
	public void doesGuiPauseGame( CallbackInfoReturnable<Boolean> cir ) {
		cir.setReturnValue( TMI.instance.controller.shouldPauseGame() );
	}

	// field_1230 -> width
	// field_1231 -> height
	// field_1347 -> backgroundWidth
	// field_1348 -> backgroundHeight
	// field_1349 -> screenHandler
	// field_1350 -> x
	// field_1351 -> y
}
