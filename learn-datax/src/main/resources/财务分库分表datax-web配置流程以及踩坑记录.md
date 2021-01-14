# 财务分库分表datax-web配置流程以及踩坑记录

## 环境准备


### 基础软件安装


- MySQL (5.5+) 必选，对应客户端可以选装, Linux服务上若安装mysql的客户端可以通过部署脚本快速初始化数据库
- JDK (1.8.0_xxx) 必选
- Maven (3.6.1+) 必选
- DataX 必选
- Python (2.x) (支持Python3需要修改替换datax/bin下面的三个python文件，替换文件在doc/datax-web/datax-python3下) 必选，主要用于调度执行底层DataX的启动脚本，默认的方式是以Java子进程方式执行DataX，用户可以选择以Python方式来做自定义的改造



### DataX安装


直接下载DataX工具包：[DataX下载地址](http://datax-opensource.oss-cn-hangzhou.aliyuncs.com/datax.tar.gz)






下载后解压至本地某个目录，进入bin目录，即可运行同步作业


```shell
$ cd  {YOUR_DATAX_HOME}/bin
$ python datax.py {YOUR_JOB.json}
```


自检脚本：    python {YOUR_DATAX_HOME}/bin/datax.py {YOUR_DATAX_HOME}/job/job.json


### DataX Web安装包准备




[datax-web-2.1.2.tar.gz](https://xcz.yuque.com/attachments/yuque/0/2020/gz/2670557/1604545986507-76154280-0b97-4c97-8e5b-7c4f84181a24.gz?_lake_card=%7B%22uid%22%3A%221604545793177-0%22%2C%22src%22%3A%22https%3A%2F%2Fxcz.yuque.com%2Fattachments%2Fyuque%2F0%2F2020%2Fgz%2F2670557%2F1604545986507-76154280-0b97-4c97-8e5b-7c4f84181a24.gz%22%2C%22name%22%3A%22datax-web-2.1.2.tar.gz%22%2C%22size%22%3A217566120%2C%22type%22%3A%22application%2Fx-gzip%22%2C%22ext%22%3A%22gz%22%2C%22progress%22%3A%7B%22percent%22%3A99%7D%2C%22status%22%3A%22done%22%2C%22percent%22%3A0%2C%22id%22%3A%22ggRbp%22%2C%22card%22%3A%22file%22%7D)


## 部署


#### 1）解压安装包


在选定的安装目录，解压安装包


```shell
tar -zxvf datax-web-{VERSION}.tar.gz
```


#### 2）执行一键安装脚本


进入解压后的目录，找到bin目录下面的install.sh文件，如果选择交互式的安装，则直接执行


```shell
./bin/install.sh
```


在交互模式下，对各个模块的package压缩包的解压以及configure配置脚本的调用，都会请求用户确认,可根据提示查看是否安装成功，如果没有安装成功，可以重复尝试； 如果不想使用交互模式，跳过确认过程，则执行以下命令安装


```shell
./bin/install.sh --force
```


#### 3）数据库初始化


如果你的服务上安装有mysql命令，在执行安装脚本的过程中则会出现以下提醒：


```properties
Scan out mysql command, so begin to initalize the database
Do you want to initalize database with sql: [{INSTALL_PATH}/bin/db/datax-web.sql]? (Y/N)y
Please input the db host(default: 127.0.0.1): 
Please input the db port(default: 3306): 
Please input the db username(default: root): 
Please input the db password(default: ): 
Please input the db name(default: exchangis)
```


按照提示输入数据库地址，端口号，用户名，密码以及数据库名称，大部分情况下即可快速完成初始化。 如果服务上并没有安装mysql命令，则可以取用目录下/bin/db/datax-web.sql脚本去手动执行，完成后修改相关配置文件


```properties
vi ./modules/datax-admin/conf/bootstrap.properties
#Database
#DB_HOST=
#DB_PORT=
#DB_USERNAME=
#DB_PASSWORD=
#DB_DATABASE=
```


按照具体情况配置对应的值即可。


#### 4) 配置


安装完成之后，


在项目目录： /modules/datax-admin/bin/env.properties 配置邮件服务(可跳过)


```properties
MAIL_USERNAME=""
MAIL_PASSWORD=""
```


此文件中包括一些默认配置参数，例如：server.port，具体请查看文件。


在项目目录下/modules/datax-execute/bin/env.properties 指定PYTHON_PATH的路径


```shell
vi ./modules/{module_name}/bin/env.properties

### 执行datax的python脚本地址
PYTHON_PATH=

### 保持和datax-admin服务的端口一致；默认是9527，如果没改datax-admin的端口，可以忽略
DATAX_ADMIN_PORT=
```


此文件中包括一些默认配置参数，例如：executor.port,json.path,data.path等，具体请查看文件。


#### 5）启动服务


##### - 一键启动所有服务


```shell
./bin/start-all.sh
```


中途可能发生部分模块启动失败或者卡住，可以退出重复执行，如果需要改变某一模块服务端口号，则：


```shell
vi ./modules/{module_name}/bin/env.properties
```


找到SERVER_PORT配置项，改变它的值即可。 当然也可以单一地启动某一模块服务：


```shell
./bin/start.sh -m {module_name}
```


##### - 一键取消所有服务


```shell
./bin/stop-all.sh
```


当然也可以单一地停止某一模块服务：


```shell
./bin/stop.sh -m {module_name}
```


#### 6）查看服务


在Linux环境下使用JPS命令，查看是否出现DataXAdminApplication和DataXExecutorApplication进程，如果存在这表示项目运行成功


> 如果项目启动失败，请检查启动日志：modules/datax-admin/bin/console.out或者modules/datax-executor/bin/console.out



#### 7）运行


部署完成后，在浏览器中输入 [http://ip:port/index.html](http://ip:port/index.html) 就可以访问对应的主界面（ip为datax-admin部署所在服务器ip,port为为datax-admin 指定的运行端口）


输入用户名 admin 密码 123456 就可以直接访问系统


#### 8) 运行日志


部署完成之后，在modules/对应的项目/data/applogs下(用户也可以自己指定日志，修改application.yml 中的logpath地址即可)，用户可以根据此日志跟踪项目实际启动情况


如果执行器启动比admin快，执行器会连接失败，日志报"拒绝连接"的错误，一般是先启动admin,再启动executor,30秒之后会重连，如果成功请忽略这个异常。


## 配置datax-web


#### 任务配置


登录http://ip:port/index.html 访问对应的主界面 port默认9527


[http://dataxweb-test.f6yc.org/index.html](http://dataxweb-test.f6yc.org/index.html)


![1604544459952.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546067262-d62319e7-d2c4-461c-99d6-40e89cb70cf1.png#align=left&display=inline&height=494&margin=%5Bobject%20Object%5D&name=1604544459952.png&originHeight=494&originWidth=970&size=9922&status=done&style=none&width=970)
进入任务管理




![1604544592751.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546075575-40958b76-0c50-4fbd-afbb-f5c409fd9d67.png#align=left&display=inline&height=534&margin=%5Bobject%20Object%5D&name=1604544592751.png&originHeight=534&originWidth=1382&size=82526&status=done&style=none&width=1382)
选择添加
![1604544633206.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546083789-5de626ba-1dd3-4c6f-962e-b93511629e05.png#align=left&display=inline&height=834&margin=%5Bobject%20Object%5D&name=1604544633206.png&originHeight=834&originWidth=1189&size=67449&status=done&style=none&width=1189)


配置对应的  调度执行任务 以及 datax 对应的json


#### 配置样例


配置一个从Mysql数据库同步抽取数据到本地的作业:


```json
{
    "job": {
        "setting": {
            "speed": {
                 "channel": 3
            },
            "errorLimit": {
                "record": 0,
                "percentage": 0.02
            }
        },
        "content": [
            {
                "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "root",
                        "password": "root",
                        "column": [
                            "id",
                            "name"
                        ],
                        "splitPk": "db_id",
                        "connection": [
                            {
                                "table": [
                                    "table"
                                ],
                                "jdbcUrl": [
     "jdbc:mysql://127.0.0.1:3306/database"
                                ]
                            }
                        ]
                    }
                },
               "writer": {
                    "name": "streamwriter",
                    "parameter": {
                        "print":true
                    }
                }
            }
        ]
    }
}
```


配置使用一份从内存产生到 Mysql 导入的数据


```json
{
    "job": {
        "setting": {
            "speed": {
                "channel": 1
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "streamreader",
                    "parameter": {
                        "column" : [
                            {
                                "value": "DataX",
                                "type": "string"
                            },
                            {
                                "value": 19880808,
                                "type": "long"
                            },
                            {
                                "value": "1988-08-08 08:08:08",
                                "type": "date"
                            },
                            {
                                "value": true,
                                "type": "bool"
                            },
                            {
                                "value": "test",
                                "type": "bytes"
                            }
                        ],
                        "sliceRecordCount": 1000
                    }
                },
                "writer": {
                    "name": "mysqlwriter",
                    "parameter": {
                        "writeMode": "insert",
                        "username": "root",
                        "password": "root",
                        "column": [
                            "id",
                            "name"
                        ],
                        "session": [
                        	"set session sql_mode='ANSI'"
                        ],
                        "preSql": [
                            "delete from test"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": "jdbc:mysql://127.0.0.1:3306/datax?useUnicode=true&characterEncoding=gbk",
                                "table": [
                                    "test"
                                ]
                            }
                        ]
                    }
                }
            }
        ]
    }
}
```


#### 执行任务


![1604545087476.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546102072-fe5e1ad7-8b32-4508-8f91-a13ab2a20e6f.png#align=left&display=inline&height=268&margin=%5Bobject%20Object%5D&name=1604545087476.png&originHeight=268&originWidth=1440&size=52976&status=done&style=none&width=1440)
#### 查看日志


![1604545105832.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546122128-e3bc36f9-65cc-4828-900c-622ef17b4529.png#align=left&display=inline&height=238&margin=%5Bobject%20Object%5D&name=1604545105832.png&originHeight=238&originWidth=641&size=19513&status=done&style=none&width=641)
进入日志查看，查看输出datax日志


![1604545276645.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1604546131795-3daf31f4-a2c4-4de4-b270-4f7ab15fbfe4.png#align=left&display=inline&height=628&margin=%5Bobject%20Object%5D&name=1604545276645.png&originHeight=628&originWidth=1774&size=95409&status=done&style=none&width=1774)
## 踩坑记录


#### **column** 列配置问题


##### 问题描述


配置的时候由于 数据库 采用select * 中 有部分字段 如 desc字段 为


mysql的关键字,导致datax采集报错：sql语法错误


##### 问题分析
怀疑 sql语法在于采集和执行 两块有问题 。查看配置发现 `column": ["*"]`
可能是由于某些字段查询异常导致，对比数据库建表语句发现没有问题。
将 "column": ["id","name","age"] 设置为部分字段 同步正常。说明可能某些字段
导致了sql语法异常。


##### 修复方案


将 column": ["*"]修复为 按照字段展现，desc可以添加转义字符
> 注意：1、强烈不推荐这样配置，因为当你目的表字段个数、类型等有改动时，你的任务可能运行不正确或者失败
>           2、column 不能配置任何常量值



#### 影响其他应用慢查询


修复方案 
可以考虑关闭log file 记录 降低io资源的占用 
如增加 查询session级别查询记录的阀值 
jdbcUrl  增加参数  sessionVariables=long_query_time=60

另外对共享资源做一定的隔离 如迁移RDS

![image.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1607483814220-0093a798-ac66-4d87-8383-e6b0d3cfa1b6.png#align=left&display=inline&height=166&margin=%5Bobject%20Object%5D&name=image.png&originHeight=331&originWidth=913&size=24957&status=done&style=none&width=456.5)
支持session ，global级别配置

If a query takes longer than this many seconds, the server increments the [`Slow_queries`](https://dev.mysql.com/doc/refman/5.7/en/server-status-variables.html#statvar_Slow_queries) status variable. If the slow query log is enabled, the query is logged to the slow query log file. This value is measured in real time, not CPU time, so a query that is under the threshold on a lightly loaded system might be above the threshold on a heavily loaded one

线上问题由于超过阀值导致增加大量日志，最终导致其他应用连接mysql共享资源被耗尽，因此在共享资源下应该合理设置该阀值，轻量负载可以调低该阀值，重量负载可以适当调高


![image.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1607482639468-feb1221f-571d-40e5-9935-239199f88f2f.png#align=left&display=inline&height=534&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1068&originWidth=1914&size=283892&status=done&style=none&width=957)
参考 mysqld参数设置[https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-session.html](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-session.html)
