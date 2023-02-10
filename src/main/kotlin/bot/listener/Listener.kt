package bot.listener

import org.pircbotx.PircBotX
import org.pircbotx.dcc.ReceiveFileTransfer
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.IncomingFileTransferEvent
import org.pircbotx.hooks.events.PrivateMessageEvent
import org.pircbotx.hooks.types.GenericMessageEvent
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes


class CallbackListener : ListenerAdapter() {
    override fun onGenericMessage(event: GenericMessageEvent) {
        //When someone says ?helloworld respond with "Hello World"
        if (event.message.startsWith("?helloworld")) event.respond("Hello world!")
    }

    override fun onPrivateMessage(event: PrivateMessageEvent) {
        if (event.message.startsWith("!d ")) {
            event.respondPrivateMessage("Initiating Download...")
//            event.getBot<PircBotX>().sendIRC().message("CR-HOLLAND|NEW", event.message.drop(3))
            event.getBot<PircBotX>().sendIRC().message("CR-HOLLAND|NEW", "xdcc send #4226")
        }
    }

    override fun onIncomingFileTransfer(event: IncomingFileTransferEvent) {
        super.onIncomingFileTransfer(event)
        // Create Path using your download directory
        val path = Paths.get("upload/" + event.safeFilename).toAbsolutePath()
        println(path)
        val fileTransfer: ReceiveFileTransfer
        // If the file exists, resume from a position
        fileTransfer = if (path.toFile().exists()) {
            // Use BasicFileAttributes to find position to resume
            event.acceptResume(
                path.toFile(),
                Files.readAttributes(path, BasicFileAttributes::class.java).size()
            )
        } else {
            event.accept(path.toFile())
        }

        // Give ReceiveFileTransfer to a new tracking thread or block here
        // with a while (fileTransfer.getFileTransferStatus().isFinished()) loop
        fileTransfer.fileTransferStatus.averageBytesPerSecond
    }
}

