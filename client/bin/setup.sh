#! /bin/bash

set -e

source ./bin/print.sh

CONFIG_FILE=.env

# Prints an error directing users to properly setup their AWS profiles
aws_profile_required_error () {
  print_message "AWS_PROFILE required." $RED
  print_message "For more information on setting up your aws credentails, check out https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-profiles.html" $WHITE
  exit 1
}

# Adds config value to .env file if it does not exist
add_to_env_if_not_exist () {
  line="${1}=${2}"

  grep -qF -- "$1" "$CONFIG_FILE" || echo "$line" >> "$CONFIG_FILE"
}

# Check to see if the user has a specified command installed
check_command () {
  command -v $1 >/dev/null 2>&1 || { echo>&2 "Please install ${2} before continuing. Read the setup section of the README for more information."; exit 1; }
}

check_command aws "aws cli"
check_command jq "jq"

print_header "Project Setup"

# ###############################
# Setting Up AWS Profile
# ###############################

print_message "Enter your aws profile [ENTER]: " $WHITE

read aws_profile

if [ -z "$aws_profile" ]
then
  aws_profile_required_error
fi

if ! [[ $(aws configure --profile ${aws_profile} list) && $? -eq 0 ]]
then
  aws_profile_required_error
fi

add_to_env_if_not_exist "AWS_PROFILE" $aws_profile
export AWS_PROFILE=$aws_profile

# ###############################
# Setting Up S3
# ###############################

print_message "Enter the name you would like to use for your S3 Bucket [Enter]: " $WHITE

read s3_bucket

if [ -z "$s3_bucket" ]
then
    print_message "s3 bucket name required." $RED
    exit 1
fi

aws s3api create-bucket --bucket $s3_bucket
add_to_env_if_not_exist "S3_BUCKET" $s3_bucket

# ###############################
# Setting Up CloudFront
# ###############################
cf_dist_output=$(aws cloudfront create-distribution \
                     --origin-domain-name ${s3_bucket}.s3.amazonaws.com \
                     --default-root-object index.html)

cf_dist_id=$(echo $cf_dist_output | jq -r '.Distribution.Id')
add_to_env_if_not_exist "CLOUDFRONT_DISTRIBUTION_ID" $cf_dist_id

print_message "Your environment has been setup and settings have been saved to \".env\"" $CYAN
print_message "Configuration Properties: \n" $WHITE
cat .env

print_header "Congratulations! You're all setup."
