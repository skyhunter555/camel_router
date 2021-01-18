# Service for testing Apache Camel with RabbitMQ 
Library name: camel-router

  Для примера работы с очередью по протоколу AMQP была использована библиотеке apache camel
и брокер сообщений RabbitMQ.  
  В примере создается одна входящая очередь (inputqueue)
и две исходящих очереди (outputorder и outputinvoice)
для разных типов документов.
При отправке сообщения в виде XML файла проводиться проверка типа документа "docType".
Если значение "order", сообщение отправляется в первую исходящую очередь.
Если значение не равно "order", сообщение отправляется во вторую исходящую очередь.
Отправляется по 100 сообщений каждого типа.

После обработки сообщений в логе выводиться информация:
******** SELECTED INVOICE OUTPUT QUEUE FOR DOCTYPE: invoice
******** SELECTED ORDER OUTPUT QUEUE FOR DOCTYPE: order

Ссылки на использованную документацию:

https://camel.apache.org/components/latest/rabbitmq-component.html
https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc

Вопросы:

https://stackoverflow.com/questions/50818500/apache-camel-routing-api-call-to-message-queue

Трудности при реализации:
Не срабатывали условия RouteBuilder. Все сообщения шли во все очереди.
https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html/apache_camel_development_guide/MsgRout#MsgRout-ContentBased

Решение:
Исправление exchangeName в настройке исходящей очереди:
"rabbitmq://user?queue=outputinvoice&autoDelete=false" -> "rabbitmq://invoice?queue=outputinvoice&autoDelete=false"
"rabbitmq://user?queue=outputorder&autoDelete=false" -> "rabbitmq://order?queue=outputorder&autoDelete=false" 
Для каждой очереди должен быть свой exchangeName.

## Example
java -jar camel-router-1.0.0.jar

## Build
mvn clean install
