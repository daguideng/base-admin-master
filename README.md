# 停止并删除容器
docker-compose down -v

# 重新构建镜像
docker build -t base-admin-master-app --no-cache .

# 启动服务
docker-compose up -d

# 查看日志
docker logs -f base_admin_app
