ARG GCLOUD_SDK_VERSION=335.0.0-alpine

FROM google/cloud-sdk:$GCLOUD_SDK_VERSION
LABEL maintainer="admin <admin@purplemango.io>"

# Install Java 8 JRE (required for Firestore emulator).
RUN apk add --update --no-cache openjdk8-jre

# Install Firestore Emulator.
RUN gcloud components install cloud-firestore-emulator beta --quiet

COPY entrypoint.sh .

ENV PORT 808005
EXPOSE "$PORT"

ENV FIRESTORE_PROJECT_ID "purplemango-firestore-id"

ENTRYPOINT ["./entrypoint.sh"]