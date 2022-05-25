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
            content = "echo \$ID | ssh -i /dev/stdin -r /mnt/space/share/spc-site/ \"\$USER@\$HOST:/opt\""
        }
    }
}