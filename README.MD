# 使用说明
## 打包
mvn clean package -DskipTests

## 使用
a. 增加spark配置：
--jars ${jar_path}
--conf spark.sql.extensions=io.github.seabow.spark.hyperscan.Extension  
b. 使用hlike语法:select hlike('abcde', array('c.*', '[a-Z]+other'))


## 性能对比
根据一些基础测试，在278亿数据量的日志场景下，hlike 对比spark 原生 regex 性能提升12.95倍。