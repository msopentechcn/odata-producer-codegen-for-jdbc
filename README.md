
OData Producer Codegen for JDBC
---------------------------------------------------------

OData Producer Codegen for JDBC能够快速的将基于JDBC协议的数据源发布为OData数据服务，目前支持生成OData v2版本。

###所需工具:###
* [Apache Maven] (http://maven.apache.org/)
* A [GIT] (http://git-scm.com/) client

###获取代码并构建:###
克隆这个代码仓库到本地

    git clone https://github.com/msopentechcn/odata-producer-codegen-for-jdbc.git

将Maven工程odata-codegen导入到Java IDE,执行Maven构建任务：

![](/img/maven.png)

Maven会自动下载所依赖的库文件，并构建目录结构。

###配置JDBC数据源###
构建完成后，配置JDBC数据源，以MySQL数据库为例:

将MySQL JDBC driver包拷贝到webapp/WEB-INF/lib/

修改resources/META-INF/persistence.xml,配置JDBC连接信息:

	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<persistence xmlns="http://java.sun.com/xml/ns/persistence"        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0" 	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
	<persistence-unit name="odata2_jpa2" transaction-type="RESOURCE_LOCAL">
		<provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
		<properties>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://127.0.0.1:3306/database?characterEncoding=UTF-8&amp;characterSetResults=UTF-8&amp;zeroDateTimeBehavior=convertToNull"/>
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
			<property name="javax.persistence.jdbc.user" value="username"/>
			<property name="javax.persistence.jdbc.password" value="password"/>
		</properties>
	</persistence-unit>
	</persistence>


###部署到应用服务器###
以tomcat服务器为例：

![](/img/deploy.png)

###选择待发布数据，一键发布###
odata-codegen 控制台:[http://localhost:8080/odata2-codegen/oDataGenerateInterceptor](http://localhost:8080/odata2-codegen/oDataGenerateInterceptor)

![](/img/interceptor.png)

将需要发布的表移到右边列表中:

![](/img/right-table.png)

点击"GENERATE"按钮开始生成,如果成功返回:

![](/img/status-ok.png)

按提示重启tomcat

###开始访问OData数据服务###
重启tomcat后,可通过以下入口访问OData数据服务:[http://localhost:8080/odata2-codegen/OdataServlet.cn](http://localhost:8080/odata2-codegen/OdataServlet.cn)

![](/img/odata-servlet.png)




