# ,__ __                           _
#/|  |  |  o                      | |
# |  |  |      __   ,_    __   ,  | |     __    _
# |  |  |  |  /    /  |  /  \_/ \_|/ \   /  \_|/ \_
# |  |  |_/|_/\___/   |_/\__/  \/ |   |_/\__/ |__/
#                                            /|
#                                            \|

# FRONTEND
.PHONY: compile_frontend
compile_frontend:
	(cd ./ms-frontend; npm run build)

# BACKEND
.PHONY: compile_backend
compile_backend:
	(cd ./ms-backend; ./gradlew assembleBootDist)

.PHONY: compile_everything
compile_everything: compile_backend compile_frontend

.PHONY: deploy_everything
deploy_everything:
	podman compose  -f compose.yaml --env-file testing.env --podman-run-args="--replace" up --detach --build

.PHONY: up_everything
up_everything: compile_everything deploy_everything
