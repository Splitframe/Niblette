build-niblette-backend:
	@docker build -f jvmBackend/Dockerfile -t niblette-server:0.1 .
build-niblette-frontend:
	@docker build -f jsFrontend/Dockerfile -t niblette-frontend:0.1 .
