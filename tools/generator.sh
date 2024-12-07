#!/bin/sh
BASE_DIR=$(cd -P $(dirname $0);pwd)
echo "jar_path is --> ${BASE_DIR}/generator.jar"
echo "properties_path is -->${BASE_DIR}/generator.properties"
java -jar -DconfigPath=${BASE_DIR}/generator.properties ${BASE_DIR}/generator.jar