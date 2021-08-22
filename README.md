# buter_in_kube
<hr>

#### Алертинг и мониторинг (что, как и зачем)
TODO

#### Декомпозиция сервисов
TODO

#### Паттерны межсервисного взаимодействия
TODO

#### Протоколы межсервисного взаимодействия
- HTTP

#### Аутентификация/авторизация
- Spring Security
- JWT tokens

#### API Gateway / Service Mesh
TODO

#### Нагрузочное тестирование
TODO

#### Используемые БД и хранение данных
- PostgreSQL (бесплатная, есть опыт работы с этой СУБД, легко настроить до рабочей конфигурации)
- Spring JPA / Hibernate

#### Консистентность данных между разными сервисами
TODO

#### Шардинг

#### Кэширование внутри сервисов

#### Распределенные транзакции

<hr>

### TODO Реализация домашних заданий в проектной работе:
* реализовать паттерн Сага для распределенных транзакций - **ДЗ по распределенным транзакциям**;
* декомпозиция микросервисов: пользовательские сценарии, общая схема взаимодействия сервисов, их назначение и зоны ответственности - **ДЗ по паттернам декомпозиции микросервисов**;
* сделать метод создания заказа идемпотентным - **ДЗ по идемпотентности и коммутативности**;
* [выполнить ДЗ по STREAM PROCESSING](HW_STREAM_PROCESSING.md);
* [выполнить ДЗ по API GATEWAY](HW_API_GATEWAY.md);

#### Сборка/деплой
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