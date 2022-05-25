job("Deploy") {
    startOn {
        gitPush { enabled = false }
    }

    gradlew("openjdk:11", "installDist")

    container(displayName = "Deploy via scp", image = "openjdk:11") {
        env["HOST"] = Params("spc-host")
        env["USER"] = Secrets("spc-webmaster-user")
        env["ID"] = Secrets("spc-webmaster-id")

        shellScript {
            interpreter = "/bin/bash"
            content = """
                ls -la
                """.trimIndent()
        }
    }

}