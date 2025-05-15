FROM nginx:1.28.0-alpine AS base

# Copy configuration into the container. Not necessary
COPY ./frontend-nginx.conf /etc/nginx/conf.d/default.conf
# Copy static content into the static content section
COPY ./dist /usr/share/nginx/html
