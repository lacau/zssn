#!/bin/bash
set -e -o pipefail

source define-variables.sh
docker run --rm -d --name=$IMAGE_NAME -v ~/zssn/data:/opt/zssn/zssn/data -p 8080:8080 $IMAGE_NAME:v$VERSION.local
 
echo "$IMAGE_NAME started!"
echo ""
echo "Stop it by running: docker stop $IMAGE_NAME"
