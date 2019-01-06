#!/bin/bash
set -e -o pipefail

mvn clean install
source define-variables.sh

IMAGE_TAG="$IMAGE_NAME:v$VERSION.local"
IMAGE_OLD=""
if [ ! -z "$(docker images -q $IMAGE_TAG)" ]; then
	echo "Image already exists... moving tag for later removal..."
	IMAGE_OLD="$IMAGE_NAME:old"
	docker tag $IMAGE_TAG $IMAGE_OLD
fi

docker build --build-arg artifactVersion="$VERSION" -t $IMAGE_TAG .
if [ ! -z "$IMAGE_OLD" ]; then
	docker rmi "$IMAGE_NAME:old"
fi
