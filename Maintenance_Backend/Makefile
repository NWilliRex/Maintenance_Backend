IMAGE_NAME=2391650/maintenance_backend
CONTAINER_NAME=maintenance_backend

build:
	docker build -t $(IMAGE_NAME) .

run:
	docker run -d --name $(CONTAINER_NAME) -p 9090:9090 $(IMAGE_NAME)

exec:
	docker exec -it $(CONTAINER_NAME) bash

stop:
	docker stop $(CONTAINER_NAME)
	docker rm $(CONTAINER_NAME)

test:
	docker run --rm $(IMAGE_NAME) mvn clean test

restart:
	@$(MAKE) stop
	@$(MAKE) build
	@$(MAKE) run