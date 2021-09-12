pipeline {
    agent any

    def mvnHome = tool 'maven_3.6.3';

    stages {
        stage ('Checking java version') {
            steps {
                    sh 'java -version'
            }
        }
        stage ('maven version') {
            steps {
                    sh '${mvnHome}/bin/mvn -version'
            }
        }
        stage ('build app test') {
            steps {
                    sh '${mvnHome}/bin/mvn clean install -DskipTests=true '
            }
        }

        stage ('docker image build')
        {
            steps {

                        sh '${mvnHome}/bin/mvn dockerfile:build'

                  }
          }
          stage ('docker image push to Docker Hub') {
            steps {
                    sh '${mvnHome}/bin/mvn dockerfile:push'
            }
        }
    }
}


// node {
//
//     def mvnHome = tool 'maven_3.6.3';
//
//     stage('Checkout') {
//         git 'https://github.com/intarsV/canoe'
//     }
//
//      stage('Deploy') {
// //          sh "docker container stop canoe || true"
// //          sh "docker rm canoe"
// //          sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
//          sh "docker build -f Dockerfile -t canoe target/"
//          sh "docker run -d -p 80:8080 --name canoe --rm canoe:latest"
//     }
// }