#!/usr/bin/env bash
# docker tomcat start

_dir=~/tomcat

# shellcheck disable=SC2162
read -p "输入启动端口：" port
echo "tomcat：${port}"

docker run --name tomcat -p "$port":8080 -v $_dir/webapps:/usr/local/tomcat/webapps -v $_dir/logs:/usr/local/tomcat/logs -d tomcat:8.5

echo "启动完成"
