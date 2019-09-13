# shell-example
### 常用shell示例


+ [web项目脚本](./web-pro-script.sh)

    针对vue、anglar、react等前端框架项目的自动部署脚本，使用nginx服务代理
    
+ [node项目脚本](./node-pro-script.sh)

    nodejs项目部署脚本，使用pm2守护进程工具`pm2 start xxx`

+ [java项目脚本](./java-pro-script.sh)
    
    java项目部署脚本，使用守护进程启动`nohup java -jar xxx.jar &`
### docker 脚本

+ gitlab
    + [startup](./docker/gitlab/startup.sh)

+ mongo
    + [startup](./docker/mongo/startup.sh)

+ nginx

    + [startup](./docker/nginx/startup.sh)
    + [reload](./docker/nginx/reload.sh)
