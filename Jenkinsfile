//declarative programming using predefined keywords

echo 'Stating the test'

pipeline
{
 agent any

 stages
 {
   stage('build')
   {
     steps 
     {
     	echo "Build number ${env.BUILD_ID} is running  on ${env.JENKINS_URL}"
     	bat "mvn -version"
     	bat "mvn clean"
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
     	bat "mvn install"	
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
         
         cleanws()
     }
 }

}