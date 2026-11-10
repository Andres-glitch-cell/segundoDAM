#!/usr/bin/env bash
set -euo pipefail

if [[ "$(uname -s)" != "Linux" ]]; then
    printf 'Este instalador solo funciona en Linux.\n' >&2
    exit 1
fi

script_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
sdk_dir="$script_dir/SDK_Java_Linux"

case "$(uname -m)" in
    x86_64|amd64) architecture="x64" ;;
    aarch64|arm64) architecture="aarch64" ;;
    *)
        printf 'Arquitectura no compatible: %s\n' "$(uname -m)" >&2
        exit 1
        ;;
esac

if [[ -e "$sdk_dir" ]]; then
    if [[ -x "$sdk_dir/bin/java" ]] && "$sdk_dir/bin/java" -version 2>&1 | grep -qE '^openjdk version "25([.]|")'; then
        printf 'JDK 25 ya está instalado en %s\n' "$sdk_dir"
        exit 0
    fi
    printf 'Ya existe %s y no parece contener JDK 25; no se modificó.\n' "$sdk_dir" >&2
    exit 1
fi

if ! command -v tar >/dev/null 2>&1; then
    printf 'Se necesita tar para instalar el SDK.\n' >&2
    exit 1
fi

download_url="https://api.adoptium.net/v3/binary/latest/25/ga/linux/$architecture/jdk/hotspot/normal/eclipse"
temporary_dir="$(mktemp -d "$script_dir/.sdk-java-linux.XXXXXX")"
trap 'rm -rf "$temporary_dir"' EXIT
archive="$temporary_dir/temurin.tar.gz"
extracted_dir="$temporary_dir/extracted"
mkdir "$extracted_dir"

if command -v curl >/dev/null 2>&1; then
    curl --fail --location --retry 3 --output "$archive" "$download_url"
elif command -v wget >/dev/null 2>&1; then
    wget --output-document="$archive" "$download_url"
else
    printf 'Se necesita curl o wget para descargar el SDK.\n' >&2
    exit 1
fi

tar -xzf "$archive" --strip-components=1 -C "$extracted_dir"
if [[ ! -x "$extracted_dir/bin/java" || ! -x "$extracted_dir/bin/javac" ]] || ! "$extracted_dir/bin/java" -version 2>&1 | grep -qE '^openjdk version "25([.]|\")'; then
    printf 'El archivo descargado no contiene un JDK 25 válido.\n' >&2
    exit 1
fi

mv "$extracted_dir" "$sdk_dir"
printf 'JDK instalado en %s\n' "$sdk_dir"
printf 'Para esta terminal, ejecuta:\nexport JAVA_HOME="%s"\nexport PATH="$JAVA_HOME/bin:$PATH"\n' "$sdk_dir"
"$sdk_dir/bin/javac" -version
