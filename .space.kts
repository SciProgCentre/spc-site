job("Deploy") {
    startOn {
        gitPush { enabled = false }
    }

    container(image = "gradle:jdk17-alpine") {
        env["SPC_HOST"] = Params("spc-host")
        env["SPC_USER"] = Secrets("spc-webmaster-user")
        env["SPC_ID"] = Secrets("spc-webmaster-id")
        kotlinScript { api ->
            api.space().projects.automation.deployments.start(
            	project = api.projectIdentifier(),
            	targetIdentifier = TargetIdentifier.Key("spc-site"),
            	version = "current",
            	// automatically update deployment status based on a status of a job
            	syncWithAutomationJob = true
        	)
            api.gradle("uploadDistribution")
        }
    }
}

job("Restart service"){
    startOn {
        gitPush { enabled = false }
    }

    container(image = "gradle:jdk17-alpine") {
        env["SPC_HOST"] = Params("spc-host")
        env["SPC_USER"] = Secrets("spc-webmaster-user")
        env["SPC_ID"] = Secrets("spc-webmaster-id")
        kotlinScript { api ->
            api.gradle("reloadDistribution")
        }
    }
}