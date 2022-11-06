import java.util.Map;
import java.io.File;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@TransformerExclusions({"mod_TooManyItems"})

public class TMIForgeLoader implements IFMLLoadingPlugin {

  public TMIForgeLoader() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.tmi.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        System.out.println("[TMI] Notice: This mod cannot run in MCP environment.");
  }

  public String[] getASMTransformerClass() {
    return null;
// new String[] { "TMIForgeTransformer" };
  }
  
  public String getModContainerClass() {
    return "TMIForgeContainer";
  }
  
  public String getSetupClass() {
    return null;
  }
  
  public String getAccessTransformerClass() {
    return null;
  }
  
  public void injectData(Map<String, Object> paramMap) {}
}