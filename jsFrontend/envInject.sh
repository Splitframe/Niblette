#!/bin/sh

WWW_DIR=/usr/share/nginx/html
INJECT_FILE_SRC="${WWW_DIR}/envInject.template.js"
INJECT_FILE_DST="${WWW_DIR}/envInject.js"

echo "Injecting env vars into ${INJECT_FILE_DST}"

envsubst < "${INJECT_FILE_SRC}" > "${INJECT_FILE_DST}"

#[ -z "$@" ] && nginx -g 'daemon off;' || $@