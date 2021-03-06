# SPRING-CLOUD-CONFIG-SERVER

**Spring-Cloud-Config-Server** is creating as config server to store configuration of all config client services like **limit-service**.

### Setup Spring-Cloud-Config-Server

### > **Added Dependency**

* Spring-loud-config-server
* spring-boot-devtools

### > **Enable Cloud Server Annotation**

first of to do that is add annotation **@EnableConfigServer** in SpringBootApplication class which is given below : 

	@SpringBootApplication
	@EnableConfigServer
	public class SpringCloudConfigServerApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(SpringCloudConfigServerApplication.class, args);
		}
	}


### > application.yml

In this project I am using application.yml but you can use either application.yml or application.properties as your comfort.

You have to define application name as spring-cloud-config-server and set the port as your choice.

	spring:
	    application:
	        name: spring-cloud-config-server
	    cloud:
	    	config:
	    		server:
	    			git:	
	    				uri: https://github.com/vikashvs36/spring-cloud-config-store
						# username: VikashSingh
						# password: *******
	server:
	    port: 8888	      

**Note :** By default  port of **Spring-cloud-config-server** service has **8888**. You can use any port as your wish.

### > **Add GIT Repository**

You can deploy the services as your wish like AWS server and any server but the Real configuration will be store on GitHub.

Create a Git repository and store the files that we want to be able to configure like limits-service and all. We will try to access them from the spring-cloud-config-server.

There are two types of GIT repository which you can use is given below : 
 
**Local git :** 

You can use local git like creating a folder and git initilize. After only commit the files we can use it and access it by config server. No need to push the commits in git server.

**Server git :** 

It can be use by creating a git repo on git server and after commit the files, can be used by config server.

In spring-cloud-config-server application, properties or yml file needs to configure git server URI as given below :  

	spring:
	    application:
	        name: spring-cloud-config-server
	    cloud:
	    	config:
	    		server:
	    			git:	
	    				uri: https://github.com/vikashvs36/spring-cloud-config-store
						# username: VikashSingh
						# password: *******

In this configuration file, URI **https://github.com/vikashvs36/spring-cloud-config-store** is a repository which is created on git server and URI needs to mentioned here as **spring.cloud.config.server.git.uri** to clone the **spring-cloud-config-store** repository at the time of startup or run **spring-cloud-config-server** service.

Username and password can be use in the case of private repository

All Configuration profile of all services will be mentioned on **spring-cloud-config-store** repository. It can be get it from [spring-cloud-config-store Repository](https://github.com/vikashvs36/spring-cloud-config-store)

### > Access the configuration file in the **spring-cloud-config-server** service.

After run the **Spring-cloud-config-server** sevice, It can be access by the **{config_client_service_name}/{profile_name}** client service name and profile name. If there is not present any profile then default profile will be by default call. 

**Example :**

	# Default profile
	http://localhost:8888/limit-service/default 
	# Devlopment profile
	http://localhost:8888/limit-service/dev
	# QA profile
	http://localhost:8888/limit-service/qa
	# producton profile
	http://localhost:8888/limit-service/prod

**Note :** If any configuration key missing in the profile files then bydefault it will get the key form default profile of their corresponding services.

### > Convention of the configuration file name.

The configuration file name of will be Either **{serviceName}-{profilename}.yml** or **{serviceName}/{profilename}.yml** like **limit-service-dev.properties**. where the limit-service is a service name and dev is profile. We can create multiple and different type of configuration profiles.

	# For the limit-service default profile is created like 
	limit-service.properties
	# For the limit-service dev profile is created like
	limit-service-dev.properties
	# For the limit-service QA profile is created like
	limit-service-qa.properties
	# For the limit-service Prod profile is created like
	limit-service-prod.properties
 
You can use here either yml or properties file.

### > Create configuration files of multiple Services.

Create multiple profile configuration files for every service for separated with folder wise which is given in below picture :

![](img/configurationFile.PNG)

It's no need to change any config client when we separated with folder wise. Only one change required which is mention in **config server** appication .yml, is given below : 

	spring:
	  cloud:
	    config:
	      server:
	        git:
	          uri: https://github.com/vikashvs36/spring-cloud-config-store
	          search-paths: "*service"

**search-paths** is used to search path respected service so need to define common path name.

### > Get Exact file from config Server

If we just want to see exact configuration file keys and values details of any services by the web, you can get from like this :

	// Get in properties format
	http://localhost:8888/limit-service.properties
	
	// Get in YML format
	http://localhost:8888/limit-service.yml
	
	// Get in JSON format
	http://localhost:8888/limit-service.json
	
**Note :** No metter you have mention in properties file or yml file, you can get in any format.


### > Get Configuration file according to tag/label from config Server 

As you know, All configuration files are commited on **spring-cloud-config-store** repository. If you want to get the files according to hash commits then you need to access by **{config_client_Service_name}/{profile}/{tag}** like this :

	http://localhost:8888/limit-service/dev/54855a56e4a7e5fe21a95cec176c69e9f3c31d09
	
In limit-service dev/qa/prod configuration file max and min key is there but in default profile had maximum and minimum key was there. so I have committed it after updated like maximum to max and so on. But I want to access it version wise. I mean, I want to access the previous committed files. that means maximum and minimum key wants to use. so I will access it by tag or label. 

**Note :** The Problem is this, those we configuration are used like this, this is not used in real world. because In the real world I don't call this rest end points to get the configuration. This has to happened to autometically by the our services. So consume this service we need to create config client like limit-service who we created before. Let's see how to consume all **Spring-cloud-config-server** by **limit-services**. let's go with the **Spring-Cloud-Config-Client** : [Limit-Service](https://github.com/vikashvs36/microservices-tutoria/tree/master/limit-service)  