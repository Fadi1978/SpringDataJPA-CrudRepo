FROM nginx:1.17.1-alpine
COPY ./dist/crudtuto-Front /usr/share/nginx/html
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]
