#!/bin/sh
# needs a mongoinstance - defaults to container linking with alias 'mongo'

MONGO_HOST=$MONGO_PORT_${mongo.port}_TCP_ADDR":"${mongo.port}

export MONGO_URL=mongodb://${MONGO_HOST}/rocketchat
export HOME=/tmp
export PORT=${rocket.port}
export ROOT_URL=http://localhost:${rocket.port}
export Accounts_AvatarStorePath=/app/uploads
export ADMIN_USERNAME=admin
export ADMIN_PASS=supersecret
export ADMIN_EMAIL=admin@example.com

node main.js
