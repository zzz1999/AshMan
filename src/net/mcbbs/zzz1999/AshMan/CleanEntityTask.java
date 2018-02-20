package net.mcbbs.zzz1999.AshMan;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;

import java.util.Map;

public class CleanEntityTask extends PluginTask<Main> {

    private Main plugin ;

    CleanEntityTask(Main owner) {
        super(owner);
        this.plugin = owner;
    }

    @Override
    public void onRun(int i) {
        Map<Integer, Level> Levels = plugin.getServer().getLevels();
        int TotalEntities = 0;
        for(Level value : Levels.values()){
            TotalEntities+=value.getEntities().length;
        }

        if(TotalEntities >= plugin.getMaxEntitiesNumber() || plugin.getServer().getTicksPerSecond() < plugin.getLowestTPS()){
            if(plugin.getServer().getTicksPerSecondAverage() <= 16){
                plugin.clean(plugin.getServer().getLevels().values());
                return;
            }
            //plugin.clean();
            plugin.getServer().getScheduler().scheduleDelayedTask(new Countdown20Task(this.plugin),10*20);
            Server.getInstance().broadcastMessage(TextFormat.ITALIC+""+TextFormat.YELLOW+"[服务器清理] 将在20秒钟之后清理掉多余的生物和掉落物");
        }

    }
}