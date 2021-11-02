# yop-java-bridge-sdk

## Usage

本项目解决使用旧版SDK的商户迁移新版SDK，新商户对接请使用 yop-java-sdk。
如果是新对接的商户请勿使用本jar。

## Requirements

Building the API client library requires:
1. Java 1.8+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.yeepay.yop.sdk</groupId>
  <artifactId>yop-java-bridge-sdk</artifactId>
  <version>4.1.9</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.yeepay.yop.sdk:yop-java-bridge-sdk:4.1.1"
```
