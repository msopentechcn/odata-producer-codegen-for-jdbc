#使用olingo-odata-jpa-entitygen.jar快速生成jap Entity#

简介:olingo-odata-jpa-entitygen 是自动生成jpaEntity代码的工具.使用依赖环境jpa-api/jpa-core/sql-driver.当我们使用olingo2-odat2-jpa2来实现odata2暴露的时候,需要手动创建和数据库表对应的Entity,如果数据库中有上百张表,那我们使用olingo-odata-jpa-entitygen来实现自动创建Entity就很方便了.

##使用步骤:##
###一:创建一个具有olingo2-odata2-jpa2环境的应用###

###二:将olingo-odata-jpa-entitygen.jar架包考到工程的classPath中.###

###三:配置tomcat启动时的CLASSPATH=H:\apache-tomcat-7.0.52\webapps\TokyoSubway\WEB-INF\lib\*.jar(因为应用是在运行时编译Entity       的,所以CLASSPATH是依赖应用的lib,TokyoSubway是工程名)###


### 四:配置web.xml###
       <servlet>
             <servlet-name>EntityServlet</servlet-name>
             <servlet-class>olingo.odata.jpa.entitygen.servlet.EntityServlet</servlet-class>
             <init-param>
                  <param-name>persistence.UnitName</param-name>
                  <param-value>odata2_jpa2</param-value>
             </init-param>
	     <init-param>
                  <param-name>persistence.ClassPath</param-name>
                  <param-value>persistence.xml</param-value>
             </init-param>
       </servlet>

       <servlet-mapping>
             <servlet-name>EntityServlet</servlet-name>
             <url-pattern>/EntityServlet/*</url-pattern>
       </servlet-mapping>



       EntityServlet是自动生成Entity的入口.
       persistence.UnitName参数是persistence.xml配置中使用的UnitName.
       persistence.ClassPath参数是persistence.xml的路径及名称,默认是保存在META-INF/persistence.xml(如果是默认的路径和名称的话        ,可以不用指定该参数).




###五:persistence.xml中persistence-unit的配置###
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


##使用sample快速搭建自己的olingo2-odata2-jpa2环境##
#####步骤:#####
#####1,将应用TokyoSubway导入您的IDE工具中#####
#####2,修改persistence.xml的数据库连接#####
#####3,配置tomcat启动是的CLASSPATH=H:\apache-tomcat-7.0.52\webapps\TokyoSubway\WEB-INF\lib\*.jar;  (指向应用部署时的lib所有jar文件)#####
#####4,startup server#####
#####5,http://localhost:8080/TokyoSubway/EntityServlet   执行自动生成Entity程序入口#####
#####6,等正确返回:#####
        STATE:OK
        Application[/TokyoSubway] auto create entity is successed.Please manually restart the application!
#####7,restart server#####
#####8,http://localhost:8080/TokyoSubway/OdataServlet.cn    这样数据库中的所有表的数据都可以暴露出来了.#####



####如果您只想暴露数据库中的部分数据####
    实现步骤:
    1,在您的数据库中创建一个名为Oling_Odata_JPA_ExposeTable的表
    
      drop table if exists Oling_Odata_JPA_ExposeTable;
      create table Oling_Odata_JPA_ExposeTable(
        ExposeTableName varchar(50) primary key
      );


这样olingo-odata-jpa-entitygen就能识别并生成ExposeTableName对应的Entity
如果数据库中没有Oling_Odata_JPA_ExposeTable或者列长度为0的olingo-odata-jpa-entitygen都默认是暴露全部数据库的.


