pipeline {
    agent any
    tools {
            maven 'apache-maven-3.5.3'
        }

    stages {
        stage ('Compile Stage') {

            steps {
                    bat 'mvn clean compile -DskipTests'
            }
        }

        stage ('Testing Stage') {

            steps {
                    echo 'mvn test'
            }
        }


        stage ('Deployment Stage') {
            steps {
                    echo 'mvn clean compile'
            }
        }
    }
}