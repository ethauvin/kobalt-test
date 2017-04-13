package com.example;

import net.thauvin.erik.semver.Version;

@Version(major = 1, minor = 0, patch = 0, prerelease = "beta")
class Main {
    public static void main(String[] argv) {
        System.out.println(GeneratedVersion.getVersion());
    }
}