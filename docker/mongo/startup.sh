#!/usr/bin/env bash
#docker mongodb start
read -p "对外端口号：" port
echo "${port}"

docker run -p ${port}:27017 -v ~/mongo/db:/data/db -d mongo

echo "启动成功"
