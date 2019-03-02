#! /bin/bash

set -e

cd $(git rev-parse --show-toplevel)/client

source ./bin/print.sh

SELECTED_ENV=${1:-dev}
DATA_FILE="resources/templates/index.${SELECTED_ENV}.json"

print_message "Building ${SELECTED_ENV} index.html" $WHITE

node ./node_modules/hbs-cli/lib/index.js \
     --data resources/templates/index.shared.json \
     --data $DATA_FILE \
     -o resources/public \
     resources/templates/index.handlebars
