package project.ys.glasssystem_r1.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.SubMenu;

public class ViewUtils {
    public static void selectMenuItem(@NonNull Menu menu, @IdRes int itemId, boolean findSub) {
        boolean find = false;
        for (int i = 0; i < menu.size(); i++) {
            if (!findSub) {
                menu.getItem(i).setChecked(menu.getItem(i).getItemId() == itemId);
            } else {
                if (menu.getItem(i).getItemId() == itemId) {
                    find = true;
                }
            }
        }

        if (!findSub) {
            return;
        } else if (find) {
            selectMenuItem(menu, itemId, false);
        } else {
            for (int i = 0; i < menu.size(); i++) {
                SubMenu subMenu = menu.getItem(i).getSubMenu();
                if (subMenu != null)
                    selectMenuItem(subMenu, itemId, true);
            }
        }
    }

}
