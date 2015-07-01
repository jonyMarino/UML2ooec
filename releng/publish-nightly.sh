#!/bin/sh
# ====================================================================
# Copyright (c) 2015 CNES and others
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Cedric Notot (Obeo) - initial API and implementation
# ====================================================================

[ -z "$WORKSPACE" -o -z "$PLATFORM" -o -z "$GIT_BRANCH" ] && {
     echo "Execution aborted.

One or more of the required variables is not set. They are normally
provided by the Hudson build.

- WORKSPACE  : the build workspace root.
- PLATFORM   : the name of the target Eclipse release (e.g. luna).
- GIT_BRANCH : the name for the Git branch being build/published.
"
    exit 1
}

if [ $# -ne 1 ]; then
     echo "Execution aborted. One or more of the required parameters is not set. 

Usage: $0 version

- version: the full target version.
"
exit 1
fi

######################################################################
# Setup
######################################################################

# Exit on error
set -e

# The full version
export VERSION=${1}

# The type of build being published
export BUILD_TYPE="nightly"
export BUILD_TYPE_PREFIX="N"

# The root folder for all UML Generators udpate sites
export UMLGEN_UPDATES_ROOT="/home/data/httpd/download.eclipse.org/umlgen/updates"

# The root folder for all UML Generators jars
export UMLGEN_JARS_ROOT="/home/data/httpd/download.eclipse.org/umlgen/jars"

# Streams are of the form 1.0.x: only keep major and minor version number parts
export STREAM=$(echo "$VERSION" | sed -r -e 's/^([0-9]+\.[0-9]+)\..*$/\1/')

# Converts the Hudson BUILD_ID (e.g. 2013-10-15_07-07-07) into the
# syntax we want for our update-sites (e.g. 20131015-070707)
export BUILD_TIMESTAMP=$(echo "$BUILD_ID" | sed -e 's/-//g' -e 's/_/-/')

# The timestamp in the p2 composite repos used to implement redirects
export P2_TIMESTAMP=$(date +"%s000")

# The full version for this build, e.g. 0.9.0-N20131015-070707
export FULL_VERSION="${VERSION}-${BUILD_TYPE_PREFIX}${BUILD_TIMESTAMP}"

# The root folder where all the builds of the same type as this one
# are published
export TARGET_ROOT="$UMLGEN_UPDATES_ROOT/$BUILD_TYPE"

# The folder for this particular build
export TARGET_DIR="$TARGET_ROOT/$FULL_VERSION/$PLATFORM"


# The root folder where all the builds of the same type as this one
# are published
export JARS_TARGET_ROOT="$UMLGEN_JARS_ROOT/$BUILD_TYPE"

# The folder for this particular build
export JARS_TARGET_DIR="$JARS_TARGET_ROOT/$FULL_VERSION"


# The folder for the related "third parties" jars on each component
export RTSJ_JARS_TARGET_DIR="$JARS_TARGET_DIR/rtsj"


######################################################################
# Publish the build
######################################################################

# Ensure the target folder exists
mkdir -p "$TARGET_DIR"
# The actual publication of the p2 repo produced by the build
cp -a "$WORKSPACE"/releng/org.eclipse.umlgen.repository/target/repository/* "$TARGET_DIR"

##############################
# PUBLISH THE RTSJ FRAMEWORK 

# Ensure the RTSJ jars folder exists
mkdir -p "$RTSJ_JARS_TARGET_DIR"
# The actual publication of the p2 repo produced by the build
cp -a "$WORKSPACE"/org.eclipse.umlgen.gen.rtsj/releng/org.eclipse.umlgen.rtsj.frameworks.repository/target/repository/* "$RTSJ_JARS_TARGET_DIR"

#
##############################

# Publish a dump of the build environment, may be useful to debug
env | sort > "$TARGET_DIR/build_env.txt"

######################################################################
# Setup or update the redirects (implemented as composite repos)
######################################################################

# Create a p2 composite repo to setup a redirect
create_redirect() {
    FROM="$1"
    TO="$2"

    mkdir -p "$FROM"
    cat > "$FROM/compositeArtifacts.xml" <<EOF
<?xml version='1.0' encoding='UTF-8'?>
<?compositeArtifactRepository version='1.0.0'?>
<repository name='Eclipse UML Generators' type='org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository' version='1.0.0'>
  <properties size='1'>
    <property name='p2.timestamp' value='$P2_TIMESTAMP'/>
  </properties>
  <children size='1'>
    <child location='http://download.eclipse.org/umlgen/$TO'/>
  </children>
</repository>
EOF

    cat > "$FROM/compositeContent.xml" <<EOF
<?xml version='1.0' encoding='UTF-8'?>
<?compositeMetadataRepository version='1.0.0'?>
<repository name='Eclipse UML Generators' type='org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository' version='1.0.0'>
  <properties size='1'>
    <property name='p2.timestamp' value='$P2_TIMESTAMP'/>
  </properties>
  <children size='1'>
    <child location='http://download.eclipse.org/umlgen/$TO'/>
  </children>
</repository>
EOF
    
}

# Create a index.htm to setup a redirect for jars
create_redirectJars() {
    FROM="$1"
    TO="$2"

    mkdir -p "$FROM"
    cat > "$FROM/index.htm" <<EOF
<html>
  <head>
    <meta http-equiv="refresh" content="0; URL=http://download.eclipse.org/umlgen/$TO/plugins/?d">
  </head>
  <body>
  </body>
</html> 
EOF
    
}

# First, a link for the $VERSION (e.g. "0.9.0/luna" => "0.9.0-NYYYYMMDD-HHMM/luna")
create_redirect "$TARGET_ROOT/$VERSION/$PLATFORM" "updates/$BUILD_TYPE/$FULL_VERSION/$PLATFORM"
# Also create a link for the $STREAM (e.g. "0.9/luna" => "0.9.1-NYYYYMMDD-HHMM/luna")
create_redirect "$TARGET_ROOT/$STREAM/$PLATFORM" "updates/$BUILD_TYPE/$FULL_VERSION/$PLATFORM"
# Also update the global "latest" links if we are building master
if [ "master" = "$GIT_BRANCH" ]; then
    create_redirect "$TARGET_ROOT/latest/$PLATFORM" "updates/$BUILD_TYPE/$FULL_VERSION/$PLATFORM"
fi

###################################
# REDIRECT FOR THE RTSJ FRAMEWORK 

# First, a link for the $VERSION (e.g. "0.9.0/rtsj" => "0.9.0-NYYYYMMDD-HHMM/rtsj")
create_redirectJars "$JARS_TARGET_ROOT/$VERSION/rtsj" "jars/$BUILD_TYPE/$FULL_VERSION/rtsj"
# Also create a link for the $STREAM (e.g. "0.9/rtsj" => "0.9.1-NYYYYMMDD-HHMM/rtsj")
create_redirectJars "$JARS_TARGET_ROOT/$STREAM/rtsj" "jars/$BUILD_TYPE/$FULL_VERSION/rtsj"
# Also update the global "latest" links if we are building master
if [ "master" = "$GIT_BRANCH" ]; then
    create_redirectJars "$JARS_TARGET_ROOT/latest/rtsj" "jars/$BUILD_TYPE/$FULL_VERSION/rtsj"
fi

#
###################################