To run the example

./bin/comsat-examples-dropwizard server ./conf/hello-world.yml


To update the configuration

Change conf/hello-world.yml


To run as linux service

#   ln -s /bin/gradle-init-start-stop /etc/init.d/application-name
#
# it requires a conf file /etc/NAME.conf, e.g. /etc/application-name.conf
# otherwise it will quit.
#
# CONFIGURATION FILE ENTRIES:
# ---------------------------
# username=process-owner
# prog="/path/to/application-name -a any -e extra parameters"
# serviceName=SomeShortNameForService
# javaClass=package.for.JavaClass (com.example.helloworld.HelloWorldApplication in this case)