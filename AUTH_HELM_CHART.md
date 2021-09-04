#### Сборка/деплой (with helm)
- docker build . -t trthhrts/authinkube:0.0.1
- docker push trthhrts/authinkube:0.0.1
- helm install postgres bitnami/postgresql -f ./auth-pg/values.yaml
- helm init auth-in-kube-chat
- helm install auth-in-kube ./auth-in-kube-chart
- minikube service auth-in-kube-chart-service --url

#### Удалить все
- kubectl delete all --all
- kubectl get pvc
- kubectl delete pvc data-postgres-0
- kubectl get pv
- kubectl delete pv ...