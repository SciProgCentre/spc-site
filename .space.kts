job("Deploy") {
    startOn {
        gitPush { enabled = false }
    }

    container(image = "mipt-npm.registry.jetbrains.space/p/mipt-npm/containers/kotlin-ci:1.0.2") {
        env["SPC_HOST"] = Params("spc-host")
        env["SPC_USER"] = Secrets("spc-webmaster.key-user")
        env["SPC_ID"] = Secrets("spc-webmaster.key-id")
        kotlinScript { api ->
            api.gradlew("uploadDistribution")
            api.gradlew("reloadDistribution")
        }
    }
}

job("Restart service"){
    startOn {
        gitPush { enabled = false }
    }

    container(image = "mipt-npm.registry.jetbrains.space/p/mipt-npm/containers/kotlin-ci:1.0.2") {
        env["SPC_HOST"] = Params("spc-host")
        env["SPC_USER"] = Secrets("spc-webmaster.key-user")
        env["SPC_ID"] = Secrets("spc-webmaster.key-id")
        kotlinScript { api ->
            api.gradlew("reloadDistribution")
        }
    }
}