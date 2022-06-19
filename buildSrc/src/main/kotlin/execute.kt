import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.util.*


fun JSch.execute(
    host: String,
    user: String,
    command: String,
    port: Int = 22,
): String {
    var session: Session? = null
    var channel: ChannelExec? = null
    try {
        session = getSession(user, host, port)

        val config = Properties()
        config["StrictHostKeyChecking"] = "no"
        session.setConfig(config)
        session.connect()
        channel = session.openChannel("exec") as ChannelExec
        channel.setCommand(command)
        channel.inputStream = null
        channel.setErrStream(System.err)
        val input = channel.inputStream
        channel.connect()
        return input.use { it.readAllBytes().decodeToString() }
    } finally {
        channel?.disconnect()
        session?.disconnect()
    }
}


fun sshExecute(
    host: String,
    user: String,
    command: String,
    port: Int = 22,
    shellConfig: JSch.() -> Unit,
): String = JSch().apply(shellConfig).execute(host, user, command, port)