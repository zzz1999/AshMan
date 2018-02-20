package net.mcbbs.zzz1999.AshMan;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.level.Level;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import java.io.File;
import java.util.*;

public class Main extends PluginBase{

    private Config config;
    private Map<Long,Entity> exemptedEntities = new HashMap<>();
    public static Main instance;
    public void onLoad(){
        instance = this;
    }

    @SuppressWarnings({"deprecation"})
    public void onEnable(){
        instance = this;
        this.getServer().getCommandMap().register("AshMan", new CommandListener(this));
        this.config = new Config(new File(this.getDataFolder(),"Config.json"),Config.JSON,new LinkedHashMap<String,Object>(){{
            put("EntitiesNumber",200);
            put("TPS",18);
            put("CleanPeriod",300);
        }}
        );
        this.getServer().getScheduler().scheduleRepeatingTask(new CleanEntityTask(this),20*this.config.getInt("CleanPeriod"));

    }

    public void addExemptedEntities(Entity entity){
        exemptedEntities.put(entity.getId(),entity);
    }

    public void removeExemptedEntities(Entity entity){
        exemptedEntities.remove(entity.getId());
    }

    public static Main getInstance(){
        return instance;
    }

    public void clean(){
        Integer i = 0;
        Integer d = 0;
        for(Player p :this.getServer().getOnlinePlayers().values()){
            AxisAlignedBB bb = new SimpleAxisAlignedBB(
                    p.getX()-16,
                    p.getY()-5,
                    p.getZ()-16,
                    p.getX()+16,
                    p.getY()+5,
                    p.getZ()+16
            );
            for (Entity entity : p.getLevel().getNearbyEntities(bb)) {
                if(entity instanceof EntityCreature && !(entity instanceof EntityHuman) && !this.exemptedEntities.containsKey(entity.getId())){
                    entity.close();
                    i++;
                }
                if(entity instanceof EntityItem){
                    entity.close();
                    d++;
                }

            }
        }

        Server.getInstance().broadcastMessage(TextFormat.ITALIC+""+TextFormat.GRAY+"[服务器清理] 本次清理总共清理掉了"+i+"个生物");
        Server.getInstance().broadcastMessage(TextFormat.ITALIC+""+TextFormat.GRAY+"[服务器清理] 本次清理总共清理掉了"+d+"个掉落物");

    }

    public void clean(Collection<Level> list){
        Integer i = 0;
        Integer d = 0;
        for (Level level : list) {
            for(Entity entity : level.getEntities()) {
                if (entity instanceof EntityCreature && !(entity instanceof EntityHuman) && !this.exemptedEntities.containsKey(entity.getId())) {
                    entity.close();
                    i++;
                }
                if (entity instanceof EntityItem) {
                    entity.close();
                    d++;
                }
            }
        }
        Server.getInstance().broadcastMessage(TextFormat.GOLD+"[服务器清理] 本次清理总共清理掉了"+i+"个生物");
        Server.getInstance().broadcastMessage(TextFormat.GOLD+"[服务器清理] 本次清理总共清理掉了"+d+"个掉落物");
    }

    public int getMaxEntitiesNumber(){
        return this.config.getInt("EntitiesNumber");
    }

    public int getLowestTPS(){
        return this.config.getInt("TPS");
    }

    public int getCleanPeriod(){
        return this.config.getInt("CleanPeriod");
    }

    public void putExemptedEntity(Entity entity){
        this.exemptedEntities.put(entity.getId(),entity);
    }

    public boolean delExemptedEntity(Entity entity){
        return this.exemptedEntities.remove(entity.getId(),entity);
    }

}