#!/usr/bin/env bash
#kill docker-ningx container and restart container

container_id=`docker ps --filter 'name=nginx' | awk 'NR>1' | awk '{print $1}'`

echo "kill nginx:"

if [ -n "$container_id" ]; then
   docker kill $container_id
fi

docker restart $container_id

echo "nginx 重启完成"
