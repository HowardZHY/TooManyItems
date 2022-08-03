import com.google.common.eventbus.EventBus;
import java.util.Arrays;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

@Mod(modid = "TooManyItems", name = "TooManyItems", version = "1.8.9", dependencies = "required-after:FML")
public class TMIForgeContainer extends DummyModContainer {
  private long updateTimer = 0L;

  public TMIForgeContainer() {
    super(new ModMetadata());
    ModMetadata modMetadata = getMetadata();
    modMetadata.authorList = Arrays.asList(new String[] { "Marglyph" });
    modMetadata.description = "Create items, enchantments, save states, cheats, etc.";
    modMetadata.modId = "TooManyItems";
    modMetadata.version = "1.8.9";
    modMetadata.name = "TooManyItems";
    modMetadata.url = "http://www.minecraftforum.net/topic/140684-162-toomanyitems-in-game-invedit-jul-5/";
  }
  
  public boolean registerBus(EventBus paramEventBus, LoadController paramLoadController) {
    paramEventBus.register(this);
    return true;
  }
  
  public String getLabel() {
    return "TooManyItems";
  }

  @SubscribeEvent
  public void tick(TickEvent.WorldTickEvent paramWorldTickEvent) {
    if (Minecraft.getMinecraft().isSingleplayer() && --this.updateTimer < 0L) {
      TMITickEntity.create();
      this.updateTimer = 100L;
    } 
  }
}