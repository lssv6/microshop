# __  __ _               ____  _                 
#|  \/  (_) ___ _ __ ___/ ___|| |__   ___  _ __  
#| |\/| | |/ __| '__/ _ \___ \| '_ \ / _ \| '_ \ 
#| |  | | | (__| | | (_) |__) | | | | (_) | |_) |
#|_|  |_|_|\___|_|  \___/____/|_| |_|\___/| .__/ 
#                                         |_|    

VERSION = 1.0


.PHONY:run_jenkins
run_jenkins:
	docker run -p 8080:8080 -p 50000:50000 --restart=on-failure \
		--name microshop_jenkins \
		jenkins/jenkins:lts-jdk17

.PHONY:test
test:
	./ms-catalog/gradlew test

.PHONY:build_jar
build_jar:
	./ms-catalog/gradlew assembleBootDist


.PHONY:build_image
build_image:
	docker build . -f ./docker/ms-catalog/Dockerfile \
		-t microshop_api:$(CATALOG_VERSION)

.PHONY:run_api
run_api:
	docker run microshop_catalog:$(CATALOG_VERSION) \
		-l microshop_catalog \
		-p 8080:8000 \
		-p 3306:3306

