package project.ys.glasssystem_r1.util.utils

import android.support.v4.app.NotificationCompat
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

fun notifyDefault(context: android.content.Context) {
    Notify
            .with(context)
            .content {
                title = "New dessert menu"
                text = "The Cheesecake Factory has a new dessert for you to try!"
            }
            .stackable {
                key = "test_key"
                summaryContent = "test summary content"
                summaryTitle = { count -> "Summary title" }
                summaryDescription = { count -> count.toString() + " new notifications." }
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


fun notifyMessage(context: android.content.Context) {
    Notify
            .with(context)
            .asMessage {
                userDisplayName = "Karn"
                conversationTitle = "Sundae chat"
                messages = Arrays.asList(
                        NotificationCompat.MessagingStyle.Message("Are you guys ready to try the Strawberry sundae?",
                                System.currentTimeMillis() - (6 * 60 * 1000), // 6 Mins ago
                                "Karn"),
                        NotificationCompat.MessagingStyle.Message("Yeah! I've heard great things about this place.",
                                System.currentTimeMillis() - (5 * 60 * 1000), // 5 Mins ago
                                "Nitish"),
                        NotificationCompat.MessagingStyle.Message("What time are you getting there Karn?",
                                System.currentTimeMillis() - (1 * 60 * 1000), // 1 Mins ago
                                "Moez")
                )
            }
            .show()
}