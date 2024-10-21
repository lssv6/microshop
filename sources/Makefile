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

.PHONY: run
run:
	docker-compose -f docker-compose.yaml --env-file production.env up --build

