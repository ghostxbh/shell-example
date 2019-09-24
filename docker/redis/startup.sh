#!/usr/bin/env bash
#docker redis start

_dir=~/redis

read -p "对外端口号：" port
echo "${port}"

docker run -p ${port}:6379 -v ${_dir}/data:/data -d redis:3.2 redis-server --appendonly yes

echo "启动成功"
