package org.functions.Bukkit.api.Animation;

import java.util.Iterator;
import java.util.List;
import org.functions.Main.Functions;

public class Animation {
    private Functions p = Functions.getMain();

    public Animation(String Message) {
        String x;
        List ls;
        int i;
        for(Iterator var2 = this.p.getSettings().getStringList("AnimationsEnable").iterator(); var2.hasNext(); Message = Message.replace("%animation:" + x + "%", (CharSequence)ls.get(i))) {
            x = (String)var2.next();
            ls = this.p.getSettings().getStringList("Animations." + x + ".Texts");
            i = this.p.getSettings().getInt("Animations." + x + ".Line");
        }

    }

    public static String Animation(String Message) {
        Animation animation = new Animation(Message);
        return animation + "";
    }
}
