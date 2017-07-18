package net.mcbbs.zzz1999.AshMan;


import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;

import java.util.ArrayList;

public class CommandListener extends Command {

    private Main plugin;

    CommandListener(Main plugin) {
        super("ashman","清理服务器掉落物与生物的命令","/ashman <clean> [world...]",new String[]{
                "am"
        });
        this.setPermission("AshMan.Commands.clean");
        this.plugin = plugin;
        this.commandParameters.clear();
        this.commandParameters.put("default",new CommandParameter[]{
                new CommandParameter("clean",new String[]{"clean"}),
        });
        this.commandParameters.put("default2",new CommandParameter[]{
                new CommandParameter("clean",new String[]{"clean"}),
                new CommandParameter("world...",CommandParameter.ARG_TYPE_RAW_TEXT),
        });


    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length == 1){
            ArrayList<String> aliase = new ArrayList<String>(){
                {
                add("c");
                add("cl");
                add("cle");
                add("clea");
                add("clean");
                }
            };
            if(aliase.contains(args[0].toLowerCase())){
                plugin.clean();
                return true;
            }
        }else if(args.length>1){
            ArrayList<Level> needClean = new ArrayList<>();
            for(int i = 1; i<args.length-1 ;i++){
                Level level = this.plugin.getServer().getLevelByName(args[i]);
                if(level != null){
                    needClean.add(level);
                }else{
                    sender.sendMessage("[服务器清理] 找不到世界"+args[i]);
                }
            }
            plugin.clean(needClean);
            return true;
        }else{
            sender.sendMessage("[服务器清理] 用法:"+this.getUsage());
        }
        return false;
    }
}