# Service for testing Apache Camel with ActiveMQ 
Library name: camel-router

  Для примера работы с очередью по протоколу AMQP была использована библиотеке apache camel
и встроенный брокер сообщений ActiveMQ.  
  При запуске приложения, создается экзепляр брокера сообщений по адресу: tcp://localhost:61616  
  В примере создается одна входящая очередь (jmsComponent:queue:inputqueue)
и две исходящих очереди (jmsComponent:queue:orderoutputqueue и jmsComponent:invoiceoutputqueue)
для разных типов документов.
При отправке сообщения в виде XML файла проводиться проверка типа документа "docType".
Если значение "order", сообщение отправляется в первую исходящую очередь.
Если значение не равно "order", сообщение отправляется во вторую исходящую очередь.
Отправляется по 100 сообщений каждого типа.

После обработки сообщений в логе выводиться информация:
******** SELECTED INVOICE OUTPUT QUEUE FOR DOCTYPE: invoice
******** SELECTED ORDER OUTPUT QUEUE FOR DOCTYPE: order

Ссылки на использованную документацию:

https://camel.apache.org/components/latest/amqp-component.html
http://activemq.apache.org/spring-support.html
https://docs.spring.io/spring-boot/docs/2.1.0.M1/reference/html/boot-features-messaging.html

Вопросы:

https://stackoverflow.com/questions/50818500/apache-camel-routing-api-call-to-message-queue

Трудности при реализации:
Ошибка при старте контекста

Caused by: org.apache.camel.ResolveEndpointFailedException: 
Failed to resolve endpoint: jmsComponent://queue:inputqueue ActiveMQConnectionFactory 
due to: java.lang.IllegalArgumentException: wrong number of arguments

Решение:
Замена зависимости org.apache.activemq:activemq-camel
на org.apache.camel:camel-activemq:3.7.0
В результате класс org.apache.activemq.camel.component.ActiveMQComponent
изменился на org.apache.camel.component.activemq.ActiveMQComponent

## Example
java -jar camel-router-1.0.0.jar

## Build
mvn clean install
