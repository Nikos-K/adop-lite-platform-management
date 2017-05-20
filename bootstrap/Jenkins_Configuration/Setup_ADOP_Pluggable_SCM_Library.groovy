// Constants
def pluggableGitURL = "https://github.com/Accenture/adop-pluggable-scm"

def jenkinsConfigurationFolderName= "/Jenkins_Configuration"
def jenkinsConfigurationFolder = folder(jenkinsConfigurationFolderName) { displayName('Jenkins Configuration') }

// Jobs
def setupAdopPluggableScmLibrary = workflowJob(jenkinsConfigurationFolderName + "/Setup_Adop_Pluggable_Scm_Library")

// Install Jenkins Plugins
setupAdopPluggableScmLibrary.with{
  description("This job is responsible for setting up the ADOP pluggable scm library.")
  environmentVariables {
    keepBuildVariables(true)
    keepSystemVariables(true)
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('''#!/bin/bash -ex

echo "Extracting Pluggable library to additonal classpath location: ${PLUGGABLE_SCM_PROVIDER_PATH}"
cp -r src/main/groovy/pluggable/ ${PLUGGABLE_SCM_PROVIDER_PATH}
echo "******************"

echo "Library contents: "
ls ${PLUGGABLE_SCM_PROVIDER_PATH}pluggable/scm/
''')
  }
  scm {
    git {
      remote {
        name("origin")
        url("${pluggableGitURL}")
      }
      branch("*/master")
    }
  }
}
