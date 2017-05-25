import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

instance = Jenkins.getInstance()
globalNodeProperties = instance.getGlobalNodeProperties()
envVarsNodePropertyList = globalNodeProperties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)

newEnvVarsNodeProperty = null
envVars = null

if ( envVarsNodePropertyList == null
        || envVarsNodePropertyList.size() == 0 ) {

    newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}

envVars.put("ADOP_PLATFORM_MANAGEMENT_GIT_URL","https://github.com/Nikos-K/adop-lite-platform-management.git")
envVars.put("ADOP_PLATFORM_MANAGEMENT_VERSION", "*/master")
envVars.put("ADOP_ACL_ENABLED", "false")
envVars.put("ADOP_LDAP_ENABLED", "false")
envVars.put("PLUGGABLE_SCM_PROVIDER_PROPERTIES_PATH", "/var/jenkins_home/userContent/datastore/pluggable/scm")
envVars.put("PLUGGABLE_SCM_PROVIDER_PATH", "/var/jenkins_home/userContent/job_dsl_additional_classpath/")
envVars.put("CARTRIDGE_SOURCES", "https://raw.githubusercontent.com/Accenture/adop-cartridges/master/cartridges.yml")
envVars.put("LDAP_ROOTDN", "")

instance.save()
