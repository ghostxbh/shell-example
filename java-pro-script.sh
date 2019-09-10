#!/usr/bin/env bash
#java start script

read -p "project name: " projectName

read -p "project path: " projectPath

jar="${projectName}.jar"

cd ${projectPath}

git pull
mvn clean install

port=`ps -ef | grep "$jar" | grep -v 'grep' | awk '{print $2}'`
echo "kill $port"
if [[ -n "${port}" ]]; then
kill -9 ${port}
fi

cd ${projectPath}/target

(nohup java -jar ${jar} nohup.out 2>&1) &
sleep 5s
ps -ef | grep ${jar} | grep -v 'grep' | awk '{print $2}'

echo "项目已启动"
