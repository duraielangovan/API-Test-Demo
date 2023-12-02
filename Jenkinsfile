//declarative programming using predefined keywords

echo 'Stating the test'

pipeline
{
environment{
    
    JAVA_TOOL_OPTIONS = "-Duser.home = /var/maven"
  }

 agent {
     
     docker{
         
         image "maven:3.8.7-openjdk-18"
         label "docker"
         args "-v /tmp/maven:/var/maven/.m2 -e MAVEN_CONFIG=/var/maven/.m2"
     }

 }

 stages
 {
   stage('build')
   {
     steps 
     {
     	echo "Build number ${env.BUILD_ID} is running  on ${env.JENKINS_URL}"
     	sh "mvn -version"
     	sh "mvn clean"
     }     
   }
   
   stage('test')
   {
    when{
        
        expression
        {
            
            params.TEST_ENV !='prod'
        }
    }

    steps 
     {
     	echo "Test phase for the environment : ${params.TEST_ENV}"  
     	sh "mvn install"	
     }     
   }
   
   stage('Deploy')
   {
     steps 
     {
     	echo "Status of current build is ${currentBuild.result}"
     }     
   }     
 }
 post {
     
     always{
         
         cleanWs()
     }
 }

}