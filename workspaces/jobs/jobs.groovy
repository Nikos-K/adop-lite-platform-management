// Constants
//def platformToolsGitURL = null;
//try{
//  platformToolsGitURL = "${ADOP_PLATFORM_MANAGEMENT_GIT_URL}"
//}catch(MissingPropertyException exception){
//  // backwards compatible - default to gerrit.
//  platformToolsGitURL = "ssh://jenkins@gerrit:29418/platform-management";
//}

// Folders
def workspaceFolderName = "${WORKSPACE_NAME}"
def workspaceFolder = folder(workspaceFolderName)

def projectManagementFolderName= workspaceFolderName + "/Project_Management"
def projectManagementFolder = folder(projectManagementFolderName) { displayName('Project Management') }

// Jobs
def generateProjectJob = freeStyleJob(projectManagementFolderName + "/Generate_Project")

//def adopLdapEnabled = '';
//
//try{
//  adopLdapEnabled = "${ADOP_LDAP_ENABLED}".toBoolean();
//}catch(MissingPropertyException ex){
//  adopLdapEnabled = true;
//}


// Setup Generate_Project
generateProjectJob.with{
  parameters{
    stringParam("PROJECT_NAME","","The name of the project to be generated.")
    booleanParam('CUSTOM_SCM_NAMESPACE', false, 'Enables the option to provide a custom project namespace for your SCM provider')
  }
  environmentVariables {
    env('WORKSPACE_NAME',workspaceFolderName)
  }
  wrappers {
    preBuildCleanup()
    injectPasswords()
    maskPasswords()
  }
  steps {
    shell('''#!/bin/bash -e

# Validate Variables
pattern=" |'"
if [[ "${PROJECT_NAME}" =~ ${pattern} ]]; then
	echo "PROJECT_NAME contains a space, please replace with an underscore - exiting..."
	exit 1
fi''')
    dsl {
      external("projects/jobs/**/*.groovy")
    }
  }
  scm {
    git {
      remote {
        name("origin")
        url("${ADOP_PLATFORM_MANAGEMENT_GIT_URL}")
      }
      branch("*/master")
    }
  }
}
