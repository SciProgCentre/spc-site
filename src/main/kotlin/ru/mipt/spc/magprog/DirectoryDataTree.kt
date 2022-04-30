package ru.mipt.spc.magprog

import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.data.StaticData
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
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension
import kotlin.reflect.KType
import kotlin.reflect.typeOf

//internal object ByteArrayIOFormat : IOFormat<ByteArray> {
//
//    override val type: KType = typeOf<ByteArray>()
//
//    override fun writeObject(output: Output, obj: ByteArray) {
//        output.writeFully(obj)
//    }
//
//    override fun readObject(input: Input): ByteArray = input.readBytes()
//
//    override fun toMeta(): Meta = Meta {
//        IOFormat.NAME_KEY put "ByteArray"
//    }
//
//}


class DirectoryDataTree(val io: IOPlugin, val path: Path) : DataTree<ByteArray> {
    override val dataType: KType
        get() = typeOf<ByteArray>()

    override val meta: Meta get() = io.readMetaFile(path)

    private fun readFile(filePath: Path): Data<ByteArray> {
        val envelope = io.readEnvelopeFile(filePath, readNonEnvelopes = true)
        val meta = envelope.meta.copy {
            META_FILE_PATH_KEY put filePath.toString()
            META_FILE_EXTENSION_KEY put filePath.extension
            //TODO add other file information
        }
        return StaticData(typeOf<ByteArray>(), envelope.data?.toByteArray() ?: ByteArray(0), meta)
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
    }
}

//TODO add transforming provider instead of ByteArray DataSet