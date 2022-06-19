import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.jcraft.jsch.SftpATTRS
import java.io.File
import java.io.FileInputStream
import java.util.*


/**
 * https://kodehelp.com/java-program-uploading-folder-content-recursively-from-local-to-sftp-server/
 */
private fun ChannelSftp.recursiveFolderUpload(sourceFile: File, destinationPath: String) {
    if (sourceFile.isFile) {
        // copy if it is a file
        cd(destinationPath)
        if (!sourceFile.name.startsWith(".")) put(
            FileInputStream(sourceFile),
            sourceFile.getName(),
            ChannelSftp.OVERWRITE
        )
    } else {
        val files = sourceFile.listFiles()
        if (files != null && !sourceFile.getName().startsWith(".")) {
            cd(destinationPath)
            var attrs: SftpATTRS? = null
            // check if the directory is already existing
            val directoryPath = destinationPath + "/" + sourceFile.getName()
            try {
                attrs = stat(directoryPath)
            } catch (e: Exception) {
                println("$directoryPath does not exist")
            }
            
            // else create a directory
            if (attrs != null) {
                println("Directory $directoryPath exists IsDir=${attrs.isDir()}")
            } else {
                println("Creating directory $directoryPath")
                mkdir(sourceFile.getName())
            }
            for (f in files) {
                recursiveFolderUpload(f, destinationPath + "/" + sourceFile.getName())
            }
        }
    }
}

fun JSch.uploadDirectory(
    file: File,
    host: String,
    user: String,
    targetDirectory: String,
    port: Int = 22,
) {
    var session: Session? = null
    var channel: ChannelSftp? = null
    try {
        session = getSession(user, host, port)

        val config = Properties()
        config["StrictHostKeyChecking"] = "no"
        session.setConfig(config)
        session.connect() // Create SFTP Session
        channel = session.openChannel("sftp") as ChannelSftp // Open SFTP Channel
        channel.connect()
        channel.cd(targetDirectory) // Change Directory on SFTP Server
        channel.recursiveFolderUpload(file, targetDirectory)
    } finally {
        channel?.disconnect()
        session?.disconnect()
    }
}

fun sshUploadDirectory(
    file: File,
    host: String,
    user: String,
    targetDirectory: String,
    port: Int = 22,
    shellConfig: JSch.() -> Unit,
) = JSch().apply(shellConfig).uploadDirectory(file, host, user, targetDirectory, port)
