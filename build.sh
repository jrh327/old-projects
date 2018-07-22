#!/bin/bash

aws s3 cp . s3://jonhopkins.net/old-projects --recursive  --exclude "*" --include "*.html" --include "*.js"
