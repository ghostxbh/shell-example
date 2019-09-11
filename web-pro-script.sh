#!/bin/bash
source /etc/profile
echo "自动配置web项目脚本"

read -p "输入项目名：" project_name
echo "项目名：$project_name"

read -p "输入项目存放路径：" pro_path
dirpath=${pro_path}/${project_name}
echo "项目存放路径：$dirpath"

cd ${dirpath}

echo "开始git克隆项目"
read -p "输入git克隆地址：" git_path
echo "git地址：$git_path"
git clone ${git_path}
echo "已克隆完成"

echo "开始构建web项目"
npm install
npm run build

read -p "输入web项目地址：" dest
dest_path=${dest}/${project_name}

rm -rf ${dest_path}
mkdir -p ${dest_path}
cp -R dist/* ${dest_path}
echo "web项目已构建"
echo "================="

echo "增加nginx配置"
read -p "输入nginx配置地址：" nginx_path
echo "nginx地址：$nginx_path"
cd ${nginx_path}

read -p "输入访问端口号：" port
echo "访问端口号：$port"
read -p "输入nginx配置名称，以.conf结尾：" pro_nginx_name
echo "nginx配置名称：$pro_nginx_name"
echo "server {
    listen       $port;
    charset      utf-8;

    root         $dest_path;
    index        index.html;
    location / {
      try_files /\$uri /\$uri/ /index.html;
    }
   error_page   502  /502.html;
}"  >> ${pro_nginx_name}

nginx -t
nginx -s reload

echo "已部署完成"
