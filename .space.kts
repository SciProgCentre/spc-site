import circlet.pipelines.script.put

job("Deploy") {
    startOn {
        gitPush { enabled = false }
    }

    container(image = "openjdk:11") {
        kotlinScript { api ->
            api.gradlew("installDist")
            api.fileShare().put(java.io.File("build/install"))
        }
    }

    container(image = "openjdk:11") {
        env["HOST"] = Params("spc-host")
        env["USER"] = Secrets("spc-webmaster-user")
        env["ID"] = Secrets("spc-webmaster-id")

        shellScript {
            interpreter = "/bin/bash"
            content = """
            	echo ${'$'}ID > id.key
                chmod 400 id.key
                scp -r -i id.key /mnt/space/share/spc-site/ "${'$'}USER@${'$'}HOST:/opt"
            """.trimIndent()
        }
    }
}

job("Restart service"){
    startOn {
        gitPush { enabled = false }
    }
    
    container(image = "openjdk:11") {
        env["HOST"] = Params("spc-host")
        env["USER"] = Secrets("spc-webmaster-user")
        env["ID"] = Secrets("spc-webmaster-id")

        shellScript {
            interpreter = "/bin/bash"
            content = """
            	echo ${'$'}ID > id_ed25519
                chmod 400 id_ed25519
                ssh -i id_ed25519 -o StrictHostKeyChecking=no -o HostKeyAlgorithms=ssh-ed25519 -t "${'$'}USER@${'$'}HOST" "systemctl restart sciprog-site"
            """.trimIndent()
        }
    }
}