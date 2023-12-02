//declarative programming using predefined keywords

echo 'Stating the test'

pipeline
{

environment{
    
    JAVA_TOOL_OPTIONS = "-Duser.home = /var/maven"
  }

 agent {
     
     docker{
         
         image 'maven:3.8.7-openjdk-18'      
     }

 }

 stages
 {
   stage('build')
   {
     steps 
     {
     	sh "mvn -version"     
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