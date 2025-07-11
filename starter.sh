#!/bin/bash

set -euo pipefail

# Directory base dove si trovano i microservizi (allo stesso livello dello script)
BASE_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$BASE_DIR"

echo "🔄 Inizio build dei microservizi Quarkus..."

for dir in */; do
  if [ -f "$dir/pom.xml" ]; then
    # Verifica se è un progetto Quarkus (cerca "io.quarkus" nel pom.xml)
    if grep -q "io.quarkus" "$dir/pom.xml"; then
      echo "📦 Build del microservizio Quarkus: $dir"

      ( cd "$dir" && mvn clean package -Dquarkus.package.type=fast-jar ) || {
        echo "❌ Errore durante la build di $dir. Interruzione dello script."
        exit 1
      }

      # Controlla se è presente il layout fast-jar
      JAR_DIR="$dir/target/quarkus-app"
      if [[ ! -d "$JAR_DIR/lib" || ! -d "$JAR_DIR/app" || ! -d "$JAR_DIR/quarkus" ]]; then
        echo "❌ Layout fast-jar mancante in $dir. Assicurati che quarkus.package.type=fast-jar sia impostato."
        exit 1
      fi

      echo "✅ Build completata: $dir"
    else
      echo "⚠️ $dir non è un progetto Quarkus. Saltato."
    fi
  else
    echo "⚠️ Nessun pom.xml in $dir. Saltato."
  fi
done

echo "🎉 Tutti i microservizi Quarkus sono stati compilati con successo!"

echo "🧹 Pulizia e arresto dei container Docker esistenti..."
docker-compose down -v

echo "🐳 Build delle immagini Docker (senza cache)..."
docker-compose build --no-cache

echo "🚀 Avvio dei container..."
docker-compose up -d
