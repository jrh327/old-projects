#!/bin/bash

source .env

aws s3 cp . "s3://$S3_BUCKET/$S3_PATH" --recursive  --exclude "*" --include "*.html" --include "*.js"
