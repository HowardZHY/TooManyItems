import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"mod_TooManyItems"})
@MCVersion("1.8.9")
public class TMIForgeLoader implements IFMLLoadingPlugin {
  public String[] getASMTransformerClass() {
    return new String[] { "TMIForgeTransformer" };
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