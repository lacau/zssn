SCRIPT_PATH="$( cd "$(dirname "$BASH_SOURCE")" ; pwd -P )"

IMAGE_NAME=zssn
VERSION=$(cd $SCRIPT_PATH; mvn -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive exec:exec -q)

echo "Current version: $VERSION"
