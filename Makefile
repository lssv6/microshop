.PHONY : run_database
run_database:
	docker run --detach \
		-p 3306:3306 \
		--name ms-database \
		--env MARIADB_USER=microshop \
		--env MARIADB_PASSWORD=password \
		--env MARIADB_DATABASE=microshop \
		--env MARIADB_ROOT_PASSWORD=password \
		mariadb:11-ubi
