# datax 使用介绍

github [https://github.com/alibaba/DataX](https://github.com/alibaba/DataX)


### DataX


DataX 是阿里巴巴集团内被广泛使用的离线数据同步工具/平台，实现包括 MySQL、Oracle、SqlServer、Postgre、HDFS、Hive、ADS、HBase、TableStore(OTS)、MaxCompute(ODPS)、DRDS 等各种异构数据源之间高效的数据同步功能。


### 特性


DataX本身作为数据同步框架，将不同数据源的同步抽象为从源头数据源读取数据的Reader插件，以及向目标端写入数据的Writer插件，理论上DataX框架可以支持任意数据源类型的数据同步工作。同时DataX插件体系作为一套生态系统, 每接入一套新数据源该新加入的数据源即可实现和现有的数据源互通。


### 支持数据源
| 类型 | 数据源 | Reader(读) | Writer(写) | 文档 |
| --- | --- | --- | --- | --- |
| RDBMS 关系型数据库 | MySQL | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/mysqlreader/doc/mysqlreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/mysqlwriter/doc/mysqlwriter.md) |
|  | Oracle | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/oraclereader/doc/oraclereader.md) 、[写](https://github.com/alibaba/DataX/blob/master/oraclewriter/doc/oraclewriter.md) |
|  | SQLServer | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/sqlserverreader/doc/sqlserverreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/sqlserverwriter/doc/sqlserverwriter.md) |
|  | PostgreSQL | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/postgresqlreader/doc/postgresqlreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/postgresqlwriter/doc/postgresqlwriter.md) |
|  | DRDS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/drdsreader/doc/drdsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/drdswriter/doc/drdswriter.md) |
|  | 通用RDBMS(支持所有关系型数据库) | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/rdbmsreader/doc/rdbmsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/rdbmswriter/doc/rdbmswriter.md) |
| 阿里云数仓数据存储 | ODPS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/odpsreader/doc/odpsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/odpswriter/doc/odpswriter.md) |
|  | ADS |  | √ | [写](https://github.com/alibaba/DataX/blob/master/adswriter/doc/adswriter.md) |
|  | OSS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/ossreader/doc/ossreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/osswriter/doc/osswriter.md) |
|  | OCS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/ocsreader/doc/ocsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/ocswriter/doc/ocswriter.md) |
| NoSQL数据存储 | OTS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/otsreader/doc/otsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/otswriter/doc/otswriter.md) |
|  | Hbase0.94 | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hbase094xreader/doc/hbase094xreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hbase094xwriter/doc/hbase094xwriter.md) |
|  | Hbase1.1 | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hbase11xreader/doc/hbase11xreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hbase11xwriter/doc/hbase11xwriter.md) |
|  | Phoenix4.x | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hbase11xsqlreader/doc/hbase11xsqlreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hbase11xsqlwriter/doc/hbase11xsqlwriter.md) |
|  | Phoenix5.x | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hbase20xsqlreader/doc/hbase20xsqlreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hbase20xsqlwriter/doc/hbase20xsqlwriter.md) |
|  | MongoDB | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/mongodbreader/doc/mongodbreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/mongodbwriter/doc/mongodbwriter.md) |
|  | Hive | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hdfsreader/doc/hdfsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hdfswriter/doc/hdfswriter.md) |
|  | Cassandra | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/cassandrareader/doc/cassandrareader.md) 、[写](https://github.com/alibaba/DataX/blob/master/cassandrawriter/doc/cassandrawriter.md) |
| 无结构化数据存储 | TxtFile | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/txtfilereader/doc/txtfilereader.md) 、[写](https://github.com/alibaba/DataX/blob/master/txtfilewriter/doc/txtfilewriter.md) |
|  | FTP | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/ftpreader/doc/ftpreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/ftpwriter/doc/ftpwriter.md) |
|  | HDFS | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/hdfsreader/doc/hdfsreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/hdfswriter/doc/hdfswriter.md) |
|  | Elasticsearch |  | √ | [写](https://github.com/alibaba/DataX/blob/master/elasticsearchwriter/doc/elasticsearchwriter.md) |
| 时间序列数据库 | OpenTSDB | √ |  | [读](https://github.com/alibaba/DataX/blob/master/opentsdbreader/doc/opentsdbreader.md) |
|  | TSDB | √ | √ | [读](https://github.com/alibaba/DataX/blob/master/tsdbreader/doc/tsdbreader.md) 、[写](https://github.com/alibaba/DataX/blob/master/tsdbwriter/doc/tsdbhttpwriter.md) |



## Mysql数据同步


### reader 实现原理


MysqlReader通过JDBC连接器连接到远程的Mysql数据库，并根据用户配置的信息生成查询SELECT SQL语句，然后发送到远程Mysql数据库，并将该SQL执行返回结果使用DataX自定义的数据类型拼装为抽象的数据集，并传递给下游Writer处理。


对于用户配置Table、Column、Where的信息，MysqlReader将其拼接为SQL语句发送到Mysql数据库；对于用户配置querySql信息，MysqlReader直接将其发送到Mysql数据库。


### mysql reader配置


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


### 参数介绍


- **jdbcUrl**
   - 描述：描述的是到对端数据库的JDBC连接信息，使用JSON的数组描述，并支持一个库填写多个连接地址。之所以使用JSON数组描述连接信息，是因为阿里集团内部支持多个IP探测，如果配置了多个，MysqlReader可以依次探测ip的可连接性，直到选择一个合法的IP。如果全部连接失败，MysqlReader报错。 注意，jdbcUrl必须包含在connection配置单元中。对于阿里集团外部使用情况，JSON数组填写一个JDBC连接即可。
jdbcUrl按照Mysql官方规范，并可以填写连接附件控制信息。具体请参看[Mysql官方文档](http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html)。
   - 必选：是
   - 默认值：无
- **username**
   - 描述：数据源的用户名
   - 必选：是
   - 默认值：无
- **password**
   - 描述：数据源指定用户名的密码
   - 必选：是
   - 默认值：无
- **table**
   - 描述：所选取的需要同步的表。使用JSON的数组描述，因此支持多张表同时抽取。当配置为多张表时，用户自己需保证多张表是同一schema结构，MysqlReader不予检查表是否同一逻辑表。注意，table必须包含在connection配置单元中。
   - 必选：是
   - 默认值：无
- **column**
   - 描述：所配置的表中需要同步的列名集合，使用JSON的数组描述字段信息。用户使用_代表默认使用所有列配置，例如['_']。
支持列裁剪，即列可以挑选部分列进行导出。
支持列换序，即列可以不按照表schema信息进行导出。
支持常量配置，用户需要按照Mysql SQL语法格式: ["id", "`table`", "1", "'bazhen.csy'", "null", "to_char(a + 1)", "2.3" , "true"] id为普通列名，`table`为包含保留在的列名，1为整形数字常量，'bazhen.csy'为字符串常量，null为空指针，to_char(a + 1)为表达式，2.3为浮点数，true为布尔值。
   - 必选：是
   - 默认值：无
- **splitPk**
   - 描述：MysqlReader进行数据抽取时，如果指定splitPk，表示用户希望使用splitPk代表的字段进行数据分片，DataX因此会启动并发任务进行数据同步，这样可以大大提供数据同步的效能。
推荐splitPk用户使用表主键，因为表主键通常情况下比较均匀，因此切分出来的分片也不容易出现数据热点。


目前splitPk仅支持整形数据切分，`不支持浮点、字符串、日期等其他类型`。如果用户指定其他非支持类型，MysqlReader将报错！
```
如果splitPk不填写，包括不提供splitPk或者splitPk值为空，DataX视作使用单通道同步该表数据。
```

   - 必选：否
   - 默认值：空
- **where**
   - 描述：筛选条件，MysqlReader根据指定的column、table、where条件拼接SQL，并根据这个SQL进行数据抽取。在实际业务场景中，往往会选择当天的数据进行同步，可以将where条件指定为gmt_create > $bizdate 。注意：不可以将where条件指定为limit 10，limit不是SQL的合法where子句。```
where条件可以有效地进行业务增量同步。如果不填写where语句，包括不提供where的key或者value，DataX均视作同步全量数据。
```

   - 必选：否
   - 默认值：无
- **querySql**
   - 描述：在有些业务场景下，where这一配置项不足以描述所筛选的条件，用户可以通过该配置型来自定义筛选SQL。当用户配置了这一项之后，DataX系统就会忽略table，column这些配置型，直接使用这个配置项的内容对数据进行筛选，例如需要进行多表join后同步数据，使用select a,b from table_a join table_b on table_a.id = table_b.id


`当用户配置querySql时，MysqlReader直接忽略table、column、where条件的配置`，querySql优先级大于table、column、where选项。

   - 必选：否
   - 默认值：无



### writer 实现原理


MysqlWriter 插件实现了写入数据到 Mysql 主库的目的表的功能。在底层实现上， MysqlWriter 通过 JDBC 连接远程 Mysql 数据库，并执行相应的 insert into ... 或者 ( replace into ...) 的 sql 语句将数据写入 Mysql，内部会分批次提交入库，需要数据库本身采用 innodb 引擎。


MysqlWriter 面向ETL开发工程师，他们使用 MysqlWriter 从数仓导入数据到 Mysql。同时 MysqlWriter 亦可以作为数据迁移工具为DBA等用户提供服务。


> 注意：目的表所在数据库必须是主库才能写入数据；整个任务至少需要具备 insert/replace into...的权限



### Mysql Writer 配置


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


### 参数介绍


### 参数说明


- **jdbcUrl**
   - 描述：目的数据库的 JDBC 连接信息。作业运行时，DataX 会在你提供的 jdbcUrl 后面追加如下属性：yearIsDateType=false&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true```
     注意：1、在一个数据库上只能配置一个 jdbcUrl 值。这与 MysqlReader 支持多个备库探测不同，因为此处不支持同一个数据库存在多个主库的情况(双主导入数据情况)
          2、jdbcUrl按照Mysql官方规范，并可以填写连接附加控制信息，比如想指定连接编码为 gbk ，则在 jdbcUrl 后面追加属性 useUnicode=true&characterEncoding=gbk。具体请参看 Mysql官方文档或者咨询对应 DBA。
```

   - 必选：是
   - 默认值：无
- **username**
   - 描述：目的数据库的用户名
   - 必选：是
   - 默认值：无
- **password**
   - 描述：目的数据库的密码
   - 必选：是
   - 默认值：无
- **table**
   - 描述：目的表的表名称。支持写入一个或者多个表。当配置为多张表时，必须确保所有表结构保持一致。```
     注意：table 和 jdbcUrl 必须包含在 connection 配置单元中
```

   - 必选：是
   - 默认值：无
- **column**
   - 描述：目的表需要写入数据的字段,字段之间用英文逗号分隔。例如: "column": ["id","name","age"]。如果要依次写入全部列，使用`*`表示, 例如: `"column": ["*"]`。```
  **column配置项必须指定，不能留空！**
     注意：1、我们强烈不推荐你这样配置，因为当你目的表字段个数、类型等有改动时，你的任务可能运行不正确或者失败
          2、 column 不能配置任何常量值
```

   - 必选：是
   - 默认值：否
- **session**
   - 描述: DataX在获取Mysql连接时，执行session指定的SQL语句，修改当前connection session属性
   - 必须: 否
   - 默认值: 空
- **preSql**
   - 描述：写入数据到目的表前，会先执行这里的标准语句。如果 Sql 中有你需要操作到的表名称，请使用 `@table` 表示，这样在实际执行 Sql 语句时，会对变量按照实际表名称进行替换。比如你的任务是要写入到目的端的100个同构分表(表名称为:datax_00,datax01, ... datax_98,datax_99)，并且你希望导入数据前，先对表中数据进行删除操作，那么你可以这样配置：`"preSql":["delete from 表名"]`，效果是：在执行到每个表写入数据前，会先执行对应的 delete from 对应表名称
   - 必选：否
   - 默认值：无
- **postSql**
   - 描述：写入数据到目的表后，会执行这里的标准语句。（原理同 preSql ）
   - 必选：否
   - 默认值：无
- **writeMode**
   - 描述：控制写入数据到目标表采用 `insert into` 或者 `replace into` 或者 `ON DUPLICATE KEY UPDATE` 语句
   - 必选：是
   - 所有选项：insert/replace/update
   - 默认值：insert
- **batchSize**
   - 描述：一次性批量提交的记录数大小，该值可以极大减少DataX与Mysql的网络交互次数，并提升整体吞吐量。但是该值设置过大可能会造成DataX运行进程OOM情况。
   - 必选：否
   - 默认值：1024



### 常用问题


#### 数据库编码问题


包括指定编码到库、表、字段级别，甚至可以均不同编码。优先级从高到低为字段、表、库、实例。我们不推荐数据库用户设置如此混乱的编码，最好在库级别就统一到UTF-8。


#### 增量数据同步


MysqlReader使用JDBC SELECT语句完成数据抽取工作，因此可以使用SELECT...WHERE...进行增量数据抽取


配合otter使用


#### 无图形化界面


配合datax-web 使用


## 我们的实践


### 分库分表


#### 全量数据同步


财务数据收入,支出数据同步到分表


目标按照group_id进行拆分同步:tf_income=>tf_income_shard_[0,31]


如何通过datax进行数据同步?


如何编写对应的reader和writer?


```
{
  "job": {
    "setting": {
      "speed": {
        "channel": 5
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "***",
            "password": "***",
            "splitPk": "pk_id",
            "connection": [
              {
                "querySql": [
                  "SELECT * FROM tf_outlay where MOD ((id_own_group div 100), 32)=0;",
                  "SELECT * FROM tf_outlay where MOD ((id_own_group div 100), 32)=1;",
                  "SELECT * FROM tf_outlay where MOD ((id_own_group div 100), 32)=2;",
                  "SELECT * FROM tf_outlay where MOD ((id_own_group div 100), 32)=3;",
                  ...
                  "SELECT * FROM tf_outlay where MOD ((id_own_group div 100), 32)=31;"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://db-f6dms-master01-test.f6car.org:3306/f6dms?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "writeMode": "insert",
            "username": "****",
            "password": ****,
            "column": [
              "*"
            ],
            "connection": [
              {
                "jdbcUrl": "jdbc:mysql://db-f6dms-master01-test.f6car.org:3306/f6financial?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true",
                "table": [
                  "tf_outlay_shard_0",
                  "tf_outlay_shard_1",
                  "tf_outlay_shard_2",
                  "tf_outlay_shard_3"         
                  "tf_outlay_shard_31"
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


#### 增量数据同步


datax配合otter使用全量与增量同步数据


先开启otter同步执行基于binlog的同步，再开启datax执行全量执行


datax writer使用 `writeMode` 使用 `insert` （当主键/唯一性索引冲突时会写不进去冲突的行）


### 踩坑记录


#### **column** 列配置问题


##### 问题描述


配置的时候由于 数据库 采用select * 中 有部分字段 如 desc字段 为


mysql的关键字,导致datax采集报错：sql语法错误


##### 问题分析


怀疑 sql语法在于采集和执行 两块有问题 。查看配置发现 `column": ["*"]`


可能是由于某些字段查询异常导致，对比数据库建表语句发现没有问题。


将 "column": ["id","name","`desc`"] 设置为部分字段 同步正常。说明可能某些字段


导致了sql语法异常。


#### 数据迁移占用io资源
记录log file逻辑，当long_query_time 超过一定阀值,运维方会记录 mysql 中操作日志中的慢日志，慢日志内容包含
select 中的慢查询 ，insert update 的慢sql
对于insert 语句 会执行 select 查询到的结果集进行 拼接 会产生大量的 insert 日志，导致io资源占用，可能引发其他问题。
  
修复方案 
1.执行select 查询使用salve节点，降低对主库的查询压力 
2.可以考虑禁用log file 记录 降低io资源的占用 
如基于session级别调整日志记录的阀值 
jdbcUrl  增加参数  sessionVariables=long_query_time=60
writer中session属性设置 set long_query_time=60 
3.另外对共享资源做一定的隔离 如迁移RDS

![image.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1607483814220-0093a798-ac66-4d87-8383-e6b0d3cfa1b6.png#align=left&display=inline&height=166&margin=%5Bobject%20Object%5D&name=image.png&originHeight=331&originWidth=913&size=24957&status=done&style=none&width=456.5)
支持session ，global级别配置

If a query takes longer than this many seconds, the server increments the [`Slow_queries`](https://dev.mysql.com/doc/refman/5.7/en/server-status-variables.html#statvar_Slow_queries) status variable. If the slow query log is enabled, the query is logged to the slow query log file. This value is measured in real time, not CPU time, so a query that is under the threshold on a lightly loaded system might be above the threshold on a heavily loaded one

线上问题由于超过阀值导致增加大量日志，最终导致其他应用连接mysql共享资源被耗尽，因此在共享资源下应该合理设置该阀值，轻量负载可以调低该阀值，重量负载可以适当调高


![image.png](https://cdn.nlark.com/yuque/0/2020/png/2670557/1607482639468-feb1221f-571d-40e5-9935-239199f88f2f.png#align=left&display=inline&height=534&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1068&originWidth=1914&size=283892&status=done&style=none&width=957)
参考 mysqld参数设置[https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-session.html](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-session.html)
