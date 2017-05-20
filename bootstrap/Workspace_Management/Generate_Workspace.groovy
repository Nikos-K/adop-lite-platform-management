// Constants
def platformToolsGitURL = null;

try{
  platformToolsGitURL = "${ADOP_PLATFORM_MANAGEMENT_GIT_URL}"
}catch(MissingPropertyException exception){
  // backwards compatible - default to gerrit.
  platformToolsGitURL = "ssh://jenkins@gerrit:29418/platform-management";
}

def workspaceManagementFolderName= "/Workspace_Management"
def workspaceManagementFolder = folder(workspaceManagementFolderName) { displayName('Workspace Management') }

// Jobs
def generateWorkspaceJob = freeStyleJob(workspaceManagementFolderName + "/Generate_Workspace")

// Setup generateWorkspaceJob
generateWorkspaceJob.with{
    parameters{
        stringParam("WORKSPACE_NAME","","The name of the project to be generated.")
    }
    label("docker")
    wrappers {
        preBuildCleanup()
        injectPasswords()
        maskPasswords()
    }
    steps {
        shell('''#!/bin/bash

# Validate Variables
pattern=" |'"
if [[ "${WORKSPACE_NAME}" =~ ${pattern} ]]; then
    echo "WORKSPACE_NAME contains a space, please replace with an underscore - exiting..."
    exit 1
fi''')
        dsl {
            external("workspaces/jobs/**/*.groovy")
        }
    }
    scm {
        git {
            remote {
                name("origin")
                url("${platformToolsGitURL}")
                credentials("adop-jenkins-master")
            }
            branch("*/master")
        }
    }
}
