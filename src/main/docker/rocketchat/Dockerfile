FROM rocketchat/base:4

ENV RC_VERSION latest

MAINTAINER buildmaster@rocket.chat

VOLUME /app/uploads

RUN set -x \
 && curl -SLf "https://rocket.chat/releases/${rocket.version}/download" -o rocket.chat.tgz \
 && curl -SLf "https://rocket.chat/releases/${rocket.version}/asc" -o rocket.chat.tgz.asc \
 && gpg --verify rocket.chat.tgz.asc \
 && tar -zxf rocket.chat.tgz -C /app \
 && rm rocket.chat.tgz rocket.chat.tgz.asc \
 && cd /app/bundle/programs/server \
 && npm install \
 && npm cache clear

USER rocketchat

WORKDIR /app/bundle

ADD run.sh /app/bundle

EXPOSE ${rocket.port}

CMD ["bash", "/app/bundle/run.sh"]
