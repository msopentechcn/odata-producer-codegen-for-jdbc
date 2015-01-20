
odata2-codegen:是ODatav2版本代码自动生成平台™
---------------------------------------------------------

odata2-codegen能帮助我们快速的将数据库以ODatav2协议暴露出去.

###所需工具:###
* [Apache Maven] (http://maven.apache.org/)
* A [GIT] (http://git-scm.com/) client

###克隆和构建:###
克隆这个仓库到本地

    '''git clone https://github.com/msopentechcn/odata-producer-codegen-for-jdbc.git'''

将工程odata2-codegen导入到IDE工具里,等待maven自动构建完成.
1[](/img/maven.png)

###配置META-INF/persistence.xml连接数据库###
以mysql为列

        <?xml version="1.0" encoding="UTF-8" standalone="no"?><persistence xmlns="http://java.sun.com/xml/ns/persistence"                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0"                                xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
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


###部署###
将odata2-codegen部署tomcat服务器中
![](/img/deploy.png)

###进入控制台###
odata2-codegen 控制台:[http://localhost:8080/odata2-codegen/oDataGenerateInterceptor](http://localhost:8080/odata2-codegen/oDataGenerateInterceptor)

![](/img/interceptor.png)

将需要暴露的表移到右边列表中:

![](/img/right-table.png)

点击"GENERATE"按钮开始生成,如果成功返回:
![](/img/status-ok.png)

按提示重启tomcat

###OData入口###
重启tomcat后,请求OData入口:[http://localhost:8080/odata2-codegen/OdataServlet.cn](http://localhost:8080/odata2-codegen/OdataServlet.cn)
![](/img/odata-servlet.png)




