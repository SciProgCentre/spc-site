package space.kscience.snark

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataSource
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.io.IOPlugin
import space.kscience.dataforge.io.readEnvelopeFile
import space.kscience.dataforge.io.readMetaFile
import space.kscience.dataforge.io.toByteArray
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.copy
import space.kscience.dataforge.names.*
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.nio.file.attribute.BasicFileAttributes
import kotlin.coroutines.CoroutineContext
import kotlin.io.path.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class DirectoryDataTree(val io: IOPlugin, val path: Path) : DataTree<ByteArray>, DataSource<ByteArray> {

    override val coroutineContext: CoroutineContext get() = io.context.coroutineContext

    override val dataType: KType
        get() = typeOf<ByteArray>()

    override val meta: Meta get() = io.readMetaFile(path)

    private fun readFile(filePath: Path): Data<ByteArray> {
        val envelope = io.readEnvelopeFile(filePath, readNonEnvelopes = true)
        val meta = envelope.meta.copy {
            META_FILE_PATH_KEY put filePath.toString()
            META_FILE_EXTENSION_KEY put filePath.extension

            val attributes = filePath.readAttributes<BasicFileAttributes>()
            META_FILE_UPDATE_TIME_KEY put attributes.lastModifiedTime().toInstant().toString()
            META_FILE_CREATE_TIME_KEY put attributes.creationTime().toInstant().toString()
        }
        return Data(meta) {
            envelope.data?.toByteArray() ?: ByteArray(0)
        }
    }


    private fun Path.toName(): Name = Name(flatMap {it.nameWithoutExtension.parseAsName().tokens})

    override val items: Map<NameToken, DataTreeItem<ByteArray>>
        get() = path.listDirectoryEntries().associate { childPath ->
            //val fileName = childPath.fileName.nameWithoutExtension

            val item: DataTreeItem<ByteArray> = if (childPath.isDirectory()) {
                DataTreeItem.Node(DirectoryDataTree(io, childPath))
            } else {
                DataTreeItem.Leaf(readFile(childPath))
            }

            val name = childPath.fileName.toName()//Name.parse(fileName)
            if (name.length == 1) {
                name.first() to item
            } else {
                TODO("Segmented names are not supported")
            }
        }

    override val updates: SharedFlow<Name> by lazy {
        val watchService = path.fileSystem.newWatchService()

        path.register(
            watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE
        )

        flow {
            while (true) {
                val key = watchService.take() ?: break
                key.pollEvents().map { it.context() }.filterIsInstance<Path>().forEach {
                    emit(it.toName())
                }
                key.reset()
            }
        }.shareIn(this, SharingStarted.Eagerly)
    }

    companion object {
        val META_FILE_KEY = "file".asName()
        val META_FILE_PATH_KEY = META_FILE_KEY + "path"
        val META_FILE_EXTENSION_KEY = META_FILE_KEY + "extension"
        val META_FILE_CREATE_TIME_KEY = META_FILE_KEY + "created"
        val META_FILE_UPDATE_TIME_KEY = META_FILE_KEY + "update"
    }
}