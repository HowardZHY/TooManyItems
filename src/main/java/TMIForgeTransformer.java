import java.io.InputStream;
import java.util.HashMap;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.commons.io.IOUtils;

public class TMIForgeTransformer implements IClassTransformer {
  private HashMap<String, String> mcpNames;
  
  private HashMap<String, String> obfNames;
  
  public byte[] transform(String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    if (paramString1.equals("ayl")) {
      System.out.println("[TMI] Rewriting class " + paramString1 + " / " + paramString2);
      try {
        InputStream inputStream = null;
        inputStream = getClass().getResourceAsStream("TMIGuiContainer.class");
        return IOUtils.toByteArray(inputStream);
      } catch (Exception exception) {
        System.out.println("[TMI] Exception rewriting class: " + exception);
        System.out.println("[TMI] TooManyItems will not work.");
      } 
    } 
    return paramArrayOfbyte;
  }
}
