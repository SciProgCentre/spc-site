package space.kscience.snark

import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.io.IOPlugin
import space.kscience.dataforge.io.readEnvelopeFile
import space.kscience.dataforge.io.readMetaFile
import space.kscience.dataforge.io.toByteArray
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.copy
import space.kscience.dataforge.names.NameToken
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.plus
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class DirectoryDataTree(val io: IOPlugin, val path: Path) : DataTree<ByteArray> {
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
        return Data(meta){
            envelope.data?.toByteArray() ?: ByteArray(0)
        }
    }


    override val items: Map<NameToken, DataTreeItem<ByteArray>>
        get() = path.listDirectoryEntries().associate { childPath ->
            val fileName = childPath.fileName.nameWithoutExtension

            val item: DataTreeItem<ByteArray> = if (childPath.isDirectory()) {
                DataTreeItem.Node(DirectoryDataTree(io, childPath))
            } else {
                DataTreeItem.Leaf(readFile(childPath))
            }

            NameToken(fileName) to item
        }

    companion object {
        val META_FILE_KEY = "file".asName()
        val META_FILE_PATH_KEY = META_FILE_KEY + "path"
        val META_FILE_EXTENSION_KEY = META_FILE_KEY + "extension"
        val META_FILE_CREATE_TIME_KEY = META_FILE_KEY +"created"
        val META_FILE_UPDATE_TIME_KEY = META_FILE_KEY +"update"
    }
}

//TODO add transforming provider instead of ByteArray DataSet