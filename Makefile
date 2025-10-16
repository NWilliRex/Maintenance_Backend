IMAGE_NAME=2391650/maintenance_backend
CONTAINER_NAME=maintenance_backend

build:
	docker build -t $(IMAGE_NAME) .

run:
	docker run -d --name $(CONTAINER_NAME) -p 8080:8080 $(IMAGE_NAME)

exec:
	docker exec -it $(CONTAINER_NAME) bash

stop:
	docker stop $(CONTAINER_NAME)  true
	docker rm $(CONTAINER_NAME)  true

test:
	docker run --rm $(IMAGE_NAME) mvn clean test