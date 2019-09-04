#!/bin/bash
source /etc/profile
echo "nodejs项目脚本"

read -p "输入项目名：" project_name
echo "项目名：$project_name"

read -p "输入项目存放路径：" pro_path
echo "项目存放路径：$pro_path"

cd $pro_path

echo "开始git克隆项目"
read -p "输入git克隆地址：" git_path
echo "git地址：$git_path"
git clone $git_path
echo "已克隆完成"

echo "开始构建web项目"
npm install

#必须有pm2的环境
pm2 start ./bin/www.js
read -p "输入启动目录，默认（./bin/www.js）：" start_path
if [$start_path]; then
    pm2 start $start_path
else
    pm2 start ./bin/www.js
fi

echo "项目已启动"



