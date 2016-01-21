# App Theme Helper

This is basically a copy of [App Theme Engine](https://github.com/afollestad/app-theme-engine) by [Aidan Follestad](https://github.com/afollestad) which only includes the "Config" part. This library is only for saving and querying theme values. The user is responsible to use those values, unless ATE this library won't automatically theme your views. As an extra this library includes a few Utilty methods from ATE and myself to make theming easy.

---

# Gradle Dependency

[![Release](https://jitpack.io/v/kabouzeid/app-theme-helper.svg)](https://jitpack.io/#kabouzeid/app-theme-helper)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

#### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

#### Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies {
	...
	compile ('com.github.kabouzeid:app-theme-helper:0.1kmod'@aar) {
		transitive = true
	}
}
```
