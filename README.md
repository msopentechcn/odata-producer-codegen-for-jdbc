
OData Producer Codegen for JDBC
---------------------------------------------------------

OData Producer Codegen for JDBC(以下简称odata-codegen)代码生成工具能够快速将基于JDBC协议的数据源批量转换为OData数据服务,用户能够通过OData标准客户端，如MS Office Excel快速获取数据并进行处理，分析和展现。

此工具能够大幅度数据服务提供商进行数据接口发布的效率，通过简单配置后，能够通过一键点击自动批量地将存储在JDBC数据源中的数据添加符合OData标准的Restful API接口，从而使得海量数据迅速具备对外流通的能力。

目前此工具支持OData v2版本。

###所需工具:###
* [Apache Maven] (http://maven.apache.org/)
* A [GIT] (http://git-scm.com/) client

###获取代码并构建:###
克隆这个代码仓库到本地

    git clone https://github.com/msopentechcn/odata-producer-codegen-for-jdbc.git

将Maven工程odata2-codegen导入到Java IDE,执行Maven构建任务：

![](/img/maven.png)

Maven会自动下载所依赖的库文件，并构建目录结构。

###配置JDBC数据源###
构建完成后，配置JDBC数据源，以MySQL数据库为例:

将MySQL JDBC driver包拷贝到webapp/WEB-INF/lib/

修改resources/META-INF/persistence.xml,配置JDBC连接信息:

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


###部署到应用服务器###
以tomcat服务器为例：

![](/img/deploy.png)

###发布OData数据服务###
进入odata2-codegen发布页面:[http://localhost:8080/odata2-codegen/oDataGenerateInterceptor](http://localhost:8080/odata2-codegen/oDataGenerateInterceptor)

![](/img/interceptor.png)

将需要发布的表移到右边列表中:

![](/img/right-table.png)

点击"GENERATE"按钮开始生成,如果成功返回:

![](/img/status-ok.png)

按提示重启tomcat

###开始访问OData数据服务###
重启tomcat后,可通过以下入口访问OData数据服务:[http://localhost:8080/odata2-codegen/OdataServlet.cn](http://localhost:8080/odata2-codegen/OdataServlet.cn)

![](/img/odata-servlet.png)

