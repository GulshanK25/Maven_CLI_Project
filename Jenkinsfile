pipeline {
    agent any

    tools {
        maven 'Maven'
    }
    triggers {
        pollSCM('H/3 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        success {
            echo 'Build and tests passed!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}