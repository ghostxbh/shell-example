#!/usr/bin/env bash
# docker mysql starter

#自己定义映射宿主机地址
_dir=~/mysql
isFlag=true

read -p "请输入映射宿主机端口：" port
echo "映射端口：${port}"

read -p "设置mysql登陆密码：" psw

if [[ -n "$port" ]]; then
	isStart=`lsof -i:${port} | awk 'NR>1' | awk '{print $1}'`
	echo "${isStart}"
	if [[ -n "$isStart" ]]; then
	    isFlag=false
		echo "映射端口已启动"
	fi
fi

if [[ isFlag ]]; then
   docker run -p ${port}:3306 --name mysql_test -v ${_dir}/conf:/etc/mysql/conf.d -v ${_dir}/logs:/logs -v ${_dir}/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${psw} -d mysql:5.7

   echo "started"
else
   echo "no start"
fi
