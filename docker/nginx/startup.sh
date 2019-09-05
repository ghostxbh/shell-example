#!/usr/bin/env bash
#docker start nginx

read -p "容器运行名称：" cname

echo "${cname}"

read -p "对外访问端口：" port

echo "${port}"

docker run -d -p ${port}:80 --name ${cname} -v ~/nginx/www:/usr/share/nginx/html -v ~/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v ~/nginx/logs:/var/log/nginx nginx

echo "nginx 启动完成"
