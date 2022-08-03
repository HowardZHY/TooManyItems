# TooManyItems
An 1.8.9 update for the Too Many Items mod
## There is a MCP Client and a Forge mod. Compile with MCP 918.

###You need to put net/minecraft/client/gui/inventory/GuiContainer.class in the jar's basal directory and rename as TMIGuiContainer.class after build in Forge env. 

META-INF:
Manifest-Version: 1.0
FMLCorePlugin: TMIForgeLoader
FMLAT: TMI_at.cfg

TMI_at.cfg:
public-f net.minecraft.enchantment.Enchantment field_180311_a #enchantmentsList

public net.minecraft.client.renderer.texture.TextureUtil func_94277_a(I)V #bindTexture

public net.minecraft.nbt.NBTTagList func_152446_a(Ljava/io/DataInput;ILnet/minecraft/nbt/NBTSizeTracker;)V #read
public net.minecraft.nbt.NBTTagList func_74734_a(Ljava/io/DataOutput;)V #write


 @RedCDev
