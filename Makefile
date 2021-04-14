GIT_ROOT	:= $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

docker-image	= $(USER)/vending-machine:latest

docker-build:
	docker build -t $(docker-image) .


tmp:
	mkdir -p $@

docker-run: tmp
	docker run -v $(GIT_ROOT)/tmp:/app/build/reports --rm -ti $(docker-image) ./gradlew --stacktrace test -i
