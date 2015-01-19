#使用com-msopentech-odata-gennerate.jar快速发布OData v2服务#

简介:com-msopentech-odata-gennerate.jar是OData接口快速生成器,能帮助我们以OData协议快速的将数据库暴露出去.

##使用步骤:##
###一:下载 sample/ODataV2GenerateSample实例项目到本地工作空间###
![](/img/down-sample.png)

###二:配置tomcat启动时的CLASSPATH=H:\apache-tomcat-7.0.52\webapps\ODataV2GenerateSample\WEB-INF\lib\*.jar(因为应用在运行的时候会编译生成的源码,所以来的架包为:javax.persistence-2.1.0.jar.所以将CLASSPATH环境指向应用的lib\*.jar是最直接的方法)###
在windows环境下修改catalina.bat文件:
![](/img/catalina-bat.png)

在linux环境下修改catalina.sh文件:
![](/img/catalina-sh.png)



###三:配置persistence.xml数据源###
        <?xml version="1.0" encoding="UTF-8" standalone="no"?><persistence xmlns="http://java.sun.com/xml/ns/persistence"                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0"                                xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
	<persistence-unit name="odata2_jpa2" transaction-type="RESOURCE_LOCAL">
	  <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
          <properties>  
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://127.0.0.1:3306/TS"/>  
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>  
            <property name="javax.persistence.jdbc.user" value="root"/>  
            <property name="javax.persistence.jdbc.password" value="password"/>  
          </properties> 	
	</persistence-unit>
        </persistence>

###四:部署ODataV2GenerateSample###
![](/img/deploy.png)

###五:请求OData generate 控制台:[http://localhost:8080/ODataV2GenerateSample/oDataGenerateInterceptor](http://localhost:8080/ODataV2GenerateSample/oDataGenerateInterceptor)###
![](/img/interceptor.png)

将需要暴露的表移到右边列表中:
![](/img/right-table.png)

点击"GENERATE"按钮开始生成,如果成功返回:
![](/img/status-ok.png)
按提示重启服务

###五:重启服务后OData接口发布成功了,请求入口:[http://localhost:8080/ODataV2GenerateSample/OdataServlet.cn](http://localhost:8080/ODataV2GenerateSample/OdataServlet.cn)###
![](/img/odata-servlet.png)




