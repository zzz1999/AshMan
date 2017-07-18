package net.mcbbs.zzz1999.AshMan;


import cn.nukkit.scheduler.PluginTask;

public class Countdown10Task extends PluginTask<Main>{

    private Main plugin;

    public Countdown10Task(Main owner) {
        super(owner);
        this.plugin = owner;
    }

    @Override
    public void onRun(int i) {
        this.plugin.clean();
    }
}