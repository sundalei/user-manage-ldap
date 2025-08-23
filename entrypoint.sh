#!/bin/sh
# entrypoint.sh

# Exit immediately if a command exits with a non-zero status.
set -e

# Initialize JAVA_TOOL_OPTIONS to ensure it's not unset.
# This avoids issues if it's not already defined in the environment.
export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS}"

# Check for each LDAP environment variable and append it to JAVA_TOOL_OPTIONS if set.
# This allows any combination of the variables to be provided at runtime.

if [ -n "$LDAP_HOST" ]; then
  echo "✅ LDAP_HOST is set. Adding to Java system properties."
  export JAVA_TOOL_OPTIONS="-Dldap.host=${LDAP_HOST} ${JAVA_TOOL_OPTIONS}"
fi

if [ -n "$LDAP_PORT" ]; then
  echo "✅ LDAP_PORT is set. Adding to Java system properties."
  export JAVA_TOOL_OPTIONS="-Dldap.port=${LDAP_PORT} ${JAVA_TOOL_OPTIONS}"
fi

if [ -n "$LDAP_PASSWORD" ]; then
  echo "✅ LDAP_PASSWORD is set. Adding to Java system properties."
  # Note: Be cautious about logging or exposing secrets. Here we just confirm it's set.
  export JAVA_TOOL_OPTIONS="-Dldap.password=${LDAP_PASSWORD} ${JAVA_TOOL_OPTIONS}"
fi

echo "🚀 Starting application..."

# Execute the command passed to this script (which is the CMD from the Dockerfile).
# 'exec' replaces the shell process with the command, ensuring that signals
# (like from 'docker stop') are passed directly to the Java process.
exec "$@"