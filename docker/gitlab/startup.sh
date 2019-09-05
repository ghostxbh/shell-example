#!/usr/bin/env bash
echo 'docker启动gitlab脚本'
echo '设置http端口前，请先lsof -i:端口号，检查端口是否占用'

read -p '请输出http端口号：' hport
echo "端口号：$hport"

docker run \
    --detach \
    --hostname yourhost.com \
    --publish 8443:443 \
    --publish ${hport}:80 \
    --name gitlab \
    --restart unless-stopped \
    --volume /mnt/sda1/gitlab/etc:/etc/gitlab \
    --volume /mnt/sda1/gitlab/log:/var/log/gitlab \
    --volume /mnt/sda1/gitlab/data:/var/opt/gitlab \
    beginor/gitlab-ce:11.3.0-ce.0

echo '启动完成'
