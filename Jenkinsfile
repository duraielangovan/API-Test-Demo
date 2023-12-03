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
                 
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.               
       success {
          publishHTML([
                              allowMissing: false, 
                              alwaysLinkToLastBuild: false, 
                              keepAll: false, 
                              reportDir: 'Reports', 
                              reportFiles: 'Spark.html', 
                              reportName: "ExtentReport${env.JOB_NAME}", 
                              reportTitles: '', 
                              useWrapperFileDirectly: true])
                }
            }
        
   }
  }   
 
}