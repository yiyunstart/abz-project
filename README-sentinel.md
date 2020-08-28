https://hub.docker.com/r/bladex/sentinel-dashboard/

下周最新版 Sentinel 控制台

docker pull bladex/sentinel-dashboard:1.7.1

docker run --name sentinel  -d -p 8858:8858 -d  bladex/sentinel-dashboard:1.7.1

查看日志
docker logs -f sentinel

visit: http://172.81.203.33:8858/
account and password: [sentinel sentinel]
