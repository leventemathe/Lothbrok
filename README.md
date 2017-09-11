# Lothbrok
Lothbrok is a cross-platform 2D game about vikings, loot and fighting. It's written in Java, with the libGDX framework, so it runs on PC/Mac/Linux, Android and iOS.

![screenshot](https://github.com/leviouss/Lothbrok/blob/master/doc/img/game.png "Screenshot")

You can jump and fight, but gold falls out of your treasure chest with every action, so be careful! Reach the end of the level with as much gold as possible.

## Cross-platform controls
The game works on PC with the keyboard, and with virtual controls on mobile.

![screenshot](https://github.com/leviouss/Lothbrok/blob/master/doc/img/gameplay_android.png "Controls")

## Tools
The maps were created with [Tiled](http://www.mapeditor.org), a flexible tile map editor. I used [Spriter](https://brashmonkey.com), a bone-based 2D animation software, to create the player and enemy animations. I created image assets in 4 sizes: XL, L, M and S to support a large variety of devices, and packaged them with Gradle tasks.

## Architecture
I created an MVC-ish architecture, where the game state is stored in the model, the view (libGDX screens and renderers) draws the state, and the controllers communicate between these two layers: for example they pass coordinates from the animations to the model, for collision detection.

![screenshot](https://github.com/leviouss/Lothbrok/blob/master/doc/img/mvc.png "MVC")

For the player and enemy entities, I used a component-based system, with one level of inheritance for some basic common data: coordinates.

![screenshot](https://github.com/leviouss/Lothbrok/blob/master/doc/img/ecs.png "Components")