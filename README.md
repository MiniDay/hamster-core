# [HamsterCore](https://github.com/MiniDay/hamster-core)

仓鼠核心：Minecraft 插件开发通用工具包

# 添加依赖

## Gradle (`build.gradle`)

```groovy
// 添加仓库
repositories {
    maven {
        url "https://maven.airgame.net/maven-public"
    }
}

dependencies {
    // 对于 Bukkit 插件
    compileOnly "cn.hamster3.mc.plugin:hamster-core-bukkit:1.0.0"
    // 对于 BungeeCord 插件
    compileOnly "cn.hamster3.mc.plugin:hamster-core-bungeecord:1.0.0"
}
```

## Maven (`pom.xml`)

```xml

<project>
    <!--添加仓库-->
    <repositories>
        <repository>
            <id>airgame-repo</id>
            <name>AirGame Maven仓库</name>
            <url>https://maven.airgame.net/maven-public</url>
        </repository>
    </repositories>

    <dependencies>
        <!--对于 Bukkit 插件-->
        <dependency>
            <groupId>cn.hamster3.mc.plugin</groupId>
            <artifactId>hamster-core-bukkit</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!--对于 BungeeCord 插件-->
        <dependency>
            <groupId>cn.hamster3.mc.plugin</groupId>
            <artifactId>hamster-core-bungeecord</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```
