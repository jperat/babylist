build:
	mvn clean package
	docker build -t babylist .

run:
	docker-compose up -d

stop:
	docker-compose down --remove-orphans

logs:
	docker-compose logs -f -t
