# __  __ _               ____  _
#|  \/  (_) ___ _ __ ___/ ___|| |__   ___  _ __
#| |\/| | |/ __| '__/ _ \___ \| '_ \ / _ \| '_ \
#| |  | | | (__| | | (_) |__) | | | | (_) | |_) |
#|_|  |_|_|\___|_|  \___/____/|_| |_|\___/| .__/
#                                         |_|

CATALOG_VERSION = 1.0

.PHONY: build
build:
	(cd ms-catalog; ./gradlew clean build)
	(cd ms-image_store; ./gradlew clean build)

#.PHONY:run_database
#run_database:
#	docker run -p 3306:3306 \
#		--name microshop_database \
#		--env MARIADB_USER=user \
#		--env MARIADB_PASSWORD=password \
#		--env MARIADB_DATABASE=microshop \
#		--env MARIADB_ROOT_PASSWORD=root \
#		mariadb:11-ubi
#
#.PHONY:run_jenkins
#run_jenkins:
#	docker run -p 8080:8080 -p 50000:50000 --restart=on-failure \
#		--name microshop_jenkins \
#		jenkins/jenkins:lts-jdk17
#.PHONY: run_image-storage
#
#.PHONY:test
#test:
#	./ms-catalog/gradlew test
#
#.PHONY:build_jar
#build_jar:
#	./ms-catalog/gradlew assembleBootDist
#
#
#.PHONY:build_image
#build_image:
#	docker build . -f ./docker/ms-catalog/Dockerfile \
#		-t microshop_api:$(CATALOG_VERSION)
#
#.PHONY:run_api
#run_api:
#	docker run microshop_catalog:$(CATALOG_VERSION) \
#		-l microshop_catalog \
#		-p 8080:8000 \
#		-p 3306:3306
