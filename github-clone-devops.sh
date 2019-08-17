#!/bin/bash
source /etc/profile

name=zeus-ui
dirpath=/work/src/server/$name

echo "git克隆项目"

git clone https://github.com/bullteam/zeus-ui.git

echo "克隆完成"

cd $dirpath

npm install
npm run build

dest=/opt/app/$name

rm -rf $dest

mkdir -p $dest

cp -R dist/* $dest

echo "打包完成"


echo "增加nginx配置"

dir_nginx=/usr/local/nginx/conf/conf.d

cd $dir_nginx

echo "server {
    listen       8888;
    charset      utf-8;

    root         /opt/app/zeus-ui;
    index        index.html;
    location / {
      try_files $uri $uri/ /index.html;
    }
   error_page   502  /502.html;
}"  >> zeus.8888.conf

nginx -t

nginx -s reload

echo "部署完成"
