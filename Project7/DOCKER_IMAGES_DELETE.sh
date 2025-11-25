#!/bin/bash

CONTAINER_NAMES=("gubenko-kafka" "gubenko-zookeeper" "gubenko-postgres" "gubenko-eureka-server" "gubenko-user-service" "gubenko-api-gateway")

echo "Проверка существования контейнеров..."

for container_name in "${CONTAINER_NAMES[@]}"; do
    if docker ps -a --format '{{.Names}}' | grep -q "^${container_name}$"; then
        echo "Найден контейнер: $container_name"

        if docker ps --format '{{.Names}}' | grep -q "^${container_name}$"; then
            echo "Останавливаем контейнер: $container_name"
            docker stop "$container_name"
        fi

        echo "Удаляем контейнер: $container_name"
        docker rm "$container_name"

        if [ $? -eq 0 ]; then
            echo "✅ Контейнер $container_name успешно удален"
        else
            echo "❌ Ошибка при удалении контейнера $container_name"
        fi
    else
        echo "ℹ️  Контейнер $container_name не найден"
    fi
done

echo "Удаляем связанные тома..."
docker volume prune -f

echo "✅ Очистка завершена. Все указанные контейнеры удалены."

#docker system prune -a -f
