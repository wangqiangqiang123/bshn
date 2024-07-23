# 图书管理系统
### 基于jdk1.8+springboot+mybatisplus+redis+jwt+logbak(maven方式构建)
## 项目介绍
本项目属于纯后端服务，是基于jdk1.8+springboot+mybatisplus，缓存用的是redis，使用jwt进行token生成验证，使用的数据库为h2database，版本：2.0.206,同时也提供了mysql配置,使用logbak日志配置，支持多环境配置文件。
## 系统功能
该系统实现读者和图书管理员注册登陆，图书的增删改查，用户的增删改查，借还图书，密码修改等功能。
## 设计说明
通过用户、角色、权限的关联关系进行接口访问权限控制，用户注册时可根据用户类型不同分配不用的访问权限，初始化的超管具有全部接口访问权限，图书管理员具有图书的增删改查，用户信息修改，密码修改，借还图书权限
读者具有借还图书，密码修改，用户信息修改权限，可根据角色权限表，用户角色表对用户的访问权限进行灵活配置，通过jwt将用户相关信息生成token，通过进行reids缓存，在过滤器进行访问权限控制，通过logback.xml配置生成不同级别日志。
## 如何使用
1.需要将项目下sql目录下对应的数据库ddl和dml脚本执行完毕</br>
2.将yml配置文件中的数据库和redis配置修改成自己的配置</br>
3.启动redis</br>
4.进到\booksmanager下在地址栏输入cmd回车,然后执行mvn install进行打包(前提是已经配置了maven环境变量),打完包后会生成target目录，进到\target下进行java -jar booksmanager-0.0.1-SNAPSHOT.jar 即可启动项目</br>
## 如何访问
1.swagger地址：
http://localhost:8086/booksmanager/swagger-ui.html#/</br>
2.注册和登录接口不需要token认证，其他接口需要从注册或登录接口返回的token进行认证。</br>
## 本项目截图
![image](https://github.com/wangqiangqiang123/bshn/blob/master/bnp1.png)</br>
![image](https://github.com/wangqiangqiang123/bshn/blob/master/bnp2.png)</br>
![image](https://github.com/wangqiangqiang123/bshn/blob/master/bnp3.png)</br>
![image](https://github.com/wangqiangqiang123/bshn/blob/master/bnp4.png)</br>
![image](https://github.com/wangqiangqiang123/bshn/blob/master/bnp5.png)</br>


