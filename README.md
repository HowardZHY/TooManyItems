# TooManyItems
An 1.8.9 update for the Too Many Items mod
## There is a MCP Client and a Forge mod. Compile with MCP 918.

## For developers:

### You need to put net/minecraft/client/gui/inventory/GuiContainer.class in the jar's basal directory and rename as TMIGuiContainer.class after build in Forge env. 

META-INF:

Manifest-Version: 1.0

FMLCorePlugin: TMIForgeLoader

FMLAT: TMI_at.cfg

TMI_at.cfg:

public-f net.minecraft.enchantment.Enchantment field_180311_a #enchantmentsList

public net.minecraft.client.renderer.texture.TextureUtil func_94277_a(I)V #bindTexture

public net.minecraft.nbt.NBTTagList func_152446_a(Ljava/io/DataInput;ILnet/minecraft/nbt/NBTSizeTracker;)V #read
public net.minecraft.nbt.NBTTagList func_74734_a(Ljava/io/DataOutput;)V #write

## COPYRIGHT INFO:

"Copyright 2011-2014 Marglyph. Free for personal or educational use only. Do not redistribute TooManyItems, including in mod packs, and do not use TooManyItems\' source code or graphics in your own mods."

The sources here is for personal or educational use only, as the Copyright is still belongs to Marglyph without further disagreement.

 @RedCDev
