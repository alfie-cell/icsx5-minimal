#!/usr/bin/env bash
# JitPack fails to build cert4android at the pinned commit, so build it into ~/.m2 once.
set -euo pipefail
REV=$(grep '^bitfire-cert4android' "$(dirname "$0")/../gradle/libs.versions.toml" | sed -E 's/.*"(.*)".*/\1/')
TMP=$(mktemp -d); trap 'rm -rf "$TMP"' EXIT
git clone -q https://github.com/bitfireAT/cert4android.git "$TMP/c4a"
cd "$TMP/c4a" && git checkout -q "$REV"
[ -f local.properties ] || echo "sdk.dir=${ANDROID_HOME:-$HOME/Library/Android/sdk}" > local.properties
./gradlew -q -Pgroup=com.github.bitfireAT -Pversion="$REV" publishToMavenLocal
echo "cert4android $REV published to ~/.m2"
