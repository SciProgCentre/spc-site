job("Deploy") {
    startOn {
        gitPush { enabled = false }
    }
    
    container(image = "openjdk:11") {
        kotlinScript { api ->
            api.gradlew("installDist")
        }
        
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