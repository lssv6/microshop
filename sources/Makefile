# ,_ _
#/| | |  o  _   ,_   _  () |)    _
# | | |  | /   /  | / \_/\ |/\  / \_|/\_
# | | |_/|/\__/   |/\_//(_)|  |/\_/ |_/
#                                  (|

# FRONTEND
.PHONY: compile_frontend
compile_frontend:
	(cd ./ms-frontend; npm run build)

# BACKEND
.PHONY: compile_backend
compile_backend:
	(cd ./ms-backend; ./gradlew assembleBootDist)

.PHONY: up_everything
up_everything: compile_backend compile_frontend
	podman compose -f compose.yaml up --detach
