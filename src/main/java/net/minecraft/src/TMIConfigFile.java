package net.minecraft.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class TMIConfigFile
{
    public static final String filename = "TooManyItems.txt";
    public static final int numSaves = 7;
    private static Map<String, String> settings = new LinkedHashMap();

    public static File file()
    {
        return new File(Minecraft.getMinecraft().mcDataDir, "TooManyItems.txt");
    }

    public static boolean read()
    {
        try
        {
            File var0 = file();

            if (!var0.exists())
            {
                return false;
            }
            else
            {
                BufferedReader var1 = new BufferedReader(new FileReader(var0));
                String var2;

                while ((var2 = var1.readLine()) != null)
                {
                    String[] var3 = var2.split(":", 2);

                    if (var3.length > 1)
                    {
                        settings.put(var3[0], var3[1]);
                    }
                }

                var1.close();
                return true;
            }
        }
        catch (Throwable var4)
        {
            TMIDebug.reportException(var4);
            return false;
        }
    }

    public static void write()
    {
        try
        {
            File var0 = file();
            PrintWriter var1 = new PrintWriter(new FileWriter(var0));
            Iterator var2 = settings.keySet().iterator();

            while (var2.hasNext())
            {
                String var3 = (String)var2.next();
                var1.println(var3 + ":" + (String)settings.get(var3));
            }

            var1.close();
        }
        catch (Throwable var4)
        {
            TMIDebug.reportException(var4);
        }
    }

    public static int getHotkey()
    {
        String var0 = (String)settings.get("key");
        boolean var1 = false;
        int var2 = Keyboard.getKeyIndex(var0.toUpperCase());

        if (var2 == 0)
        {
            var2 = 24;
        }

        return var2;
    }

    public static void set(String p_set_0_, String p_set_1_)
    {
        settings.put(p_set_0_, p_set_1_);
        write();
    }

    public static boolean getBooleanSetting(String p_getBooleanSetting_0_)
    {
        return Boolean.parseBoolean((String)settings.get(p_getBooleanSetting_0_));
    }

    public static boolean isEnabled()
    {
        return getBooleanSetting("enable");
    }

    public static void toggleEnabled()
    {
        set("enable", Boolean.toString(!getBooleanSetting("enable")));
    }

    public static void setEnabled(boolean p_setEnabled_0_)
    {
        set("enable", Boolean.toString(p_setEnabled_0_));
    }

    public static String getSaveName(int p_getSaveName_0_)
    {
        String var1 = (String)settings.get("save-name" + (p_getSaveName_0_ + 1));
        return var1 != null ? var1 : "" + (p_getSaveName_0_ + 1);
    }

    public static void setSaveName(int p_setSaveName_0_, String p_setSaveName_1_)
    {
        set("save-name" + (p_setSaveName_0_ + 1), p_setSaveName_1_);
    }

    static
    {
        settings.put("enable", "true");
        settings.put("give-command", "/give {0} {1} {2} {3}");
        settings.put("give-command-num-id", "false");
        settings.put("key", "o");

        for (int var0 = 0; var0 < 7; ++var0)
        {
            settings.put("save-name" + (var0 + 1), "" + (var0 + 1));
        }
    }
}