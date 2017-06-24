// Constants
def pluggableGitURL = "https://github.com/Accenture/adop-pluggable-scm"

def jenkinsConfigurationFolderName= "/Jenkins_Configuration"
def jenkinsConfigurationFolder = folder(jenkinsConfigurationFolderName) { displayName('Jenkins Configuration') }

// Jobs
def setupAdopPluggableScmLibrary = freeStyleJob(jenkinsConfigurationFolderName + "/Setup_Adop_Pluggable_Scm_Library")

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
  scm {
    git {
      remote {
        name("origin")
        url("${pluggableGitURL}")
      }
      branch("*/master")
    }
  }
  steps {
    //systemGroovyCommand(readFileFromWorkspace('add_groovy_profile.template'))
    shell('''#!/bin/bash -ex
    mkdir -p $PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH $PLUGGABLE_SCM_PROVIDER_PATH
    mkdir -p ${PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH}/CartridgeLoader ${PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH}/ScmProviders

    echo "Extracting Pluggable library to additional classpath location: ${WORKSPACE}/job_dsl_additional_classpath/"
    cp -r ${WORKSPACE}/src/main/groovy/pluggable/ $PLUGGABLE_SCM_PROVIDER_PATH
    echo "******************"

    echo "Library contents: "
    ls ${PLUGGABLE_SCM_PROVIDER_PATH}/pluggable/scm/

    echo "Adding Bitbucket SCM Provider property files"
    tee ${PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH}/CartridgeLoader/adop-bitbucket.props << EOF
    loader.id=adop-bitbucket
    loader.credentialId=pluggable-scm
    bitbucket.endpoint=innersource.accenture.com
    bitbucket.endpoint.context=/
    bitbucket.protocol=https
    bitbucket.port=443
    EOF

    tee ${PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH}/ScmProviders/adop-bitbucket.ssh.props << EOF
    scm.loader.id=adop-bitbucket
    scm.id=adop-bitbucket-ssh
    scm.type=bitbucket
    scm.protocol=ssh
    scm.port=22
    scm.url=https://innersource.accenture.com
    EOF

    tee ${PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH}/ScmProviders/adop-bitbucket.https.props << EOF
    scm.loader.id=adop-bitbucket
    scm.id=adop-bitbucket-https
    scm.type=bitbucket
    scm.protocol=https
    scm.port=443
    scm.url=https://innersource.accenture.com
    EOF''')
    shell('''#!/bin/bash
    echo ${PLUGGABLE_SCM_PROVIDER_PATH}
    wget https://raw.githubusercontent.com/Accenture/adop-jenkins/master/resources/scriptler/retrieve_scm_props.groovy
    sed -i "s,###SCM_PROVIDER_PROPERTIES_PATH###,$PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH,g" retrieve_scm_props.groovy
''')
  }
}
