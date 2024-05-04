FROM node:22

RUN apt-get update
RUN apt-get -y install git

WORKDIR /src

CMD git clone --depth 1 file:///repo ; cd repo && git pull && cd template-sample-client && yarn && yarn start
