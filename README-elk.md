# docker 安装elk环境

### 获取镜像
 
 最新的安装包 2020-08-27 目前最新版 7.9.0
```shell script

docker pull elasticsearch:7.9.0
docker pull kibana:7.9.0
docker pull logstash:7.9.0

```

### 准备配置文件

#### kibana.yml
```shell script
server.name: kibana
server.host: "0"
elasticsearch.hosts: [ "http://172.81.203.33:9200" ]
monitoring.ui.container.elasticsearch.enabled: true
i18n.locale: "zh-CN"
```

#### logstash.conf
```shell script
    
input {
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 5044
    codec => json_lines
  }
}

output {
  elasticsearch {
    hosts => ["http://172.81.203.33:9200"]
    index => "logstash-%{+YYYY.MM.dd}"
  }
  stdout {
    codec => rubydebug
  }
}
```

### 创建实例和启动

```shell script
docker run -dit --name elk_k -p 5601:5601 \
 -v /data/runtime/kibana.yml:/usr/share/kibana/config/kibana.yml \
 kibana:7.9.0


mkdir -p /data/runtime/es_data
docker run  -dit --name elk_e  -p 9200:9200 -p 9300:9300 \
 -e  "discovery.type=single-node" \
 -v /data/runtime/es_data:/usr/share/elasticsearch/data \
  elasticsearch:7.9.0

docker run  -dit --name elk_l -d \
            -v "$PWD":/data/runtime \
            -p 9044:5044 \
            logstash:7.9.0 \
            logstash -f /data/runtime/logstash.conf
```

### 运维调试


```shell script
进入docker容器内部查看信息
docker exec -it elk_l /bin/bash
查看日志
docker logs -f elk_l

启动、关闭、重启、删除
docker start elk_l
docker stop elk_l
docker restart elk_l
docker rm elk_l

```
