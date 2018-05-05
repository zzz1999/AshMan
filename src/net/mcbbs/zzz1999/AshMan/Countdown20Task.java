package net.mcbbs.zzz1999.AshMan;


import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;

public class Countdown20Task extends PluginTask<Main>{

    private Main plugin;

    public Countdown20Task(Main owner) {
        super(owner);
        this.plugin = owner;
    }

    @Override
    public void onRun(int i) {
        plugin.getServer().getScheduler().scheduleDelayedTask(new Countdown10Task(this.plugin),10*20);
        Server.getInstance().broadcastMessage(TextFormat.ITALIC+""+TextFormat.YELLOW+"[服务器清理] 将在10秒钟之后清理掉多余的生物");
    }
}