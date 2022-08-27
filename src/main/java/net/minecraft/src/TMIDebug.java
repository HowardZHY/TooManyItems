package net.minecraft.src;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class TMIDebug
{
    private static long lastReportTime = 0L;

    public static void reportException(Throwable p_reportException_0_)
    {
        try
        {
            System.out.println("[TMI serious error - copy from here]");
            p_reportException_0_.printStackTrace();
            System.out.println("[TMI serious error - copy to here]");
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[TMI] serious error (see Game Output) " + p_reportException_0_.toString()));
        }
        catch (Exception var2)
        {
            System.out.println("[TMI] Error during exception reporting:");
            var2.printStackTrace();
        }
    }

    public static void reportExceptionWithTimeout(Throwable p_reportExceptionWithTimeout_0_, long p_reportExceptionWithTimeout_1_)
    {
        long var3 = System.currentTimeMillis();

        if (var3 > lastReportTime + p_reportExceptionWithTimeout_1_)
        {
            lastReportTime = var3;
            reportException(p_reportExceptionWithTimeout_0_);
        }
    }

    public static void logWithTrace(String p_logWithTrace_0_)
    {
        logWithTrace(p_logWithTrace_0_, 1000);
    }

    public static void logWithTrace(String p_logWithTrace_0_, int p_logWithTrace_1_)
    {
        System.out.println("[TMI] " + p_logWithTrace_0_);
        StackTraceElement[] var2 = Thread.currentThread().getStackTrace();

        for (int var3 = 0; var3 < var2.length && var3 < p_logWithTrace_1_; ++var3)
        {
            System.out.println(var2[var3].toString());
        }
    }

    public static void logWithError(String p_logWithError_0_, Throwable p_logWithError_1_)
    {
        System.out.println("[TMI] " + p_logWithError_0_);
        p_logWithError_1_.printStackTrace();
    }
}