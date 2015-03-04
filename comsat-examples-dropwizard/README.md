# COMSAT Dropwizard Examples

## Features

- Comsat + Dropwizard
- Swagger Integration


## Run

In the shell:

```sh
gradle runSimple
```


To package:

```sh
gradle distZip
```

The package is under `build/distribution`, unzip, and it can be run as

```sh
./bin/comsat-examples-dropwizard server ./conf/hello-world.yml
```