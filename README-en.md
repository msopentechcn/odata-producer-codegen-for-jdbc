
OData Producer Codegen for JDBC
---------------------------------------------------------

OData Producer Codegen for JDBC(odata-codegen) can rapidly convert JDBC-backend datasource into OData service in a batch and automaticall way. 
which enable data service consumers to easily access data through standard OData client like MS Office Excel.

This tool can help to improve the productivity for data service providers. A
fter simple configuration and click one button, it will generate OData compliant Restful API,
Which gains immediate data interoperability. 

This tool is now supporting OData v2.

###Required Tools:###
* [Apache Maven] (http://maven.apache.org/)
* A [GIT] (http://git-scm.com/) client

###Clone and build:###
clone the repository to local:

    git clone https://github.com/msopentechcn/odata-producer-codegen-for-jdbc.git

Import Maven project - odata-codegen into Java IDE, and build:

![](/img/maven.png)

Maven will automatically download all the dependencies jars, and build directory structure.

###Config JDBC datasource###
After build phase complete, config JDBC datasource, use MySQL as a example:

Copy MySQL JDBC driver to webapp/WEB-INF/lib/

Modify resources/META-INF/persistence.xml,configure JDBC connection information:

	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<persistence xmlns="http://java.sun.com/xml/ns/persistence"        
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0" 	
	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence  http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
		<persistence-unit name="odata2_jpa2" transaction-type="RESOURCE_LOCAL">
			<provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
			<properties>
				<property name="javax.persistence.jdbc.url"  value="jdbc:mysql://127.0.0.1:3306/database?characterEncoding=UTF-8&amp;characterSetResults=UTF-8&amp;zeroDateTimeBehavior=convertToNull"/>
				<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
				<property name="javax.persistence.jdbc.user" value="username"/>
				<property name="javax.persistence.jdbc.password" value="password"/>
			</properties>
		</persistence-unit>
	</persistence>


###Deploy to app server###
Use Tomcat as a example：

![](/img/deploy.png)

###<a name='id_1'></a>Publish OData Service###
Go to odata-codegen publish UI:[http://localhost:8080/odata-codegen/admin](http://localhost:8080/odata-codegen/admin)

![](/img/interceptor.png)

Move tables to be published to the right list:

![](/img/right-table.png)

Click "GENERATE", It will soon return:

![](/img/status-ok.png)

Restart Tomcat Server.

###Begin to access your OData Service###
After restarting you application server,you can access OData service from:[http://localhost:8080/odata-codegen/OdataServlet.svc](http://localhost:8080/odata-codegen/OdataServlet.svc)

![](/img/odata-servlet.png)

###Note###
re-publish[Publish OData Service](#id_1) is needed if database schema(create/remove table, change table definition) has been changed.
