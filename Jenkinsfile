pipeline {
    agent any
    options {
        timeout(time: 21, unit: 'MINUTES')                                        // pipeline timeout -limit
        buildDiscarder(logRotator(numToKeepStr: '10'))                            // keep builds -limit
        disableConcurrentBuilds()
    }
    environment {        
	      HOME = '/tmp'
        MICROSERVICE = "${env.JOB_NAME.tokenize('/')[0]}"
        DOCKER_IMAGE_BUILD = ''
        DOCKER_VIXTRUX_CUSTOMERS = "token-dockerhub"
        DOCKER_REGISTRY_IMG="vixtrux/${MICROSERVICE}" 
        // JENKINS-ACCOUNT FIREBASE       
        //JK_FIREBASE_TOKEN_ADMIN="${FirebaseToken}"                               // firebase account token


    }
    stages {
        /* useless stage to get the when block at the top level */
        stage('Meta') {
            /* only start a build when something changed inside the microservice or it is triggered manually */
            when {
                anyOf {
                    triggeredBy cause: 'UserIdCause'
                    changeset "$MICROSERVICE/**"
                      }
                 }

     stages {       

   stage('Clean') {
            steps {
 	     dir('customers') {
             script {               
			  echo 'Clean Old Files ....'
			  sh 'mvn clean'	
		          sh 'ls'                
                    } 
	         }               
               }
        }

	stage('Build'){
            steps {
 	     dir('customers') {
             script {            	
			  echo 'Building ....'
			  sh 'mvn compile'
			  sh 'ls'
                    } 
	        }               
              }
	}

	stage('Units-Tests'){
            steps {
 	     dir('customers') {
             script {            	
			  echo 'Testing Unit ...'	         

               try{
                       sh 'mvn test'                   
                    }catch(Exception ex)
                    {
                      echo ' error api test'
                    }   
                  // junit 'newman.xml'                  
			  
          
                    } 
	        }               
              }
	 }
	       

       stage('Restore-Packages') {
            steps {
 	     dir('customers') {
                    script {
                    echo 'Restore packages............' 
                    sh 'mvn package'			  
                    sh 'ls'              
                     } 
	        }               
              }
        }
        


     stage('Build/Upload-Image'){
              steps {    
                dir('customers') {    
                script {                    
                echo 'Build Upload Image Docker ..........'
                sh 'pwd'
                echo 'trtrt'
                echo  "$DOCKER_REGISTRY_IMG:$BUILD_NUMBER"
                echo  "$DOCKER_VIXTRUX_CUSTOMERS"
                 def DOCKER_IMAGE_BUILD =docker.build("$DOCKER_REGISTRY_IMG:$BUILD_NUMBER"," --rm -f Dockerfile .")
                echo 'sdssddsdsd'
                docker.withRegistry("", "$DOCKER_VIXTRUX_CUSTOMERS") {             
                        DOCKER_IMAGE_BUILD.push("$BUILD_NUMBER")                          
                            }	              
                 } 
                   }                         
                }
        }


          stage('Remove-Image'){
               steps { 	 
                  dir('customers') {
                script {                    
                    echo 'Remove Image Docker ........................'
                        sh 'pwd'
                        sh "docker rmi $DOCKER_REGISTRY_IMG:$BUILD_NUMBER"
                        sh "docker system prune -f"   
                     } 
                  }                        
                }
         }


 	stage('Deploy-Release-DEV'){
            when { 
                    branch 'main' 
                 }
            steps {
 	     dir('customers') {
             script {
                           	
			   echo 'Deployment environment ..........'                          
                           docker.withRegistry("", "$DOCKER_VIXTRUX_CUSTOMERS") {  
                              sh  "docker-compose up -d"    //deploy ec2                      
                            }	                          
                           	     
                  } 
	        }               
             }
	 }


        stage('Stress-Tests'){
                    when { 
                        branch 'main' 
                    }
                steps {
            dir('customers') {
                script {            	
                echo 'Testing Unit ....'		      
                sh '/home/ubuntu/apache-jmeter-5.4.1/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t jmeter-test.jmx || exit 0'
               
            
                        } 
                }                        
              }
        }  




       }
     }
    }	

}
