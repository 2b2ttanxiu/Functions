package org.functions.Bukkit.Main;

import org.functions.Bukkit.Commands.Defaults.CommandHelp;
import org.functions.Bukkit.Commands.Permissions.*;

public class RegisterCommands {
    public static void run() {
        CommandMain.run();
        CommandHelp.run();
        CommandGroup.run();
        CommandHome.run();
        CommandReport.run();
        CommandSuicide.run();
        CommandBack.run();;
        CommandDeleteOperator.run();
        CommandOperator.run();
        CommandPermission.run();
        CommandWarps.run();
        CommandEconomy.run();
        CommandMoney.run();
        CommandPay.run();
        CommandHat.run();
        CommandWeather.run();
        CommandSpawn.run();
        Functions.instance.getAPI().sendConsole("§aRegister Commands successfully!");
    }
}
