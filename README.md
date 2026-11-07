# Grimforge

**Build your tools from parts... then watch them grow!**

* Forge pickaxes, axes, shovels and swords from heads, handles and bindings.
* Level them up by mining and fighting, and spend points on modifiers.
* Pry runes off monsters, socket them into your weapons, and discover Rune Words.

Java Edition required. Grimforge is a NeoForge mod for Minecraft 26.3 and 1.21.1. Looking for the Paper plugin instead? That lives at [Mikeym41/Grimforge](https://github.com/Mikeym41/Grimforge).

## Download Grimforge

This place holds the Java code for Grimforge. The mod is in early development and isn't released yet. When it is, you'll find it on Modrinth. Until then, the [Grimforge plugin](https://modrinth.com/plugin/grimforge) is the playable version.

Edit the Code
---------

Want to add a feature or fix a bug yourself? You can get the game running, with Grimforge, straight from the code here by doing these *four* things:

1. Download Grimforge's source code and put it somewhere. Git is the best way if you know it, but [a .zip file works too](https://github.com/Mikeym41/Grimforge-Mod/archive/refs/heads/mc26.3/dev.zip). (If you plan on sending your changes back, you'll need Git.)
2. Install the right Java for the branch you're on:
   * `mc26.3/dev` (Minecraft 26.3): Java 25
   * `mc1.21.1/dev` (Minecraft 1.21.1): Java 21
3. Open terminal / command prompt and go to the folder where you put the code.
4. Run **one** of these commands:
   * Mac OS X / Linux: `./gradlew runClient`
   * Windows - Command Prompt: `gradlew runClient`
   * Windows - PowerShell: `.\gradlew runClient`

⚒️ That's it. ⚒️ The first run takes a while, because it downloads Minecraft and decompiles it so you can read its code. It needs about 4 GB of free memory, so close anything heavy first. If it works, **the game opens and you can make a world with Grimforge in it**.

When you change the code, restart the game by running the command again. If your Java has an error in it, the command fails and tells you where.

### Using a Java IDE

To work on Grimforge in an IDE:

1. Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
2. Open the folder with Grimforge's code. IntelliJ turns it into a project.
3. In **Settings → Build Tools → Gradle**, set **Gradle JVM** to Java 25 (or Java 21 on the 1.21.1 branch).

That's pretty much it. To play, open the **Gradle** panel and double-click **Tasks → mod development → runClient**.

### Two Versions, Two Branches

Grimforge supports two Minecraft versions, and each one has its own branch:

| Branch | Minecraft | NeoForge | Java |
| --- | --- | --- | --- |
| `mc26.3/dev` | 26.3 | 26.3.0.101 | 25 |
| `mc1.21.1/dev` | 1.21.1 | 21.1.265 | 21 |

New features land on `mc26.3/dev` first, then get ported to `mc1.21.1/dev`.

### Speeding Up Testing

Restarting the game after every change gets old fast. Running `runServer` instead of `runClient` starts faster, and you can reconnect to it after each restart.

Submitting Your Changes
------------

Grimforge is open source under the [MIT license](LICENSE), so your changes will be too. The best way to send them is to fork this repo on GitHub, make your changes there, and open a pull request against the `mc26.3/dev` branch.

Links
-----

* [Grimforge plugin on Modrinth](https://modrinth.com/plugin/grimforge)
* [Grimforge plugin on GitHub](https://github.com/Mikeym41/Grimforge)
* [Issue tracker](https://github.com/Mikeym41/Grimforge-Mod/issues)
