pipeline {
    agent any

    tools {
        maven 'Maven'
    }
    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Checkout') {
            git branch: 'currencyConverterService_file_unit_test',
                url: 'https://github.com/GulshanK25/Maven_CLI_Project'
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