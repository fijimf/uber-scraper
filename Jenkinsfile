pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "docker build -t uberscraper:latest -t uberscraper:${TAG_NAME} ."
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}