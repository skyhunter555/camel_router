# Service for testing Apache Camel AMQP 
Library name: camel-router

  � ������� ��������� ���� �������� ������� (jmsComponent:queue:inputqueue)
� ��� ��������� ������� (jmsComponent:queue:orderoutputqueue � jmsComponent:textoutputqueue)
��� ������ ����� ����������.
��� �������� ��������� � ���� XML ����� ����������� �������� ���� ��������� "docType".
���� �������� "order", ��������� ������������ � ������ ��������� �������.
���� �������� �� ����� "order", 

������ XML:
<?xml version="1.0" encoding="windows-1251"?>
<routerDocument>
  <docId>1</docId>
  <docType>order</docType>
</routerDocument>

����� ��������� ��������� � ���� ���������� ����������:
******** SELECTED TEXT OUTPUT QUEUE FOR DOCTYPE: text
******** SELECTED ORDER OUTPUT QUEUE FOR DOCTYPE: order

������ �� �������������� ������������:
https://camel.apache.org/components/latest/amqp-component.html
http://activemq.apache.org/spring-support.html
https://docs.spring.io/spring-boot/docs/2.1.0.M1/reference/html/boot-features-messaging.html
�������:
https://stackoverflow.com/questions/50818500/apache-camel-routing-api-call-to-message-queue

��������� ��� ����������:
������ ��� ������ ���������
Caused by: org.apache.camel.ResolveEndpointFailedException: 
Failed to resolve endpoint: jmsComponent://queue:inputqueue ActiveMQConnectionFactory 
due to: java.lang.IllegalArgumentException: wrong number of arguments
�������:
������ ����������� org.apache.activemq:activemq-camel
�� org.apache.camel:camel-activemq:3.7.0
� ���������� ����� org.apache.activemq.camel.component.ActiveMQComponent
��������� �� org.apache.camel.component.activemq.ActiveMQComponent

## Example
java -jar camel-router-1.0.0.jar

## Build
mvn clean install
