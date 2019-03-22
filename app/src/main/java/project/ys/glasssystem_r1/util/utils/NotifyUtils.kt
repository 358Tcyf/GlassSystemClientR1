package project.ys.glasssystem_r1.util.utils

import io.karn.notify.Notify
import java.util.*


fun notifyDefault(context: android.content.Context, mTitle: String, mText: String) {
    Notify
            .with(context)
            .content {
                title = mTitle
                text = mText
            }
            .show()
}


fun notifyTextList(context: android.content.Context) {
    Notify
            .with(context)
            .asTextList {
                lines = Arrays.asList("New! Fresh Strawberry Cheesecake.",
                        "New! Salted Caramel Cheesecake.",
                        "New! OREO Dream Dessert.")
                title = "New menu items!"
                text = lines.size.toString() + " new dessert menu items found."
            }
            .show()

}

fun notifyBigText(context: android.content.Context) {
    Notify
            .with(context)
            .asBigText {
                title = "Chocolate brownie sundae"
                text = "Try our newest dessert option!"
                expandedText = "Mouthwatering deliciousness."
                bigText = "Our own Fabulous Godiva Chocolate Brownie, Vanilla Ice Cream, Hot Fudge, Whipped Cream and Toasted Almonds.\n" +
                        "\n" +
                        "Come try this delicious new dessert and get two for the price of one!"
            }
            .show()
}

