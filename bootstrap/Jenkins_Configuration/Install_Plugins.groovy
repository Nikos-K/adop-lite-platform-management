// Constants
def jenkinsConfigurationFolderName= "/Jenkins_Configuration"
def jenkinsConfigurationFolder = folder(jenkinsConfigurationFolderName) { displayName('Jenkins Configuration') }

// Jobs
def installPlugins = workflowJob(platformManagementFolderName + "/Install_Plugins")

// Install Jenkins Plugins
installPlugins.with{
  description("This job is responsible for installing all the required Jenkins plugins for ADOP Lite.")
}
