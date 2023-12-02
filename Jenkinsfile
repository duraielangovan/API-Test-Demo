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
     }     
   }
   
   stage('test')
   {
    when{
        
        expression
        {
            
            params.TEST_ENV !='qa'
        }
    }

    steps 
     {
     	echo "Test phase for the environment : ${params.TEST_ENV}"  	
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

}