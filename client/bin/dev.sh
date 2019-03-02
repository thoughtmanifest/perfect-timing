#! /bin/bash

set -e

# Ensure scripts are called from the correct directory.
# NOTE: This requires the project to be initialized with git
cd $(git rev-parse --show-toplevel)/client

# Load printing helpers
source ./bin/print.sh

print_header "Setting up development environment" $CYAN

# Install NPM & bundle packages
source ./bin/install-npm-deps.sh

# Set the index file to use dev settings
./bin/build-index.sh

print_message "Starting figwheel dev server" $WHITE

# Start figwheel server
clojure -Adev
