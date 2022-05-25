job("Deploy") {
    container(displayName = "Build and install", image = "openjdk:11") {
        kotlinScript { api ->
            // here can be your complex logic
            api.gradlew("installDist")
        }
    }
    container(displayName = "Deploy", image = "openjdk:11") {
        env["HOST"] = Params("sciprog-host")
        env["ID"] = Secrets("sciprog-webmaster-id")

        shellScript {
            interpreter = "/bin/bash"
            content = """
                ls -la
                """.trimIndent()
        }
    }

}