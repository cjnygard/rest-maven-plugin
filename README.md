# rest-maven-plugin

Welcome to the rest-maven-plugin plugin for Apache Maven 3.

This plugin is meant to provide an easy way to interface to REST
services via the POST operation to send data files to the REST URL and
retrieve (and store) the results.  It also provides a means to perform a 
simple GET operation on a URL to retrieve and store the results.

One typical example is to send *.md documentation files to a
markdown-to-pdf conversion service (see
http://github.com/cjnygard/md2pdf) and store the resulting *.pdf file
locally.

## Available goals

 * rest:rest-request


## Getting started with REST and Maven

To use this plugin and start working with the rest request, declare the
plugin and add a dependency on rest-maven-plugin:

    <packaging>jar</packaging>
    ....
    <build>
      <plugins>
        <plugin>
          <groupId>com.github.cjnygard.mvn</groupId>
          <artifactId>rest-maven-plugin</artifactId>
          <version>0.1.4</version>
        </plugin>
      </plugins>
    </build>
    ....
    <dependencies>
      <dependency>
        <groupId>com.github.cjnygard.mvn</groupId>
        <artifactId>rest-maven-plugin</artifactId>
        <version>0.1.4</version>
      </dependency>
    </dependencies>


### Adding source directories

To specify the input *fileset* you can add the following
configurations.  To use a single fileset:

    <configuration>
      <fileset>
        <directory>${project.resources[0].directory}</directory>
        <includes>
          <include>*.md</include>
        </includes>
      </fileset>
    </configuration>

To use multiple filesets, just wrap the single fileset in a *filesets*
list wrapper:

    <configuration>
      <filesets>
        <fileset>
          <directory>${project.resources[0].directory}</directory>
          <includes>
            <include>*.md</include>
          </includes>
        </fileset>
        <fileset>
          <....>
        </fileset>
      </filesets>
    </configuration>

### Destination path

The results retrieved from the REST request will be stored in a file
based on the original POSTed filename, in the directory specified by
the *outputDir* tag.   Default *outputDir*  is
"${project.build.directory}/rest" but can be changed in the
configuration:

    <configuration>
      <outputDir>${project.build.directory}/docs</outputDir>
    </configuration>

### Result filename mapping

It is possible to change the name of the file using the <fileMapper>
settings.  This is a standard maven fileMapper implementation.  If
multiple *fileMapper* elements are specified, each will be applied in
turn to the transformed filename to get the final result.

For example, to change the extension to pdf and replace any part of
the filename matching 'test' to 'result', do the following:

    <configuration>
      <fileMappers>
        <fileMapper implementation="org.codehaus.plexus.components.io.filemappers.FileExtensionMapper">
          <targetExtension>.pdf</targetExtension>
        </fileMapper>
        <fileMapper implementation="org.codehaus.plexus.components.io.filemappers.RegExpFileMapper">
          <pattern>test</pattern>
          <replacement>result</replacement>
        </fileMapper>
      </fileMappers>
    </configuration>

See the standard FileMapper documentation for more details on typical
usage.

### Result file for GET request

When performing a GET request with no uploaded file content, it is possible
to use the *outputFilename* property to specify the filename.  This filename
will pass through any filename mapping that is defined in the pom.xml.
The file will be stored in the directory specified by the <outputDir> path.

    <configuration>
      <outputFilename>save.as.filename.ext</outputFilename>
    </configuration>

## REST URL Endpoint

The REST URL endpoint is specified via two parameters, the *endpoint*
and the *resource*.  The components are separated so that mapping
separate execution configurations to different *resource* extensions
can still share the same base *endpoint* setting.

    <configuration>
      <endpoint>http://docker:3001/md2pdf</endpoint>
      <resource>v1/api</resource>
    </configuration>


### REST Method

The REST request method can be configured via the *method* tag.
Currently only the default setting POST is fully tested and supported.
Other methods requiring data upload (PUT, PATCH) should be supported
identically to the POST request, but have not been tested.

If GET is used, the code will upload a file if the *fileset* element
is defined in order to initiate the GET request.

    <configuration>
      <method>POST</method>
    </configuration>

### REST Query Parameters

The REST request URL can be further modified by adding query
parameters to the request.  These query parameters can be configured
via the *queryParams* tag, which is a map of key/value pairs.

For example, to add the propertyies n=3 and addRequired=1 to the REST
request URL, the following configuration can be used:

    <configuration>
      <queryParams>
        <n>3</n>
        <addRequired>1</addRequired>
      </queryParams>
    </configuration>

### REST Header Parameters

The REST request URL can be further modified by adding header
parameters to the request.  These header parameters can be configured
via the *headers* tag, which is a map of key/value pairs.

    <configuration>
      <headers>
        <Content-Type>application/json</Content-Type>
      </headers>
    </configuration>

### REST Request/Response types

The REST request and response types can be configured via the
*requestType* and *responseType* tags.  Defaults for request and
response types are 'text/plain' and 'application/octet-stream'
respectively.

The request and response type parameters use the *MediaType* datatype
and consequently can be configured using the tags of the *MediaType*
object.  For example:

    <configuration>
      <requestType>
        <type>application</type>
        <subtype>json</subtype>
      </requestType>
      <responseType>
        <type>application</type>
        <subtype>pdf</subtype>
      </responseType>
    </configuration>

