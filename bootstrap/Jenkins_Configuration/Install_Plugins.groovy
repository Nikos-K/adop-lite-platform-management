// Constants
def platformToolsGitURL = "https://github.com/Nikos-K/adop-lite-platform-management.git"

def jenkinsConfigurationFolderName= "/Jenkins_Configuration"
def jenkinsConfigurationFolder = folder(jenkinsConfigurationFolderName) { displayName('Jenkins Configuration') }

// Jobs
def installPlugins = freeStyleJob(jenkinsConfigurationFolderName + "/Install_Plugins")

// Install Jenkins Plugins
installPlugins.with{
  description("This job is responsible for installing all the required Jenkins plugins for ADOP Lite.")
  environmentVariables {
    keepBuildVariables(true)
    keepSystemVariables(true)
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('''#!/bin/bash -e

./Jenkkins_Configuration/install_plugins.sh plugins.txt

''')
  }
  scm {
    git {
      remote {
        name("origin")
        url("${platformToolsGitURL}")
      }
      branch("*/master")
    }
  }
}
