# What is ADOP-Lite?
ADOP-Lite is the name given to adopting just the Cartridge loader into a pre-existing instance of Jenkins. ADOP-Lite enables Jenkins instances (that do not come pre-configured with the Load_Platform & adop-platform-management jobs) to load cartridges and to benefit from multi-tenancy via workspaces & projects.

For many people using their own dedicated full instance of ADOP or using ADOP via the ADOP Enterprise edition meets their needs. However, not everyone is lucky enough to have control over creating/recreating the tooling instances. If you are tied to using a pre-existing Jenkins and do not have full control over your infrastructure, ADOP-LITE is your ticket to the wonderful world of Cartridges.

# Pre-requisites for getting into ADOP LITE (Work-In-Progress)
1. A Jenkins instance that meets the minimum specifications and either runs on Linux or has access to a slave running Linux.
2. Access to a cloud account so that you can re-use cartridges that create cloud resources.
3. Access to Docker so that you can re-use cartridges that use Docker.

Recommended Jenkins Specifications (Work-In-Progress)

# Adopting ADOP LITE (Work-In-Progress)
![HomePage](https://github.com/Nikos-K/adop-lite-platform-management/blob/master/img/ADOP_Lite_Diag_1.png)
1. Install job-dsl:1.48 , mask-passwords:2.8, groovy:1.29, conditional-buildstep:1.3.5, parameterized-trigger:2.32, ws-cleanup:0.30, envinject:1.92.1 & git:3.0.0 plugins and restart Jenkins.
2. Create a new Freestyle project with name Load_ADOP_Lite. As part of the new project add a Process Job DSLs build step, and provide the contents of the [Load_ADOP_Lite.groovy](https://github.com/Nikos-K/adop-lite-platform-management/blob/master/load_adop_lite_seed/Load_ADOP_Lite.groovy) script before saving the project.
3. Trigger a Build for the Load_ADOP_Lite job. When the build execution finishes, it should load the ADOP-Lite seed job in the Load_ADOP_Lite project. 
4. Navigate to the Load_ADOP_Lite project and then select the Build with Parameters option. Make sure that the right values for the build parameters are selected and then click Build. After the completion of the job you should be able to see the Jenkins Configuration and Workspace Management folders.