//declarative programming using predefined keywords

echo 'Stating the test'

pipeline
{

 agent any
 
 stages
 {
   stage('Build')
   {
     steps 
     {
     	bat "mvn -version" 
     	bat "mvn clean"    
     }     
   }
   
   stage('Test')
   {

    steps 
     {
     	echo "Test phase for the environment : ${params.TEST_ENV}" 
     	bat "mvn -D clean install"
     	
     }  
     post {          
                            
       success {
       
       bat "mkdir test-output/reports"
       	
          publishHTML([
                              allowMissing: false, 
                              alwaysLinkToLastBuild: false, 
                              keepAll: true, 
                              includes: '**/*.png',
                              reportDir: 'test-output/reports', 
                              reportFiles: 'Spark.html', 
                              reportName: 'ExtentReport', 
                              reportTitles: 'JENKINSDEMO', 
                              useWrapperFileDirectly: true])
                }
            }
       
   }
  }   
}
