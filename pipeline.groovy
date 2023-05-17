pipeline {
  agent any

  stages {
    stage('Check Namespace') {
      steps {
        script {
          def namespaceExists = sh(script: "kubectl get namespace wp", returnStatus: true)
          if (namespaceExists == 0) {
            echo "Namespace 'wp' already exists."
          } else {
            echo "Namespace 'wp' does not exist. Creating..."
            sh "kubectl create namespace wp"
          }
        }
      }
    }
    
    stage('Check WordPress') {
      steps {
        script {
          def releaseExists = sh(script: "helm list -n wp | grep wordpress", returnStatus: true)
          if (releaseExists == 0) {
            echo "WordPress Helm chart is already installed."
          } else {
            echo "WordPress Helm chart is not installed. Installing..."
            sh "helm install wordpress bitnami/wordpress -n wp -f path/to/your/values.yaml"
          }
        }
      }
    }
  }
}
