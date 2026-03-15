#Build TypeScript
FROM node:20 AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

#Serve static files
FROM nginx:alpine

COPY --from=build /app /usr/share/nginx/html

EXPOSE 80