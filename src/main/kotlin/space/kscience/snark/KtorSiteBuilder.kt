package space.kscience.snark

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.*
import io.ktor.server.plugins.origin
import io.ktor.server.request.host
import io.ktor.server.request.port
import io.ktor.server.routing.Route
import io.ktor.server.routing.createRouteFromPath
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.HTML
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.withDefault
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.cutLast
import space.kscience.dataforge.names.endsWith
import java.nio.file.Path
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@PublishedApi
internal class KtorSiteBuilder(
    override val snark: SnarkPlugin,
    override val data: DataTree<*>,
    override val meta: Meta,
    private val baseUrl: String,
    private val ktorRoute: Route,
) : SiteBuilder {

    override fun assetFile(remotePath: String, file: Path) {
        ktorRoute.file(remotePath, file.toFile())
    }

    override fun assetDirectory(remotePath: String, directory: Path) {
        ktorRoute.static(remotePath) {
            files(directory.toFile())
        }
    }

    private fun resolveRef(baseUrl: String, ref: String) = if (baseUrl.isEmpty()) {
        ref
    } else if (ref.isEmpty()) {
        baseUrl
    } else {
        "${baseUrl.removeSuffix("/")}/$ref"
    }


    inner class KtorPageBuilder(
        val pageBaseUrl: String,
        override val meta: Meta = this@KtorSiteBuilder.meta,
    ) : PageBuilder {
        override val context: Context get() = this@KtorSiteBuilder.context
        override val data: DataTree<*> get() = this@KtorSiteBuilder.data

        override fun resolveRef(ref: String): String = resolveRef(pageBaseUrl, ref)

        override fun resolvePageRef(pageName: Name): String = if (pageName.endsWith(SiteBuilder.INDEX_PAGE_TOKEN)) {
            resolveRef(pageName.cutLast().toWebPath())
        } else {
            resolveRef(pageName.toWebPath())
        }
    }

    override fun page(route: Name, content: context(PageBuilder, HTML)() -> Unit) {
        ktorRoute.get(route.toWebPath()) {
            call.respondHtml {
                val request = call.request
                //substitute host for url for backwards calls
                val url = URLBuilder(baseUrl).apply {
                    protocol = URLProtocol.createOrDefault(request.origin.scheme)
                    host = request.host()
                    port = request.port()
                }
                val pageBuilder = KtorPageBuilder(url.buildString())
                content(pageBuilder, this)
            }
        }
    }

    override fun route(
        routeName: Name,
        dataOverride: DataTree<*>?,
        metaOverride: Meta?,
        setAsRoot: Boolean,
    ): SiteBuilder = KtorSiteBuilder(
        snark = snark,
        data = dataOverride ?: data,
        meta = metaOverride?.withDefault(meta) ?: meta,
        baseUrl = if (setAsRoot) {
            resolveRef(baseUrl, routeName.toWebPath())
        } else {
            baseUrl
        },
        ktorRoute = ktorRoute.createRouteFromPath(routeName.toWebPath())
    )


    override fun assetResourceFile(remotePath: String, resourcesPath: String) {
        ktorRoute.resource(resourcesPath, resourcesPath)
    }

    override fun assetResourceDirectory(resourcesPath: String) {
        ktorRoute.resources(resourcesPath)
    }
}

inline fun Route.snarkSite(
    snark: SnarkPlugin,
    data: DataTree<*>,
    meta: Meta = data.meta,
    block: SiteBuilder.() -> Unit,
) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(KtorSiteBuilder(snark, data, meta, "", this@snarkSite))
}

fun Application.snarkSite(
    snark: SnarkPlugin,
    data: DataTree<*> = DataTree.empty(),
    meta: Meta = data.meta,
    block: SiteBuilder.() -> Unit,
) {
    routing {
        snarkSite(snark, data, meta, block)
    }
}